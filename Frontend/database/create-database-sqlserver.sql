-- =====================================================
-- VENDAMAX - SISTEMA DE GESTÃO COMERCIAL
-- Banco de Dados: SQL Server 2019+
-- Versão: 1.0
-- Data: 26/11/2025
-- =====================================================

-- Criar banco de dados
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'vendamax')
BEGIN
    CREATE DATABASE vendamax;
END
GO

USE vendamax;
GO

-- =====================================================
-- TABELA: categories (Categorias de Produtos)
-- =====================================================
CREATE TABLE categories (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(500),
    active BIT NOT NULL DEFAULT 1,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

-- =====================================================
-- TABELA: products (Produtos)
-- =====================================================
CREATE TABLE products (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(200) NOT NULL,
    description NVARCHAR(MAX),
    barcode NVARCHAR(50) UNIQUE,
    category_id BIGINT,
    cost_price DECIMAL(10,2) NOT NULL CHECK (cost_price >= 0),
    sale_price DECIMAL(10,2) NOT NULL CHECK (sale_price >= 0),
    stock_quantity INT NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
    min_stock INT NOT NULL DEFAULT 0 CHECK (min_stock >= 0),
    max_stock INT,
    unit NVARCHAR(20) NOT NULL DEFAULT 'UN',
    image_url NVARCHAR(500),
    active BIT NOT NULL DEFAULT 1,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_products_category FOREIGN KEY (category_id) 
        REFERENCES categories(id) ON DELETE SET NULL
);
GO

-- =====================================================
-- TABELA: customers (Clientes)
-- =====================================================
CREATE TABLE customers (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(200) NOT NULL,
    email NVARCHAR(100) UNIQUE,
    phone NVARCHAR(20),
    cpf_cnpj NVARCHAR(18) UNIQUE,
    address NVARCHAR(500),
    city NVARCHAR(100),
    state NVARCHAR(2),
    zip_code NVARCHAR(10),
    credit_limit DECIMAL(10,2) DEFAULT 0 CHECK (credit_limit >= 0),
    current_debt DECIMAL(10,2) DEFAULT 0 CHECK (current_debt >= 0),
    active BIT NOT NULL DEFAULT 1,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

-- =====================================================
-- TABELA: users (Usuários do Sistema)
-- =====================================================
CREATE TABLE users (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(200) NOT NULL,
    email NVARCHAR(100) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    role NVARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'MANAGER', 'SELLER', 'CASHIER')),
    active BIT NOT NULL DEFAULT 1,
    last_login DATETIME2,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

-- =====================================================
-- TABELA: permissions (Permissões do Sistema)
-- =====================================================
CREATE TABLE permissions (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(50) NOT NULL UNIQUE,
    description NVARCHAR(200),
    module NVARCHAR(50) NOT NULL,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

-- =====================================================
-- TABELA: user_permissions (Permissões dos Usuários)
-- =====================================================
CREATE TABLE user_permissions (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    granted_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    granted_by BIGINT,
    CONSTRAINT FK_user_permissions_user FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT FK_user_permissions_permission FOREIGN KEY (permission_id) 
        REFERENCES permissions(id) ON DELETE CASCADE,
    CONSTRAINT FK_user_permissions_granted_by FOREIGN KEY (granted_by) 
        REFERENCES users(id) ON DELETE NO ACTION,
    CONSTRAINT UQ_user_permission UNIQUE (user_id, permission_id)
);
GO

-- =====================================================
-- TABELA: cash_registers (Caixas)
-- =====================================================
CREATE TABLE cash_registers (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL,
    opening_balance DECIMAL(10,2) NOT NULL DEFAULT 0 CHECK (opening_balance >= 0),
    closing_balance DECIMAL(10,2) CHECK (closing_balance >= 0),
    status NVARCHAR(20) NOT NULL CHECK (status IN ('OPEN', 'CLOSED')),
    opened_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    closed_at DATETIME2,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_cash_registers_user FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE NO ACTION
);
GO

-- =====================================================
-- TABELA: cash_movements (Movimentações de Caixa)
-- =====================================================
CREATE TABLE cash_movements (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cash_register_id BIGINT NOT NULL,
    type NVARCHAR(20) NOT NULL CHECK (type IN ('DEPOSIT', 'WITHDRAWAL', 'SALE')),
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0),
    description NVARCHAR(500),
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_cash_movements_register FOREIGN KEY (cash_register_id) 
        REFERENCES cash_registers(id) ON DELETE CASCADE
);
GO

-- =====================================================
-- TABELA: sales (Vendas)
-- =====================================================
CREATE TABLE sales (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    customer_id BIGINT,
    user_id BIGINT NOT NULL,
    cash_register_id BIGINT,
    subtotal DECIMAL(10,2) NOT NULL CHECK (subtotal >= 0),
    discount DECIMAL(10,2) DEFAULT 0 CHECK (discount >= 0),
    total DECIMAL(10,2) NOT NULL CHECK (total >= 0),
    status NVARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'COMPLETED', 'CANCELLED')),
    payment_method NVARCHAR(50),
    notes NVARCHAR(MAX),
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_sales_customer FOREIGN KEY (customer_id) 
        REFERENCES customers(id) ON DELETE SET NULL,
    CONSTRAINT FK_sales_user FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE NO ACTION,
    CONSTRAINT FK_sales_cash_register FOREIGN KEY (cash_register_id) 
        REFERENCES cash_registers(id) ON DELETE SET NULL
);
GO

