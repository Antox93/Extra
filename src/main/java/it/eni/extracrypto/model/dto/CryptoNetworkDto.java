package it.eni.extracrypto.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CryptoNetworkDto {
    private String name;
    private BigDecimal fee;

}
