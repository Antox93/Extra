package it.eni.extracrypto.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoMarketDto {
    private CryptoMarketDataDto data;

    public BigDecimal getPrice(){
        return this.data.getCoin().getQuote().getValue().getPrice();
    }
}