-- =====================================================
-- TABELA: sale_items (Itens da Venda)
-- =====================================================
CREATE TABLE sale_items (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    sale_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10,2) NOT NULL CHECK (unit_price >= 0),
    subtotal DECIMAL(10,2) NOT NULL CHECK (subtotal >= 0),
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_sale_items_sale FOREIGN KEY (sale_id) 
        REFERENCES sales(id) ON DELETE CASCADE,
    CONSTRAINT FK_sale_items_product FOREIGN KEY (product_id) 
        REFERENCES products(id) ON DELETE NO ACTION
);
GO

-- =====================================================
-- TABELA: payments (Pagamentos)
-- =====================================================
CREATE TABLE payments (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    sale_id BIGINT NOT NULL,
    method NVARCHAR(50) NOT NULL CHECK (method IN ('CASH', 'CREDIT_CARD', 'DEBIT_CARD', 'PIX', 'CHECK')),
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0),
    installments INT DEFAULT 1 CHECK (installments > 0),
    status NVARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_payments_sale FOREIGN KEY (sale_id) 
        REFERENCES sales(id) ON DELETE CASCADE
);
GO

-- =====================================================
-- TABELA: stock_movements (Movimentações de Estoque)
-- =====================================================
CREATE TABLE stock_movements (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    product_id BIGINT NOT NULL,
    type NVARCHAR(20) NOT NULL CHECK (type IN ('IN', 'OUT', 'ADJUSTMENT')),
    quantity INT NOT NULL,
    reason NVARCHAR(500),
    user_id BIGINT NOT NULL,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_stock_movements_product FOREIGN KEY (product_id) 
        REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT FK_stock_movements_user FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE NO ACTION
);
GO

-- =====================================================
-- TABELA: accounts_receivable (Contas a Receber)
-- =====================================================
CREATE TABLE accounts_receivable (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    description NVARCHAR(500) NOT NULL,
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0),
    due_date DATE NOT NULL,
    paid_date DATE,
    status NVARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'PAID', 'OVERDUE', 'CANCELLED')),
    category NVARCHAR(50),
    notes NVARCHAR(MAX),
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_accounts_receivable_customer FOREIGN KEY (customer_id) 
        REFERENCES customers(id) ON DELETE NO ACTION
);
GO

-- =====================================================
-- TABELA: accounts_payable (Contas a Pagar)
-- =====================================================
CREATE TABLE accounts_payable (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    description NVARCHAR(500) NOT NULL,
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0),
    due_date DATE NOT NULL,
    paid_date DATE,
    status NVARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'PAID', 'OVERDUE', 'CANCELLED')),
    category NVARCHAR(50),
    supplier NVARCHAR(200),
    notes NVARCHAR(MAX),
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

-- =====================================================
-- TABELA: activity_logs (Logs de Atividades)
-- =====================================================
CREATE TABLE activity_logs (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT,
    action NVARCHAR(100) NOT NULL,
    entity_type NVARCHAR(50),
    entity_id BIGINT,
    description NVARCHAR(MAX),
    ip_address NVARCHAR(45),
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_activity_logs_user FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE SET NULL
);
GO

-- =====================================================
-- TABELA: notifications (Notificações)
-- =====================================================
CREATE TABLE notifications (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title NVARCHAR(200) NOT NULL,
    message NVARCHAR(MAX) NOT NULL,
    type NVARCHAR(20) NOT NULL CHECK (type IN ('INFO', 'WARNING', 'ERROR', 'SUCCESS')),
    is_read BIT NOT NULL DEFAULT 0,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_notifications_user FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE CASCADE
);
GO

