package it.eni.extracrypto.model.dto;

import it.eni.extracrypto.model.enums.CryptoName;
import it.eni.extracrypto.model.enums.Network;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private String walletAddressRecipient;
    private String walletAddressSender;
    private Network network;
    private CryptoName cryptoName;
    private BigDecimal amount;
}
