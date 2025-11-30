-- ============================================
-- CRIAR USUÁRIO ADMIN DE TESTE
-- ============================================
-- Senha: admin123 (criptografada com BCrypt)
-- ============================================

USE vendamax;
GO

-- Inserir usuário admin
INSERT INTO usuarios (name, email, password, role, active, created_at, updated_at)
VALUES (
    'Admin Teste',
    'admin@vendamax.com',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', -- senha: admin123
    'ADMIN',
    1,
    GETDATE(),
    GETDATE()
);
GO

-- Verificar se foi criado
SELECT id, name, email, role, active, created_at
FROM usuarios
WHERE email = 'admin@vendamax.com';
GO

PRINT 'Usuario admin criado com sucesso!';
PRINT 'Email: admin@vendamax.com';
PRINT 'Senha: admin123';
GO
