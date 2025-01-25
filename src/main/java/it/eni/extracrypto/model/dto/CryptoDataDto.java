package it.eni.extracrypto.model.dto;

import it.eni.extracrypto.model.enums.CryptoName;
import it.eni.extracrypto.model.enums.Network;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CryptoDataDto {
    private CryptoName cryptoName;
    private BigDecimal cryptoAmount;
    private BigDecimal dollarAmount;
    private Network network;

}
