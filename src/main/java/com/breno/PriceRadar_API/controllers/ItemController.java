package com.breno.PriceRadar_API.controllers;

import com.breno.PriceRadar_API.DTOs.ItemResponseDTO;
import com.breno.PriceRadar_API.DTOs.SnapshotHistoryResponseDTO;
import com.breno.PriceRadar_API.DTOs.SnapshotRequestDTO;
import com.breno.PriceRadar_API.DTOs.TrackedItemRequestDTO;
import com.breno.PriceRadar_API.services.SnapshotService;
import com.breno.PriceRadar_API.services.TrackedItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
@Tag(name = "Itens Rastreados", description = "Gerenciamento dos itens que estão sob monitoramento de preços")
public class ItemController {

    private final TrackedItemService trackedItemService;
    private final SnapshotService snapshotService;

    @PostMapping
    @Operation(summary = "Criar novo item rastreado",
               description = "Cria um novo item para monitoramento de preços com um preço alvo definido")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ItemResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na solicitação")
    })
    public ResponseEntity<ItemResponseDTO> create(
            @Valid @RequestBody TrackedItemRequestDTO request) {
        ItemResponseDTO itemResponseDTO = trackedItemService.createTrackedItem(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(itemResponseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(itemResponseDTO);
    }

    @GetMapping
    @Operation(summary = "Listar todos os itens rastreados",
               description = "Retorna uma lista de todos os itens sob monitoramento de preço")
    @ApiResponse(responseCode = "200", description = "Lista de itens retornada com sucesso")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(trackedItemService.getAllTrackedItems());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de um item",
               description = "Retorna as informações detalhadas de um item rastreado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item encontrado",
                    content = @Content(schema = @Schema(implementation = ItemResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    public ResponseEntity<ItemResponseDTO> getItem(
            @Parameter(description = "ID do item rastreado", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(trackedItemService.getTrackedItemById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar preço alvo de um item",
               description = "Atualiza o preço alvo que define quando um alerta deve ser disparado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ItemResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "400", description = "Preço inválido")
    })
    public ResponseEntity<ItemResponseDTO> update(
            @Parameter(description = "ID do item rastreado", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novo preço alvo", example = "1299.90", required = true)
            String targetPrice) {
        ItemResponseDTO updatedItem = trackedItemService.updateTrackedItem(id, new java.math.BigDecimal(targetPrice));
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um item rastreado",
               description = "Remove um item do monitoramento e todos os seus alertas associados")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do item rastreado", example = "1", required = true)
            @PathVariable Long id) {
        trackedItemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/snapshots")
    @Operation(summary = "Criar novo snapshot de preço",
               description = "Registra o preço atual do item em um determinado momento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Snapshot criado com sucesso",
                    content = @Content(schema = @Schema(implementation = SnapshotHistoryResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na solicitação"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    public ResponseEntity<SnapshotHistoryResponseDTO> addPriceSnapshot(
            @Parameter(description = "ID do item rastreado", example = "1", required = true)
            @PathVariable Long id,
            @RequestBody SnapshotRequestDTO request) {
        SnapshotHistoryResponseDTO snapshotResponseDTO = snapshotService.createSnapshot(id, request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{snapshotId}")
                .buildAndExpand(snapshotResponseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(snapshotResponseDTO);
    }

    @GetMapping("/{id}/snapshots")
    @Operation(summary = "Listar snapshots de um item",
               description = "Retorna o histórico de todos os snapshots de preço de um item")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Snapshots encontrados"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    public ResponseEntity<List<SnapshotHistoryResponseDTO>> getSnapshots(
            @Parameter(description = "ID do item rastreado", example = "1", required = true)
            @PathVariable Long id) {
        List<SnapshotHistoryResponseDTO> snapshots = snapshotService.getSnapshotsByItem(id);
        return ResponseEntity.ok(snapshots);
    }

    @GetMapping("/{itemId}/snapshots/{snapshotId}")
    @Operation(summary = "Obter um snapshot específico",
               description = "Retorna as informações detalhadas de um snapshot de preço")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Snapshot encontrado",
                    content = @Content(schema = @Schema(implementation = SnapshotHistoryResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item ou snapshot não encontrado")
    })
    public ResponseEntity<SnapshotHistoryResponseDTO> getSnapshotById(
            @Parameter(description = "ID do item rastreado", example = "1", required = true)
            @PathVariable Long itemId,
            @Parameter(description = "ID do snapshot", example = "1", required = true)
            @PathVariable Long snapshotId) {
        SnapshotHistoryResponseDTO snapshot = snapshotService.getSnapshotById(snapshotId);
        return ResponseEntity.ok(snapshot);
    }
}
