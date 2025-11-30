-- ============================================
-- INSERIR VENDAS DO DIA ATUAL PARA TESTE
-- ============================================

USE vendamax;
GO

-- Verificar se existe usuário e caixa
IF NOT EXISTS (SELECT 1 FROM usuarios WHERE id = 1)
BEGIN
    PRINT 'ERRO: Usuário com ID 1 não existe!';
    PRINT 'Execute primeiro o script de criação de usuários.';
    RETURN;
END

-- Verificar se existe caixa aberto
IF NOT EXISTS (SELECT 1 FROM caixas WHERE user_id = 1 AND status = 'OPEN')
BEGIN
    PRINT 'Criando caixa aberto para teste...';
    INSERT INTO caixas (user_id, opening_balance, status, opened_at, created_at)
    VALUES (1, 500.00, 'OPEN', GETDATE(), GETDATE());
    PRINT 'Caixa criado com sucesso!';
END

DECLARE @CaixaId BIGINT = (SELECT TOP 1 id FROM caixas WHERE user_id = 1 AND status = 'OPEN');

PRINT 'Caixa ID: ' + CAST(@CaixaId AS VARCHAR(10));
PRINT '';

-- ============================================
-- VENDA 1: Notebook + Mouse
-- ============================================
DECLARE @VendaId1 BIGINT;

INSERT INTO vendas (customer_id, user_id, cash_register_id, subtotal, discount, total, final_amount, total_amount, status, payment_method, created_at)
VALUES (1, 1, @CaixaId, 3589.90, 0, 3589.90, 3589.90, 3589.90, 'COMPLETED', 'DINHEIRO', DATEADD(HOUR, -3, GETDATE()));

SET @VendaId1 = SCOPE_IDENTITY();

-- Itens da venda 1
INSERT INTO itens_venda (sale_id, product_id, quantity, unit_price, subtotal, discount, total_price, created_at)
VALUES 
(@VendaId1, 1, 1, 3500.00, 3500.00, 0, 3500.00, DATEADD(HOUR, -3, GETDATE())),
(@VendaId1, 2, 1, 89.90, 89.90, 0, 89.90, DATEADD(HOUR, -3, GETDATE()));

-- Pagamento da venda 1
INSERT INTO pagamentos (sale_id, method, payment_method, amount, status, paid_at, created_at)
VALUES (@VendaId1, 'CASH', 'DINHEIRO', 3589.90, 'APPROVED', DATEADD(HOUR, -3, GETDATE()), DATEADD(HOUR, -3, GETDATE()));

PRINT 'Venda 1 inserida: R$ 3.589,90 (Notebook + Mouse)';

-- ============================================
-- VENDA 2: Teclado Mecânico
-- ============================================
DECLARE @VendaId2 BIGINT;

INSERT INTO vendas (customer_id, user_id, cash_register_id, subtotal, discount, total, final_amount, total_amount, status, payment_method, created_at)
VALUES (2, 1, @CaixaId, 450.00, 0, 450.00, 450.00, 450.00, 'COMPLETED', 'CARTAO_CREDITO', DATEADD(HOUR, -2, GETDATE()));

SET @VendaId2 = SCOPE_IDENTITY();

-- Itens da venda 2
INSERT INTO itens_venda (sale_id, product_id, quantity, unit_price, subtotal, discount, total_price, created_at)
VALUES (@VendaId2, 3, 1, 450.00, 450.00, 0, 450.00, DATEADD(HOUR, -2, GETDATE()));

-- Pagamento da venda 2
INSERT INTO pagamentos (sale_id, method, payment_method, amount, status, paid_at, created_at)
VALUES (@VendaId2, 'CREDIT_CARD', 'CARTAO_CREDITO', 450.00, 'APPROVED', DATEADD(HOUR, -2, GETDATE()), DATEADD(HOUR, -2, GETDATE()));

PRINT 'Venda 2 inserida: R$ 450,00 (Teclado Mecânico)';

-- ============================================
-- VENDA 3: Monitor + Webcam
-- ============================================
DECLARE @VendaId3 BIGINT;

INSERT INTO vendas (customer_id, user_id, cash_register_id, subtotal, discount, total, final_amount, total_amount, status, payment_method, created_at)
VALUES (3, 1, @CaixaId, 1149.00, 50.00, 1099.00, 1099.00, 1099.00, 'COMPLETED', 'PIX', DATEADD(HOUR, -1, GETDATE()));

SET @VendaId3 = SCOPE_IDENTITY();