-- =====================================================
-- TABELA: backups (Backups do Sistema)
-- =====================================================
CREATE TABLE backups (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    filename NVARCHAR(500) NOT NULL,
    size_bytes BIGINT NOT NULL,
    type NVARCHAR(20) NOT NULL CHECK (type IN ('FULL', 'INCREMENTAL')),
    status NVARCHAR(20) NOT NULL CHECK (status IN ('SUCCESS', 'FAILED', 'IN_PROGRESS')),
    created_by BIGINT,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_backups_user FOREIGN KEY (created_by) 
        REFERENCES users(id) ON DELETE SET NULL
);
GO

-- =====================================================
-- ÍNDICES PARA PERFORMANCE
-- =====================================================

-- Produtos
CREATE INDEX idx_products_name ON products(name);
CREATE INDEX idx_products_barcode ON products(barcode);
CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_active ON products(active);
GO

-- Clientes
CREATE INDEX idx_customers_name ON customers(name);
CREATE INDEX idx_customers_cpf_cnpj ON customers(cpf_cnpj);
CREATE INDEX idx_customers_email ON customers(email);
GO

-- Usuários
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
GO

-- Vendas
CREATE INDEX idx_sales_customer ON sales(customer_id);
CREATE INDEX idx_sales_user ON sales(user_id);
CREATE INDEX idx_sales_created_at ON sales(created_at);
CREATE INDEX idx_sales_status ON sales(status);
GO

-- Itens de Venda
CREATE INDEX idx_sale_items_sale ON sale_items(sale_id);
CREATE INDEX idx_sale_items_product ON sale_items(product_id);
GO

-- Movimentações de Estoque
CREATE INDEX idx_stock_movements_product ON stock_movements(product_id);
CREATE INDEX idx_stock_movements_created_at ON stock_movements(created_at);
GO

-- Contas a Receber
CREATE INDEX idx_accounts_receivable_customer ON accounts_receivable(customer_id);
CREATE INDEX idx_accounts_receivable_status ON accounts_receivable(status);
CREATE INDEX idx_accounts_receivable_due_date ON accounts_receivable(due_date);
GO

-- Contas a Pagar
CREATE INDEX idx_accounts_payable_status ON accounts_payable(status);
CREATE INDEX idx_accounts_payable_due_date ON accounts_payable(due_date);
GO

-- Logs de Atividades
CREATE INDEX idx_activity_logs_user ON activity_logs(user_id);
CREATE INDEX idx_activity_logs_created_at ON activity_logs(created_at);
GO

-- =====================================================
-- TRIGGER: Atualizar updated_at automaticamente
-- =====================================================
CREATE OR ALTER TRIGGER trg_categories_updated_at ON categories
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
    UPDATE categories
    SET updated_at = GETDATE()
    FROM categories c
    INNER JOIN inserted i ON c.id = i.id;
END;
GO

CREATE OR ALTER TRIGGER trg_products_updated_at ON products
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
    UPDATE products
    SET updated_at = GETDATE()
    FROM products p
    INNER JOIN inserted i ON p.id = i.id;
END;
GO

CREATE OR ALTER TRIGGER trg_customers_updated_at ON customers
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
    UPDATE customers
    SET updated_at = GETDATE()
    FROM customers c
    INNER JOIN inserted i ON c.id = i.id;
END;
GO

CREATE OR ALTER TRIGGER trg_users_updated_at ON users
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
    UPDATE users
    SET updated_at = GETDATE()
    FROM users u
    INNER JOIN inserted i ON u.id = i.id;
END;
GO

CREATE OR ALTER TRIGGER trg_cash_registers_updated_at ON cash_registers
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
    UPDATE cash_registers
    SET updated_at = GETDATE()
    FROM cash_registers cr
    INNER JOIN inserted i ON cr.id = i.id;
END;
GO

CREATE OR ALTER TRIGGER trg_sales_updated_at ON sales
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
    UPDATE sales
    SET updated_at = GETDATE()
    FROM sales s
    INNER JOIN inserted i ON s.id = i.id;
END;
GO

CREATE OR ALTER TRIGGER trg_accounts_receivable_updated_at ON accounts_receivable
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
    UPDATE accounts_receivable
    SET updated_at = GETDATE()
    FROM accounts_receivable ar
    INNER JOIN inserted i ON ar.id = i.id;
END;
GO

CREATE OR ALTER TRIGGER trg_accounts_payable_updated_at ON accounts_payable
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
    UPDATE accounts_payable
    SET updated_at = GETDATE()
    FROM accounts_payable ap
    INNER JOIN inserted i ON ap.id = i.id;
