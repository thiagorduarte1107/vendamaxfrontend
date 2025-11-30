/**
 * Mapper para métodos de pagamento
 * Converte entre os valores do frontend e os enums do backend
 */
export class PaymentMethodMapper {
  private static readonly FRONTEND_TO_BACKEND: { [key: string]: string } = {
    'money': 'DINHEIRO',
    'credit': 'CARTAO_CREDITO',
    'debit': 'CARTAO_DEBITO',
    'pix': 'PIX',
    'boleto': 'BOLETO',
    'transfer': 'TRANSFERENCIA'
  };

  private static readonly BACKEND_TO_FRONTEND: { [key: string]: string } = {
    'DINHEIRO': 'money',
    'CARTAO_CREDITO': 'credit',
    'CARTAO_DEBITO': 'debit',
    'PIX': 'pix',
    'BOLETO': 'boleto',
    'TRANSFERENCIA': 'transfer'
  };

  /**
   * Converte método de pagamento do frontend para o backend
   */
  static toBackend(frontendMethod: string): string {
    return this.FRONTEND_TO_BACKEND[frontendMethod] || 'DINHEIRO';
  }

  /**
   * Converte método de pagamento do backend para o frontend
   */
  static toFrontend(backendMethod: string): string {
    return this.BACKEND_TO_FRONTEND[backendMethod] || 'money';
  }
}
