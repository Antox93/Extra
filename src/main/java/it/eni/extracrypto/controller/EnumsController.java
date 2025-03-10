package it.eni.extracrypto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eni.extracrypto.model.dto.CryptoMarketDto;
import it.eni.extracrypto.model.dto.CryptoNetworkDto;
import it.eni.extracrypto.model.enums.CryptoName;
import it.eni.extracrypto.model.enums.Network;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enums")
public class EnumsController {

    private static final String API_KEY = "37a8a575-4e40-4841-af89-02f35d8ff82e";
    private static final String COINMARKETCAP_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";






    @GetMapping("/crypto/data/all")
    public ResponseEntity<?> getAllCryptoData() {
        // Headers per la chiamata
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            List<String> idList = Arrays.stream(CryptoName.values()).map(c->c.id.toString()).toList();
            String idJoined = String.join(",",idList);
            String url = COINMARKETCAP_URL + "?id=" +idJoined;

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            String json = new ObjectMapper().writeValueAsString(response.getBody());
           CryptoMarketDto result = new ObjectMapper().readValue(json, CryptoMarketDto.class);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore durante la richiesta all'API di CoinMarketCap: " + e.getMessage());
        }
    }

    @GetMapping("/crypto/data/{id}")
    public ResponseEntity<?> getCryptoDetail(@PathVariable ("id") String id) {
        // Headers per la chiamata
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            String url = COINMARKETCAP_URL + "?id=" +id;
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            String json = new ObjectMapper().writeValueAsString(response.getBody());
            CryptoMarketDto result = new ObjectMapper().readValue(json, CryptoMarketDto.class);



            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore durante la richiesta all'API di CoinMarketCap: " + e.getMessage());
        }
    }

    @GetMapping ("networks")
    public List<CryptoNetworkDto> getNetworks() {
       return Arrays.stream(Network.values()).map(n -> new CryptoNetworkDto(n.toString(),n.fee)).toList();

    }

    @GetMapping("/crypto/data")
    public ResponseEntity<?> getCryptoData(@RequestParam String searchCrypto) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            String url = COINMARKETCAP_URL + "?id=" +searchCrypto;

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            String json = new ObjectMapper().writeValueAsString(response.getBody());
            CryptoMarketDto result = new ObjectMapper().readValue(json, CryptoMarketDto.class);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore durante la richiesta all'API di CoinMarketCap: " + e.getMessage());
        }
    }


    @GetMapping("/crypto/history/{slug}")
    public ResponseEntity<?> getCryptoHistoricalData(@PathVariable("slug") String slug) {
        if (slug == null || slug.isEmpty()) {
            return ResponseEntity.status(400).body("Errore: il parametro slug è mancante.");
        }

        String apiKey = "CG-oaG2WmpByRhut8sHUQEo6knh";
        String url = "https://api.coingecko.com/api/v3/coins/" + slug +
                "/market_chart?vs_currency=usd&days=2&x_cg_demo_api_key=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, null, Map.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore nel recupero dati da CoinGecko: " + e.getMessage());
        }
    }





}



