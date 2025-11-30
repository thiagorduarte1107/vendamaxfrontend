package com.vendamax.mapper;

/**
 * Mapper para métodos de pagamento
 * Converte entre os formatos português (ENUM) e inglês (DB constraint)
 */
public class PaymentMethodMapper {

    /**
     * Converte método de pagamento do formato português (ENUM) para inglês (DB)
     * @param paymentMethodPt Método em português (DINHEIRO, CARTAO_CREDITO, etc)
     * @return Método em inglês (CASH, CREDIT_CARD, etc)
     */
    public static String toEnglish(String paymentMethodPt) {
        return switch (paymentMethodPt) {
            case "DINHEIRO" -> "CASH";
            case "CARTAO_CREDITO" -> "CREDIT_CARD";
            case "CARTAO_DEBITO" -> "DEBIT_CARD";
            case "PIX" -> "PIX";
            case "BOLETO" -> "CHECK";
            case "TRANSFERENCIA" -> "CHECK";
            default -> "CASH";
        };
    }

    /**
     * Converte método de pagamento do formato inglês (DB) para português (ENUM)
     * @param paymentMethodEn Método em inglês (CASH, CREDIT_CARD, etc)
     * @return Método em português (DINHEIRO, CARTAO_CREDITO, etc)
     */
    public static String toPortuguese(String paymentMethodEn) {
        return switch (paymentMethodEn) {
            case "CASH" -> "DINHEIRO";
            case "CREDIT_CARD" -> "CARTAO_CREDITO";
            case "DEBIT_CARD" -> "CARTAO_DEBITO";
            case "PIX" -> "PIX";
            case "CHECK" -> "BOLETO";
            default -> "DINHEIRO";
        };
    }
}
