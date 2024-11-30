package it.eni.extracrypto.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/enums")
public class EnumsController {

    private static final String API_KEY = "37a8a575-4e40-4841-af89-02f35d8ff82e";
    private static final String COINMARKETCAP_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?id=1,1027,5426,74,5994";

    @GetMapping("/crypto/data")
    public ResponseEntity<?> getCryptoData() {
        // Headers per la chiamata
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(COINMARKETCAP_URL, entity, Map.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore durante la richiesta all'API di CoinMarketCap: " + e.getMessage());
        }
    }
}
