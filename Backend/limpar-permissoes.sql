-- ============================================
-- LIMPAR TABELA PERMISSOES
-- ============================================
-- Este script limpa a tabela permissoes para permitir
-- que o Hibernate adicione as novas colunas (active, updated_at)
-- ============================================

USE vendamax;
GO

-- Verificar quantos registros existem
SELECT COUNT(*) AS total_registros FROM permissoes;
GO

-- Limpar a tabela
DELETE FROM permissoes;
GO

-- Verificar se foi limpa
SELECT COUNT(*) AS total_registros_apos FROM permissoes;
GO

PRINT 'Tabela permissoes limpa com sucesso!';
PRINT 'Agora reinicie a aplicacao Spring Boot para que o Hibernate adicione as novas colunas.';
GO
