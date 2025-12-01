-- =====================================================
-- HABILITAR AUTENTICAÇÃO SQL SERVER
-- Execute este script no SQL Server Management Studio (SSMS)
-- Conecte com autenticação Windows
-- =====================================================

USE master;
GO

-- 1. Habilitar modo de autenticação mista
EXEC xp_instance_regwrite 
    N'HKEY_LOCAL_MACHINE', 
    N'Software\Microsoft\MSSQLServer\MSSQLServer',
    N'LoginMode', 
    REG_DWORD, 
    2;
GO

-- 2. Habilitar auditoria
EXEC xp_instance_regwrite 
    N'HKEY_LOCAL_MACHINE',
    N'Software\Microsoft\MSSQLServer\MSSQLServer',
    N'AuditLevel',
    REG_DWORD,
    2;
GO

-- 3. Habilitar e configurar usuário sa
ALTER LOGIN sa ENABLE;
GO

ALTER LOGIN sa WITH PASSWORD = 'VendaMax@2024!';
GO

-- 4. Criar usuário vendamax_user
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = 'vendamax_user')
BEGIN
    CREATE LOGIN vendamax_user WITH PASSWORD = 'VendaMax@2024!';
END
GO

-- 5. Configurar permissões no banco vendamax
USE vendamax;
GO

IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = 'vendamax_user')
BEGIN
    CREATE USER vendamax_user FOR LOGIN vendamax_user;
END
GO

ALTER ROLE db_owner ADD MEMBER vendamax_user;
GO

-- 6. Verificar configurações
SELECT 
    name,
    is_disabled,
    create_date,
    modify_date
FROM sys.sql_logins
WHERE name IN ('sa', 'vendamax_user');
GO

PRINT '============================================';
PRINT 'CONFIGURAÇÃO CONCLUÍDA!';
PRINT '============================================';
PRINT 'Usuário: vendamax_user';
PRINT 'Senha: VendaMax@2024!';
PRINT 'Banco: vendamax';
PRINT '';
PRINT 'IMPORTANTE: Reinicie o SQL Server!';
PRINT 'PowerShell (Admin): Restart-Service MSSQLSERVER';
PRINT '============================================';
GO
