package it.eni.extracrypto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.eni.extracrypto.model.dto.CryptoDataDto;
import it.eni.extracrypto.model.dto.CryptoMarketDto;
import it.eni.extracrypto.model.entity.CryptoWallet;
import it.eni.extracrypto.model.enums.CryptoName;
import it.eni.extracrypto.model.enums.Network;
import it.eni.extracrypto.repository.CryptoWalletRepository;
import it.eni.extracrypto.util.CryptoHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CryptoWalletService {
    private final CryptoWalletRepository cryptoWalletRepository;
    private final String url;
    private final String apiKey;

    @Autowired
    public CryptoWalletService(CryptoWalletRepository cryptoWalletRepository, @Value("${cryptomarket.api.get-crypto}") String url,
                               @Value("${cryptomarket.api.key}") String apiKey){
        this.cryptoWalletRepository= cryptoWalletRepository;
        this.url= url;
        this.apiKey= apiKey;
    }

    public List<CryptoDataDto> findCryptoData(String walletAddress, Network network){
        List<CryptoWallet> cryptoWallets=cryptoWalletRepository.findByWalletAddressAndNetwork(walletAddress,network);
        List<CryptoDataDto> cryptoList = new ArrayList<>();
        for(CryptoWallet s : cryptoWallets){
            CryptoDataDto cryptoDataDto = new CryptoDataDto();
            cryptoDataDto.setCryptoAmount(s.getAmount());
            cryptoDataDto.setCryptoName(s.getCryptoName());
            cryptoDataDto.setDollarAmount(convert(s.getCryptoName(),s.getAmount()));
            cryptoList.add(cryptoDataDto);
        }
        return cryptoList;
    }

    public BigDecimal findWalletStatus(String walletAddress,Network network){
        List<CryptoWallet> cryptoWallets=cryptoWalletRepository.findByWalletAddressAndNetwork(walletAddress,network);
        BigDecimal somma= BigDecimal.ZERO;
        for(CryptoWallet s:cryptoWallets ){
            BigDecimal dollars=convert(s.getCryptoName(),s.getAmount());
            somma=dollars.add(somma);

        }
        return somma.setScale(5, RoundingMode.HALF_UP);
    }

    private  BigDecimal convert(CryptoName cryptoName, BigDecimal amount){
        Map<String,Integer> params = new HashMap<>();
        params.put("id",cryptoName.id);
        HttpResponse<String> response= CryptoHttpClient.sendGet(url,apiKey,params);

        if(response.statusCode()==200){
            try {
                CryptoMarketDto dto = new ObjectMapper().readValue(response.body(),CryptoMarketDto.class);
                BigDecimal dollars = dto.getPrice();
                return dollars.multiply(amount);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return BigDecimal.ZERO;
    }
}


