package it.eni.extracrypto.model.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

@Data
public class CryptoMarketQuoteDto {
    private CryptoMarketValueDto value;

    @JsonAnySetter
    public void add(String key, CryptoMarketValueDto value){
        this.value = value;
    }
}