-- Itens da venda 3
INSERT INTO itens_venda (sale_id, product_id, quantity, unit_price, subtotal, discount, total_price, created_at)
VALUES 
(@VendaId3, 4, 1, 899.00, 899.00, 0, 899.00, DATEADD(HOUR, -1, GETDATE())),
(@VendaId3, 5, 1, 250.00, 250.00, 0, 250.00, DATEADD(HOUR, -1, GETDATE()));

-- Pagamento da venda 3
INSERT INTO pagamentos (sale_id, method, payment_method, amount, status, paid_at, created_at)
VALUES (@VendaId3, 'PIX', 'PIX', 1099.00, 'APPROVED', DATEADD(HOUR, -1, GETDATE()), DATEADD(HOUR, -1, GETDATE()));

PRINT 'Venda 3 inserida: R$ 1.099,00 (Monitor + Webcam) - Desconto R$ 50,00';

-- ============================================
-- VENDA 4: SSD + Memória RAM
-- ============================================
DECLARE @VendaId4 BIGINT;

INSERT INTO vendas (customer_id, user_id, cash_register_id, subtotal, discount, total, final_amount, total_amount, status, payment_method, created_at)
VALUES (4, 1, @CaixaId, 498.00, 0, 498.00, 498.00, 498.00, 'COMPLETED', 'CARTAO_DEBITO', DATEADD(MINUTE, -30, GETDATE()));

SET @VendaId4 = SCOPE_IDENTITY();

-- Itens da venda 4
INSERT INTO itens_venda (sale_id, product_id, quantity, unit_price, subtotal, discount, total_price, created_at)
VALUES 
(@VendaId4, 6, 1, 299.00, 299.00, 0, 299.00, DATEADD(MINUTE, -30, GETDATE())),
(@VendaId4, 7, 1, 199.00, 199.00, 0, 199.00, DATEADD(MINUTE, -30, GETDATE()));

-- Pagamento da venda 4
INSERT INTO pagamentos (sale_id, method, payment_method, amount, status, paid_at, created_at)
VALUES (@VendaId4, 'DEBIT_CARD', 'CARTAO_DEBITO', 498.00, 'APPROVED', DATEADD(MINUTE, -30, GETDATE()), DATEADD(MINUTE, -30, GETDATE()));

PRINT 'Venda 4 inserida: R$ 498,00 (SSD + Memória RAM)';

-- ============================================
-- VENDA 5: Headset + Mousepad
-- ============================================
DECLARE @VendaId5 BIGINT;

INSERT INTO vendas (customer_id, user_id, cash_register_id, subtotal, discount, total, final_amount, total_amount, status, payment_method, created_at)
VALUES (5, 1, @CaixaId, 224.00, 0, 224.00, 224.00, 224.00, 'COMPLETED', 'DINHEIRO', DATEADD(MINUTE, -10, GETDATE()));

SET @VendaId5 = SCOPE_IDENTITY();

-- Itens da venda 5
INSERT INTO itens_venda (sale_id, product_id, quantity, unit_price, subtotal, discount, total_price, created_at)
VALUES 
(@VendaId5, 10, 1, 179.00, 179.00, 0, 179.00, DATEADD(MINUTE, -10, GETDATE())),
(@VendaId5, 9, 1, 45.00, 45.00, 0, 45.00, DATEADD(MINUTE, -10, GETDATE()));

-- Pagamento da venda 5
INSERT INTO pagamentos (sale_id, method, payment_method, amount, status, paid_at, created_at)
VALUES (@VendaId5, 'CASH', 'DINHEIRO', 224.00, 'APPROVED', DATEADD(MINUTE, -10, GETDATE()), DATEADD(MINUTE, -10, GETDATE()));

PRINT 'Venda 5 inserida: R$ 224,00 (Headset + Mousepad)';

-- ============================================
-- RESUMO
-- ============================================
PRINT '';
PRINT '============================================';
PRINT 'VENDAS DO DIA INSERIDAS COM SUCESSO!';
PRINT '============================================';
PRINT '';

SELECT 
    COUNT(*) as 'Total de Vendas',
    SUM(total) as 'Faturamento Total',
    AVG(total) as 'Ticket Médio'
FROM vendas 
WHERE CAST(created_at AS DATE) = CAST(GETDATE() AS DATE);

PRINT '';
PRINT 'Detalhamento por forma de pagamento:';
SELECT 
    payment_method as 'Forma de Pagamento',
    COUNT(*) as 'Quantidade',
    SUM(total) as 'Total'
FROM vendas 
WHERE CAST(created_at AS DATE) = CAST(GETDATE() AS DATE)
GROUP BY payment_method;

PRINT '';
PRINT '============================================';
GO
