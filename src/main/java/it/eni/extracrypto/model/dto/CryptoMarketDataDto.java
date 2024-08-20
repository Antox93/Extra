package it.eni.extracrypto.model.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

@Data
public class CryptoMarketDataDto {
    private CryptoMarketCoinDto coin;

    @JsonAnySetter
    public void add(String key, CryptoMarketCoinDto value){
        this.coin = value;
    }
}
