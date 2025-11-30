-- ============================================
-- SCRIPT DE DADOS DE TESTE - VENDAMAX
-- ============================================

USE vendamax;
GO

-- Limpar dados existentes (cuidado em produção!)
DELETE FROM itens_venda;
DELETE FROM pagamentos;
DELETE FROM vendas;
DELETE FROM produtos;
DELETE FROM categorias;
DELETE FROM clientes;
GO

-- ============================================
-- 1. CATEGORIAS
-- ============================================
SET IDENTITY_INSERT categorias ON;

INSERT INTO categorias (id, name, description, active, created_at, updated_at) VALUES
(1, 'Eletrônicos', 'Produtos eletrônicos e tecnologia', 1, GETDATE(), GETDATE()),
(2, 'Informática', 'Computadores e periféricos', 1, GETDATE(), GETDATE()),
(3, 'Acessórios', 'Acessórios diversos', 1, GETDATE(), GETDATE()),
(4, 'Escritório', 'Material de escritório', 1, GETDATE(), GETDATE()),
(5, 'Games', 'Jogos e consoles', 1, GETDATE(), GETDATE());

SET IDENTITY_INSERT categorias OFF;
GO

-- ============================================
-- 2. PRODUTOS
-- ============================================
SET IDENTITY_INSERT produtos ON;

INSERT INTO produtos (id, name, description, sku, barcode, category_id, cost_price, sale_price, price, stock, min_stock, unit, active, created_at, updated_at) VALUES
-- Eletrônicos
(1, 'Notebook Dell Inspiron 15', 'Intel i5, 8GB RAM, 256GB SSD', 'NB-DELL-001', '7891234567890', 2, 2800.00, 3500.00, 3500.00, 15, 3, 'UN', 1, GETDATE(), GETDATE()),
(2, 'Mouse Logitech MX Master', 'Mouse sem fio ergonômico', 'MS-LOG-001', '7891234567891', 3, 65.00, 89.90, 89.90, 50, 10, 'UN', 1, GETDATE(), GETDATE()),
(3, 'Teclado Mecânico RGB', 'Teclado gamer com iluminação RGB', 'KB-MEC-001', '7891234567892', 3, 320.00, 450.00, 450.00, 25, 5, 'UN', 1, GETDATE(), GETDATE()),
(4, 'Monitor LG 24" Full HD', 'Monitor LED 24 polegadas', 'MON-LG-001', '7891234567893', 2, 650.00, 899.00, 899.00, 12, 3, 'UN', 1, GETDATE(), GETDATE()),
(5, 'Webcam Logitech C920', 'Webcam Full HD 1080p', 'WEB-LOG-001', '7891234567894', 1, 180.00, 250.00, 250.00, 30, 5, 'UN', 1, GETDATE(), GETDATE()),

-- Informática
(6, 'SSD Kingston 480GB', 'SSD SATA III 480GB', 'SSD-KIN-001', '7891234567895', 2, 220.00, 299.00, 299.00, 40, 10, 'UN', 1, GETDATE(), GETDATE()),
(7, 'Memória RAM 8GB DDR4', 'Memória RAM DDR4 2666MHz', 'RAM-DDR4-001', '7891234567896', 2, 150.00, 199.00, 199.00, 35, 10, 'UN', 1, GETDATE(), GETDATE()),
(8, 'HD Externo 1TB', 'HD Externo portátil USB 3.0', 'HD-EXT-001', '7891234567897', 2, 250.00, 349.00, 349.00, 20, 5, 'UN', 1, GETDATE(), GETDATE()),

-- Acessórios
(9, 'Mousepad Gamer Grande', 'Mousepad 70x30cm', 'MP-GAM-001', '7891234567898', 3, 25.00, 45.00, 45.00, 60, 15, 'UN', 1, GETDATE(), GETDATE()),
(10, 'Headset Gamer RGB', 'Headset com microfone e LED', 'HS-GAM-001', '7891234567899', 3, 120.00, 179.00, 179.00, 28, 8, 'UN', 1, GETDATE(), GETDATE()),
(11, 'Cabo HDMI 2.0 - 2m', 'Cabo HDMI 4K 2 metros', 'CAB-HDMI-001', '7891234567800', 3, 15.00, 29.90, 29.90, 100, 20, 'UN', 1, GETDATE(), GETDATE()),
(12, 'Hub USB 3.0 4 Portas', 'Hub USB com 4 portas', 'HUB-USB-001', '7891234567801', 3, 35.00, 59.90, 59.90, 45, 10, 'UN', 1, GETDATE(), GETDATE()),

-- Escritório
(13, 'Cadeira Gamer Preta', 'Cadeira ergonômica para escritório', 'CAD-GAM-001', '7891234567802', 4, 450.00, 699.00, 699.00, 8, 2, 'UN', 1, GETDATE(), GETDATE()),
(14, 'Mesa para Computador', 'Mesa com suporte para monitor', 'MES-COM-001', '7891234567803', 4, 280.00, 449.00, 449.00, 10, 2, 'UN', 1, GETDATE(), GETDATE()),
(15, 'Luminária LED USB', 'Luminária flexível USB', 'LUM-LED-001', '7891234567804', 4, 25.00, 49.90, 49.90, 50, 10, 'UN', 1, GETDATE(), GETDATE()),

