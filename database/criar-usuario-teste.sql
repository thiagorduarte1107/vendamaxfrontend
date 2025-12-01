-- ============================================
-- CRIAR USUÁRIO TESTE
-- ============================================
USE vendamax;
GO

-- Deletar se existir
DELETE FROM usuarios WHERE email = 'teste@vendamax.com';
GO

-- Inserir novo usuário
-- Senha: teste123 (hash BCrypt)
INSERT INTO usuarios (name, email, password, role, active, created_at, updated_at)
VALUES (
    'Usuario Teste',
    'teste@vendamax.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', -- senha: password
    'ADMIN',
    1,
    GETDATE(),
    GETDATE()
);
GO

SELECT id, name, email, role, active FROM usuarios WHERE email = 'teste@vendamax.com';
GO

PRINT 'Usuario teste criado!';
PRINT 'Email: teste@vendamax.com';
PRINT 'Senha: password';
GO
