package it.eni.extracrypto.service;

import it.eni.extracrypto.model.dto.CryptoDataDto;
import it.eni.extracrypto.model.entity.CryptoWallet;
import it.eni.extracrypto.model.enums.Network;
import it.eni.extracrypto.repository.CryptoWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoWalletService {
    private final CryptoWalletRepository cryptoWalletRepository;

    @Autowired
    public CryptoWalletService(CryptoWalletRepository cryptoWalletRepository){
        this.cryptoWalletRepository= cryptoWalletRepository;
    }

    public List<CryptoDataDto> findCryptoData(String walletAddress, Network network){
        List<CryptoWallet> cryptoWallets=cryptoWalletRepository.findByWalletAddressAndNetwork(walletAddress,network);
        List<CryptoDataDto> cryptoList = new ArrayList<>();
        for(CryptoWallet s : cryptoWallets){
            CryptoDataDto cryptoDataDto = new CryptoDataDto();
            cryptoDataDto.setCryptoAmount(s.getAmount());
            cryptoDataDto.setCryptoName(s.getCryptoName());
            cryptoDataDto.setDollarAmount(BigDecimal.ZERO);
            cryptoList.add(cryptoDataDto);
        }
        return cryptoList;
    }
}


