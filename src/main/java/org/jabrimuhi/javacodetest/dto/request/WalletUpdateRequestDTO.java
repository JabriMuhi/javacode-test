package org.jabrimuhi.javacodetest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jabrimuhi.javacodetest.model.OperationType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletUpdateRequestDTO {

    @Schema(description = "The ID of the wallet", example = "550e8400-e29b-41d4-a716-446655440000")
    @JsonProperty("wallet_id")
    @NotNull
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "Wallet ID must be a valid UUID")
    private String walletId;

    @Schema(description = "The amount to update the wallet balance", example = "1000")
    @Positive
    private long amount;

    @Schema(description = "The type of operation (DEPOSIT or WITHDRAW)", example = "DEPOSIT")
    @JsonProperty("operation_type")
    @NotNull
    private OperationType operationType;
}
