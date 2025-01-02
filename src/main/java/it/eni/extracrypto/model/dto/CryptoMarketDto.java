package it.eni.extracrypto.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoMarketDto {
    private CryptoMarketDataDto data;

    public BigDecimal getPrice(String id) {
        var coin = this.data.getCoin().stream()
                .filter(c-> id.equals(c.getId()))
                .findFirst().orElse(null);

        if (coin == null) return BigDecimal.ZERO;

        return coin.getQuote().getValue().getPrice();

    }
}
