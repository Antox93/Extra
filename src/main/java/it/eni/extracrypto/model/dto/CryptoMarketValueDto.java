package it.eni.extracrypto.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoMarketValueDto {
    private BigDecimal price;
    private BigDecimal percent_change_1h;
    private BigDecimal percent_change_24h;
    private BigDecimal percent_change_7d;
    private BigDecimal percent_change_30d;
    private BigDecimal percent_change_60d;
    private BigDecimal percent_change_90d;
    private BigDecimal market_cap;
    private BigDecimal volume_24h;
}
