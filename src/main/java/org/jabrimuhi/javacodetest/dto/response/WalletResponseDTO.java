package org.jabrimuhi.javacodetest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponseDTO {
    private Long balance;
    private String error;
}
