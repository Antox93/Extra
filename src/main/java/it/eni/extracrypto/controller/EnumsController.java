package it.eni.extracrypto.controller;

import it.eni.extracrypto.model.enums.CryptoName;
import it.eni.extracrypto.model.enums.Network;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/enums")
public class EnumsController {

    @GetMapping("/networks")
    public List<Network> getAllNetworks() {
        return Arrays.asList(Network.values());
    }
    @GetMapping("/crypto")
    public List<CryptoName> getAllCrypto() {
        return Arrays.asList(CryptoName.values());
    }
    @GetMapping("crypto/id")
    public List<Integer> getAllCryptoId() {
        List<Integer> lista=new ArrayList<>();
        for (CryptoName c : CryptoName.values()){
            lista.add(c.id);
        }
        return lista;
    }
}
