package ar.gob.pueblogeneralbelgrano.municipalidad.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@RestController
public class HealthCheckController {

    private DataSource dataSource;

    public HealthCheckController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Endpoint que comprueba el estado de la api
     *
     * @return estado de la api
     */
    @Operation(summary = "Obtener estado de la api",
            description = "Devuelve el estado de la api (Mantenimiento / En funcionamiento)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API Funcionando"),
            @ApiResponse(responseCode = "503", description = "API en mantenimiento"),
    })
    @GetMapping("/health")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Map<String,String>> healthCheck(){
        try (Connection connection = dataSource.getConnection()){
            return ResponseEntity.ok(Map.of("message", "API Funcionando"));
        } catch (SQLException error){
            return ResponseEntity.status(503).body(Map.of("message", "API en mantenimiento"));
        }
    }
}
