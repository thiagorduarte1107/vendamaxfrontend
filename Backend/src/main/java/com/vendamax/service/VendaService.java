package com.vendamax.service;

import com.vendamax.dto.VendaDTO;
import com.vendamax.dto.request.CriarVendaRequest;
import com.vendamax.entity.*;
import com.vendamax.mapper.PaymentMethodMapper;
import com.vendamax.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service: Venda
 */
@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final CaixaRepository caixaRepository;
    private final ProdutoRepository produtoRepository;

    /**
     * Listar todas as vendas
     */
    public List<VendaDTO> findAll() {
        return vendaRepository.findAll().stream()
                .map(VendaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Buscar venda por ID
     */
    public VendaDTO findById(Long id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
        return VendaDTO.fromEntity(venda);
    }

    /**
     * Buscar vendas do dia
     */
    public List<VendaDTO> findVendasDoDia() {
        return vendaRepository.findVendasDoDia().stream()
                .map(VendaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Buscar vendas por cliente
     */
    public List<VendaDTO> findByCliente(Long clienteId) {
        return vendaRepository.findByClienteIdOrderByCreatedAtDesc(clienteId).stream()
                .map(VendaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Buscar vendas por período
     */
    public List<VendaDTO> findByPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return vendaRepository.findByPeriodo(inicio, fim).stream()
                .map(VendaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Criar venda
     */
    @Transactional
    public VendaDTO create(CriarVendaRequest request, Long usuarioId) {
        // Buscar usuário
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Buscar caixa
        Caixa caixa = caixaRepository.findById(request.getCaixaId())
                .orElseThrow(() -> new RuntimeException("Caixa não encontrado"));

        // Verificar se caixa está aberto
        if (caixa.getStatus() != Caixa.CaixaStatus.OPEN) {
            throw new RuntimeException("Caixa não está aberto");
        }

        // Buscar cliente (opcional)
        Cliente cliente = null;
        if (request.getClienteId() != null) {
            cliente = clienteRepository.findById(request.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        }

        // Criar venda
        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setUsuario(usuario);
        venda.setCaixa(caixa);
        venda.setDiscount(request.getDiscount() != null ? request.getDiscount() : BigDecimal.ZERO);
        venda.setStatus(Venda.VendaStatus.PENDING);
        venda.setNotes(request.getNotes());

        // Adicionar itens
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CriarVendaRequest.ItemVendaRequest itemRequest : request.getItens()) {
            Produto produto = produtoRepository.findById(itemRequest.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemRequest.getProdutoId()));

            // Verificar estoque
            if (produto.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getName());
            }

            ItemVenda item = new ItemVenda();
            item.setVenda(venda);
            item.setProduto(produto);
            item.setQuantity(itemRequest.getQuantity());
            item.setUnitPrice(itemRequest.getUnitPrice());
            item.setDiscount(itemRequest.getDiscount() != null ? itemRequest.getDiscount() : BigDecimal.ZERO);
            
            BigDecimal itemSubtotal = itemRequest.getUnitPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            item.setSubtotal(itemSubtotal);
            
            BigDecimal itemTotal = itemSubtotal.subtract(item.getDiscount());
            item.setTotalPrice(itemTotal);

            venda.getItens().add(item);
            totalAmount = totalAmount.add(itemTotal);

            // Atualizar estoque
            produto.setStock(produto.getStock() - itemRequest.getQuantity());
            produtoRepository.save(produto);
        }

        venda.setSubtotal(totalAmount);
        venda.setTotal(totalAmount.subtract(venda.getDiscount()));
        venda.setTotalAmount(totalAmount);
        venda.setFinalAmount(totalAmount.subtract(venda.getDiscount()));

        // Adicionar pagamentos
        for (CriarVendaRequest.PagamentoRequest pagamentoRequest : request.getPagamentos()) {
            Pagamento pagamento = new Pagamento();
            pagamento.setVenda(venda);
            
            pagamento.setMethod(PaymentMethodMapper.toEnglish(pagamentoRequest.getPaymentMethod()));
            pagamento.setPaymentMethod(Pagamento.MetodoPagamento.valueOf(pagamentoRequest.getPaymentMethod()));
            pagamento.setAmount(pagamentoRequest.getAmount());
            pagamento.setStatus("PENDING");
            pagamento.setPaidAt(LocalDateTime.now());

            venda.getPagamentos().add(pagamento);
        }

        // Verificar se pagamento está completo
        BigDecimal totalPago = venda.getPagamentos().stream()
                .map(Pagamento::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPago.compareTo(venda.getFinalAmount()) >= 0) {
            venda.setStatus(Venda.VendaStatus.COMPLETED);
        }

        venda = vendaRepository.save(venda);
        return VendaDTO.fromEntity(venda);
    }

    /**
     * Cancelar venda
     */
    @Transactional
    public VendaDTO cancelar(Long id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        if (venda.getStatus() == Venda.VendaStatus.CANCELLED) {
            throw new RuntimeException("Venda já está cancelada");
        }

        // Devolver estoque
        for (ItemVenda item : venda.getItens()) {
            Produto produto = item.getProduto();
            produto.setStock(produto.getStock() + item.getQuantity());
            produtoRepository.save(produto);
        }

        venda.setStatus(Venda.VendaStatus.CANCELLED);
        venda = vendaRepository.save(venda);
        return VendaDTO.fromEntity(venda);
    }

    /**
     * Calcular total de vendas do dia
     */
    public BigDecimal calcularTotalVendasDia() {
        return vendaRepository.calcularTotalVendasDia();
    }

    /**
     * Contar vendas do dia
     */
    public long countVendasDia() {
        return vendaRepository.countVendasDia();
    }
}
