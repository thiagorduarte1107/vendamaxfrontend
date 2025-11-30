@echo off
echo ============================================
echo REINICIANDO SQL SERVER
echo ============================================
echo.

net stop MSSQLSERVER
timeout /t 3 /nobreak >nul
net start MSSQLSERVER

echo.
echo ============================================
echo SQL SERVER REINICIADO!
echo ============================================
echo.
echo Agora teste a conexao:
echo sqlcmd -S localhost -U vendamax_user -P "VendaMax@2024!" -d vendamax -Q "SELECT DB_NAME()"
echo.
pause
