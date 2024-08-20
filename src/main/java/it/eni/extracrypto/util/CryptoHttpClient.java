package it.eni.extracrypto.util;

import it.eni.extracrypto.exception.BusinessException;
import it.eni.extracrypto.model.enums.ErrorEnum;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CryptoHttpClient {
    private CryptoHttpClient() {
    }

    public static HttpResponse<String> sendGet(String url, String apiKey, Map<String,Integer> params){
        HttpClient client = HttpClient.newHttpClient();

        url = url+getParams(params);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .headers("X-CMC_PRO_API_KEY",apiKey).build();

        HttpResponse<String> response;
        try {
            response=client.send(request,HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new BusinessException(ErrorEnum.COINMARKET_ERROR);
        }
        return response;

    }

    private static String getParams(Map<String,Integer> params) {
        StringBuilder builder = new StringBuilder("?");

        boolean isFirst = true;
        for (Map.Entry<String, Integer> p : params.entrySet()) {
            if (!isFirst) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(p.getKey(), StandardCharsets.UTF_8)).append("=")
                    .append(URLEncoder.encode(String.valueOf(p.getValue()), StandardCharsets.UTF_8));
            isFirst = false;
        }
        return builder.toString();
    }


}
