@echo off
echo ============================================
echo RESETAR SENHA DO ADMIN
echo ============================================
echo.
echo Resetando senha para: admin123
echo.

sqlcmd -S localhost -U vendamax_user -P "VendaMax2024" -d vendamax -Q "UPDATE usuarios SET password = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy' WHERE email = 'admin@vendamax.com'; SELECT id, name, email, role, active FROM usuarios WHERE email = 'admin@vendamax.com';"

echo.
echo ============================================
echo SENHA RESETADA!
echo ============================================
echo.
echo Email: admin@vendamax.com
echo Senha: admin123
echo.
pause
