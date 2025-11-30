@echo off
echo ============================================
echo LIMPAR TABELA PERMISSOES
echo ============================================
echo.
echo Este script vai limpar a tabela permissoes
echo para permitir que o Hibernate adicione as
echo novas colunas (active, updated_at).
echo.
echo ATENCAO: Todos os registros serao deletados!
echo.
pause

echo.
echo Executando script SQL...
echo.

sqlcmd -S localhost -U vendamax_user -P "VendaMax2024" -d vendamax -i limpar-permissoes.sql

echo.
echo ============================================
echo CONCLUIDO!
echo ============================================
echo.
echo Agora reinicie a aplicacao Spring Boot.
echo As novas colunas serao adicionadas automaticamente.
echo.
pause
