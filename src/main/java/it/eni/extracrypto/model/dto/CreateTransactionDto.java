package it.eni.extracrypto.model.dto;

import it.eni.extracrypto.model.enums.CryptoName;
import it.eni.extracrypto.model.enums.Network;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateTransactionDto {
    private String walletRecipient;
    private String walletSender;
    private Network network;
    private CryptoName cryptoName;
    private BigDecimal amount;
}
