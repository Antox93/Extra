package it.eni.extracrypto.controller;

import it.eni.extracrypto.model.dto.CryptoDataDto;
import it.eni.extracrypto.model.enums.Network;
import it.eni.extracrypto.service.CryptoWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("crypto-wallet")
public class CryptoWalletController {
    private final CryptoWalletService cryptoWalletService;


    @Autowired
    public CryptoWalletController(CryptoWalletService cryptoWalletService){
        this.cryptoWalletService = cryptoWalletService;
    }

    @GetMapping
    public ResponseEntity<List<CryptoDataDto>> searchCryptoData(@RequestParam String walletAddress,@RequestParam Network network){
      List<CryptoDataDto> cryptoDataDtos = cryptoWalletService.findCryptoData(walletAddress,network);
      return ResponseEntity.ok(cryptoDataDtos);

    }

    @GetMapping("status")
    public BigDecimal searchWalletStatus(@RequestParam String walletAddress,@RequestParam Network network){
        return cryptoWalletService.findWalletStatus(walletAddress,network);
    }

}
