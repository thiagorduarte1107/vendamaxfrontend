package com.vendamax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * VendaMax - Sistema de Gestão Comercial
 * Aplicação Spring Boot
 * 
 * @author Thiago Duarte
 * @version 1.0.0
 */
@SpringBootApplication
public class VendaMaxApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendaMaxApplication.class, args);
        System.out.println("\n" +
                "╔══════════════════════════════════════════════════════════════╗\n" +
                "║                                                              ║\n" +
                "║              🚀 VENDAMAX API INICIADA! 🚀                    ║\n" +
                "║                                                              ║\n" +
                "║  📊 API REST: http://localhost:8080/api                      ║\n" +
                "║  📚 Swagger:  http://localhost:8080/api/swagger-ui.html     ║\n" +
                "║  🗄️  Banco:    SQL Server 2022 (vendamax)                    ║\n" +
                "║                                                              ║\n" +
                "╚══════════════════════════════════════════════════════════════╝\n");
    }
}
