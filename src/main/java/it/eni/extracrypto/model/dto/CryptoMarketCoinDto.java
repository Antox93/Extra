package it.eni.extracrypto.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoMarketCoinDto {
    private String name;
    private String symbol;
    @JsonProperty("cmc_rank")
    private Integer cmcRank;
    private CryptoMarketQuoteDto quote;
    private String id;



    }