-- Games
(16, 'Controle Xbox Wireless', 'Controle sem fio para Xbox', 'CTR-XBX-001', '7891234567805', 5, 280.00, 399.00, 399.00, 18, 5, 'UN', 1, GETDATE(), GETDATE()),
(17, 'Jogo FIFA 2024', 'Jogo FIFA 2024 para Xbox', 'JOG-FIFA-001', '7891234567806', 5, 180.00, 249.00, 249.00, 25, 5, 'UN', 1, GETDATE(), GETDATE()),
(18, 'Volante Logitech G29', 'Volante com pedais para simuladores', 'VOL-LOG-001', '7891234567807', 5, 1200.00, 1699.00, 1699.00, 5, 2, 'UN', 1, GETDATE(), GETDATE());

SET IDENTITY_INSERT produtos OFF;
GO

-- ============================================
-- 3. CLIENTES
-- ============================================
SET IDENTITY_INSERT clientes ON;

INSERT INTO clientes (id, name, email, phone, cpf_cnpj, cpf, cnpj, address, city, state, zipcode, notes, active, created_at, updated_at) VALUES
(1, 'João Silva Santos', 'joao.silva@email.com', '(11) 98765-4321', '12345678901', '12345678901', 'CNPJ001', 'Rua das Flores, 123', 'São Paulo', 'SP', '01234-567', 'Cliente VIP', 1, GETDATE(), GETDATE()),
(2, 'Maria Oliveira Costa', 'maria.oliveira@email.com', '(11) 97654-3210', '98765432109', '98765432109', 'CNPJ002', 'Av. Paulista, 1000', 'São Paulo', 'SP', '01310-100', 'Cliente frequente', 1, GETDATE(), GETDATE()),
(3, 'TechStore Ltda', 'contato@techstore.com', '(11) 3456-7890', '12345678000190', 'CPF003', '12345678000190', 'Rua do Comércio, 500', 'São Paulo', 'SP', '01234-000', 'Revenda', 1, GETDATE(), GETDATE()),
(4, 'Carlos Eduardo Lima', 'carlos.lima@email.com', '(11) 96543-2109', '45678912301', '45678912301', 'CNPJ004', 'Rua Augusta, 789', 'São Paulo', 'SP', '01305-100', '', 1, GETDATE(), GETDATE()),
(5, 'Ana Paula Ferreira', 'ana.ferreira@email.com', '(11) 95432-1098', '78912345601', '78912345601', 'CNPJ005', 'Rua Oscar Freire, 456', 'São Paulo', 'SP', '01426-001', 'Preferência por eletrônicos', 1, GETDATE(), GETDATE()),
(6, 'InfoTech Comércio', 'vendas@infotech.com', '(11) 3789-4560', '98765432000180', 'CPF006', '98765432000180', 'Av. Faria Lima, 2000', 'São Paulo', 'SP', '01452-000', 'Compra em grande quantidade', 1, GETDATE(), GETDATE()),
(7, 'Pedro Henrique Souza', 'pedro.souza@email.com', '(11) 94321-0987', '32165498701', '32165498701', 'CNPJ007', 'Rua Consolação, 234', 'São Paulo', 'SP', '01301-000', '', 1, GETDATE(), GETDATE()),
(8, 'Juliana Martins', 'juliana.martins@email.com', '(11) 93210-9876', '65432198701', '65432198701', 'CNPJ008', 'Av. Rebouças, 1500', 'São Paulo', 'SP', '05402-100', 'Cliente desde 2020', 1, GETDATE(), GETDATE()),
(9, 'GameStore Brasil', 'contato@gamestore.com.br', '(11) 3654-9870', '45678912000170', 'CPF009', '45678912000170', 'Rua dos Games, 100', 'São Paulo', 'SP', '01234-100', 'Especializada em games', 1, GETDATE(), GETDATE()),
(10, 'Roberto Alves', 'roberto.alves@email.com', '(11) 92109-8765', '14725836901', '14725836901', 'CNPJ010', 'Rua Haddock Lobo, 678', 'São Paulo', 'SP', '01414-001', '', 1, GETDATE(), GETDATE());

SET IDENTITY_INSERT clientes OFF;
GO

-- ============================================
-- VERIFICAÇÃO
-- ============================================
PRINT '============================================';
PRINT 'DADOS DE TESTE INSERIDOS COM SUCESSO!';
PRINT '============================================';
PRINT '';
SELECT 'Categorias inseridas: ' + CAST(COUNT(*) AS VARCHAR(10)) FROM categorias;
SELECT 'Produtos inseridos: ' + CAST(COUNT(*) AS VARCHAR(10)) FROM produtos;
SELECT 'Clientes inseridos: ' + CAST(COUNT(*) AS VARCHAR(10)) FROM clientes;
PRINT '';
PRINT '============================================';
GO
