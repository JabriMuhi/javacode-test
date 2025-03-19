package org.jabrimuhi.javacodetest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jabrimuhi.javacodetest.dto.request.WalletUpdateRequestDTO;
import org.jabrimuhi.javacodetest.dto.response.WalletResponseDTO;
import org.jabrimuhi.javacodetest.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallet")
@Tag(name = "Wallet")
public class WalletController {
    private final WalletService walletService;

    @PostMapping
    @Operation(summary = "Update wallet balance", description = "Updates the balance of a specified wallet.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated balance"),
            @ApiResponse(responseCode = "404", description = "Wallet not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Insufficient funds",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))})
    })
    public ResponseEntity<?> updateWallet(
            @Parameter(description = "Wallet update request", required = true,
                    content = @Content(schema = @Schema(implementation = WalletUpdateRequestDTO.class)))
            @Valid @RequestBody WalletUpdateRequestDTO request) {
        UUID walletId = UUID.fromString(request.getWalletId());
        walletService.updateBalance(walletId, request.getAmount(), request.getOperationType());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{walletId}")
    @Operation(summary = "Get wallet balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved balance",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Wallet not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))})
    })
    public ResponseEntity<WalletResponseDTO> getBalance(
            @Parameter(description = "ID of the wallet", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID walletId) {
            Long balance = walletService.getBalance(walletId);

            return ResponseEntity.ok(new WalletResponseDTO(balance, null));
    }
}
