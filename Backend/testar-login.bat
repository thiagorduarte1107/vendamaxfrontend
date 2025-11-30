@echo off
echo ============================================
echo TESTAR LOGIN
echo ============================================
echo.
echo Testando login com:
echo Email: admin@vendamax.com
echo Senha: admin123
echo.

curl -X POST http://localhost:8080/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"admin@vendamax.com\",\"password\":\"admin123\"}"

echo.
echo.
pause
