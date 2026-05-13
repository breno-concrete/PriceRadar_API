package com.breno.PriceRadar_API.controllers;

import com.breno.PriceRadar_API.DTOs.AlertResponseDTO;
import com.breno.PriceRadar_API.DTOs.PriceAlertRequestDTO;
import com.breno.PriceRadar_API.services.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/items/{itemId}/alerts")
@RequiredArgsConstructor
@Tag(name = "Alertas", description = "Gerenciamento dos alertas de preço de itens")
public class AlertController {

    private final AlertService alertService;

    @PostMapping
    @Operation(summary = "Criar novo alerta de preço",
               description = "Cria um novo alerta para um item rastreado que dispara quando o preço atinge o valor especificado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alerta criado com sucesso",
                    content = @Content(schema = @Schema(implementation = AlertResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na solicitação"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    public ResponseEntity<AlertResponseDTO> createAlert(
            @Parameter(description = "ID do item rastreado", example = "1", required = true)
            @PathVariable Long itemId,
            @RequestBody PriceAlertRequestDTO request) {
        AlertResponseDTO alertDTO = alertService.createAlert(itemId, request.triggeredPrice());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{alertId}")
                .buildAndExpand(alertDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(alertDTO);
    }

    @GetMapping
    @Operation(summary = "Listar alertas de um item",
               description = "Retorna todos os alertas associados a um item rastreado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alertas encontrados",
                    content = @Content(schema = @Schema(implementation = AlertResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    public ResponseEntity<List<AlertResponseDTO>> getAlertsByItem(
            @Parameter(description = "ID do item rastreado", example = "1", required = true)
            @PathVariable Long itemId) {
        List<AlertResponseDTO> alerts = alertService.getAlertsByItem(itemId);
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/{alertId}")
    @Operation(summary = "Obter detalhes de um alerta",
               description = "Retorna as informações detalhadas de um alerta específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alerta encontrado",
                    content = @Content(schema = @Schema(implementation = AlertResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item ou alerta não encontrado")
    })
    public ResponseEntity<AlertResponseDTO> getAlertById(
            @Parameter(description = "ID do item rastreado", example = "1", required = true)
            @PathVariable Long itemId,
            @Parameter(description = "ID do alerta", example = "1", required = true)
            @PathVariable Long alertId) {
        AlertResponseDTO alert = alertService.getAlertById(alertId);
        return ResponseEntity.ok(alert);
    }

    @DeleteMapping("/{alertId}")
    @Operation(summary = "Deletar um alerta",
               description = "Remove um alerta de preço do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Alerta deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item ou alerta não encontrado")
    })
    public ResponseEntity<Void> deleteAlert(
            @Parameter(description = "ID do item rastreado", example = "1", required = true)
            @PathVariable Long itemId,
            @Parameter(description = "ID do alerta", example = "1", required = true)
            @PathVariable Long alertId) {
        alertService.deleteAlert(alertId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{alertId}/read")
    @Operation(summary = "Marcar alerta como lido",
               description = "Marca um alerta como lido pelo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alerta marcado como lido",
                    content = @Content(schema = @Schema(implementation = AlertResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item ou alerta não encontrado")
    })
    public ResponseEntity<AlertResponseDTO> markAsRead(
            @Parameter(description = "ID do item rastreado", example = "1", required = true)
            @PathVariable Long itemId,
            @Parameter(description = "ID do alerta", example = "1", required = true)
            @PathVariable Long alertId) {
        AlertResponseDTO alert = alertService.markAsRead(alertId);
        
        return ResponseEntity.ok(alert);
    }
}
