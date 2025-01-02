package it.eni.extracrypto.model.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CryptoMarketDataDto {
    private List<CryptoMarketCoinDto> coin=new ArrayList<>();

    @JsonAnySetter
    public void add(String key, CryptoMarketCoinDto value){
        this.coin.add(value);
    }
}
