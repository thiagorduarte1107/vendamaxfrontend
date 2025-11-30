package com.vendamax.resource;

import com.vendamax.dto.response.ApiResponse;
import com.vendamax.dto.response.DashboardResponse;
import com.vendamax.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Resource: Dashboard
 */
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Métricas e estatísticas do sistema")
public class DashboardResource {

    private final DashboardService dashboardService;

    /**
     * Obter métricas do dashboard
     */
    @GetMapping("/metricas")
    @Operation(summary = "Métricas", description = "Obter todas as métricas do dashboard")
    public ResponseEntity<ApiResponse<DashboardResponse>> getMetricas() {
        DashboardResponse metricas = dashboardService.getMetricas();
        return ResponseEntity.ok(ApiResponse.success(metricas));
    }
}
