-- ============================================
-- CORRIGIR TRIGGER DA TABELA PRODUTOS
-- ============================================

USE vendamax;
GO

-- Dropar trigger incorreta
IF EXISTS (SELECT * FROM sys.triggers WHERE name = 'trg_products_updated_at')
BEGIN
    DROP TRIGGER trg_products_updated_at;
    PRINT 'Trigger antiga removida.';
END
GO

-- Criar trigger correta
CREATE TRIGGER trg_produtos_updated_at ON produtos
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
    UPDATE produtos
    SET updated_at = GETDATE()
    FROM produtos p
    INNER JOIN inserted i ON p.id = i.id;
END;
GO

PRINT 'Trigger corrigida com sucesso!';
GO
