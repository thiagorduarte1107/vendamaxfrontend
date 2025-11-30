@echo off
echo ============================================
echo TESTAR LOGIN - USUARIO TESTE
echo ============================================
echo.
echo Testando login com:
echo Email: teste@vendamax.com
echo Senha: password
echo.

curl -X POST http://localhost:8080/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"teste@vendamax.com\",\"password\":\"password\"}"

echo.
echo.
pause
