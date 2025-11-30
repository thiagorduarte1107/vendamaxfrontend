-- ============================================
-- SCRIPT PARA CRIAR CAIXA PADRÃO
-- ============================================

USE vendamax;
GO

-- Inserir caixa padrão aberto
SET IDENTITY_INSERT caixas ON;

INSERT INTO caixas (id, user_id, opening_balance, closing_balance, opened_at, closed_at, status, notes, created_at, updated_at) VALUES
(1, 1, 0.00, NULL, GETDATE(), NULL, 'OPEN', 'Caixa padrão do sistema', GETDATE(), GETDATE());

SET IDENTITY_INSERT caixas OFF;
GO

-- Verificação
SELECT * FROM caixas;
GO

PRINT 'Caixa padrão criado com sucesso!';
GO