END;
GO

-- =====================================================
-- VIEWS PARA RELATÓRIOS
-- =====================================================

-- View: Produtos com estoque baixo
CREATE OR ALTER VIEW vw_low_stock_products AS
SELECT 
    p.id,
    p.name,
    p.barcode,
    c.name AS category_name,
    p.stock_quantity,
    p.min_stock,
    p.sale_price
FROM products p
LEFT JOIN categories c ON p.category_id = c.id
WHERE p.active = 1 
  AND p.stock_quantity <= p.min_stock;
GO

-- View: Vendas do dia
CREATE OR ALTER VIEW vw_daily_sales AS
SELECT 
    s.id,
    s.total,
    s.payment_method,
    s.status,
    u.name AS seller_name,
    c.name AS customer_name,
    s.created_at
FROM sales s
INNER JOIN users u ON s.user_id = u.id
LEFT JOIN customers c ON s.customer_id = c.id
WHERE CAST(s.created_at AS DATE) = CAST(GETDATE() AS DATE);
GO

-- View: Contas vencidas
CREATE OR ALTER VIEW vw_overdue_accounts AS
SELECT 
    'RECEIVABLE' AS type,
    ar.id,
    ar.description,
    ar.amount,
    ar.due_date,
    c.name AS customer_name
FROM accounts_receivable ar
INNER JOIN customers c ON ar.customer_id = c.id
WHERE ar.status = 'PENDING' 
  AND ar.due_date < CAST(GETDATE() AS DATE)
UNION ALL
SELECT 
    'PAYABLE' AS type,
    ap.id,
    ap.description,
    ap.amount,
    ap.due_date,
    ap.supplier AS customer_name
FROM accounts_payable ap
WHERE ap.status = 'PENDING' 
  AND ap.due_date < CAST(GETDATE() AS DATE);
GO

-- View: Métricas do Dashboard
CREATE OR ALTER VIEW vw_dashboard_metrics AS
SELECT 
    (SELECT COUNT(*) FROM products WHERE active = 1) AS total_products,
    (SELECT COUNT(*) FROM customers WHERE active = 1) AS total_customers,
    (SELECT COUNT(*) FROM sales WHERE CAST(created_at AS DATE) = CAST(GETDATE() AS DATE)) AS sales_today,
    (SELECT ISNULL(SUM(total), 0) FROM sales WHERE CAST(created_at AS DATE) = CAST(GETDATE() AS DATE) AND status = 'COMPLETED') AS revenue_today,
    (SELECT COUNT(*) FROM products WHERE active = 1 AND stock_quantity <= min_stock) AS low_stock_count,
    (SELECT COUNT(*) FROM accounts_receivable WHERE status = 'PENDING' AND due_date < CAST(GETDATE() AS DATE)) AS overdue_receivables,
    (SELECT COUNT(*) FROM accounts_payable WHERE status = 'PENDING' AND due_date < CAST(GETDATE() AS DATE)) AS overdue_payables;
GO

-- =====================================================
-- DADOS INICIAIS (SEEDS)
-- =====================================================

-- Permissões
INSERT INTO permissions (name, description, module) VALUES
('dashboard.view', 'Visualizar dashboard', 'dashboard'),
('products.view', 'Visualizar produtos', 'products'),
('products.create', 'Criar produtos', 'products'),
('products.edit', 'Editar produtos', 'products'),
('products.delete', 'Excluir produtos', 'products'),
('sales.view', 'Visualizar vendas', 'sales'),
('sales.create', 'Criar vendas', 'sales'),
('customers.view', 'Visualizar clientes', 'customers'),
('customers.create', 'Criar clientes', 'customers'),
('reports.view', 'Visualizar relatórios', 'reports'),
('settings.view', 'Visualizar configurações', 'settings'),
('users.manage', 'Gerenciar usuários', 'users');
GO

-- Usuário Admin (senha: admin123)
INSERT INTO users (name, email, password, role, active) VALUES
('Administrador', 'admin@vendamax.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', 1);
GO

-- Categorias Iniciais
INSERT INTO categories (name, description, active) VALUES
('Eletrônicos', 'Produtos eletrônicos e tecnologia', 1),
('Alimentos', 'Produtos alimentícios', 1),
('Bebidas', 'Bebidas em geral', 1),
('Limpeza', 'Produtos de limpeza', 1);
GO

PRINT 'Banco de dados VendaMax criado com sucesso!';
PRINT 'Usuário admin: admin@vendamax.com';
PRINT 'Senha: admin123';
GO
