/**
 * Mapper para conversão de dados de vendas
 */

import { Sale, SaleItem } from '../models';
import { SaleDTO, SaleItemDTO } from '../models/product.dto';

export class SaleMapper {
  /**
   * Converte SaleDTO (backend) para Sale (frontend)
   */
  static toModel(dto: SaleDTO): Sale {
    return {
      id: dto.id.toString(),
      clientId: dto.clienteId?.toString() || '',
      client: dto.clienteNome ? {
        id: dto.clienteId.toString(),
        name: dto.clienteNome,
        email: '',
        phone: '',
        address: '',
        creditLimit: 0,
        currentDebt: 0,
        createdAt: new Date()
      } : undefined,
      items: dto.itens.map(item => this.toModelItem(item)),
      subtotal: dto.totalAmount,
      discount: dto.discount,
      total: dto.finalAmount,
      paymentMethod: dto.pagamentos?.[0]?.paymentMethod as any || 'cash',
      status: dto.status as any,
      createdAt: new Date(dto.createdAt)
    };
  }

  /**
   * Converte SaleItemDTO para SaleItem
   */
  static toModelItem(dto: SaleItemDTO): SaleItem {
    return {
      id: dto.id.toString(),
      productId: dto.produtoId.toString(),
      product: {
        id: dto.produtoId.toString(),
        name: dto.produtoNome,
        description: '',
        categoryId: '',
        cost: 0,
        price: dto.unitPrice,
        stock: 0,
        minStock: 0,
        createdAt: new Date(),
        updatedAt: new Date()
      },
      quantity: dto.quantity,
      unitPrice: dto.unitPrice,
      subtotal: dto.totalPrice
    };
  }

  /**
   * Converte Sale (frontend) para SaleDTO (backend)
   * Nota: O backend usa CriarVendaRequest para criar vendas
   */
  static toDTO(sale: Partial<Sale>): any {
    return {
      clienteId: sale.clientId ? parseInt(sale.clientId) : null,
      itens: sale.items?.map(item => ({
        produtoId: parseInt(item.productId),
        quantity: item.quantity,
        unitPrice: item.unitPrice,
        discount: 0
      })),
      discount: sale.discount || 0,
      notes: '',
      pagamentos: [{
        paymentMethod: sale.paymentMethod?.toUpperCase() || 'DINHEIRO',
        amount: sale.total || 0
      }]
    };
  }

  /**
   * Converte array de DTOs para array de Models
   */
  static toModelArray(dtos: SaleDTO[]): Sale[] {
    return dtos.map(dto => this.toModel(dto));
  }
}
