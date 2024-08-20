package it.eni.extracrypto.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoMarketCoinDto {
    private String name;
    private String symbol;
    private Integer cmcRank;
    private CryptoMarketQuoteDto quote;



}
