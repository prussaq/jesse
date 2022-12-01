package net.prussaq.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MarketService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public Map<String, Double> getPrices(List<String> tickets) {
        String TQBR = "TQBR";
        String TQPI = "TQPI";
//        String urlTemplate = "https://iss.moex.com/iss/engines/stock/markets/shares/boards/TQBR/securities/<SECID>.json?iss.meta=off&iss.only=securities&securities.columns=PREVADMITTEDQUOTE";
        String urlTemplate = "https://iss.moex.com/iss/engines/stock/markets/shares/boards/TQBR/securities/<SECID>.json?iss.meta=off&iss.only=marketdata&marketdata.columns=LAST";
        Map<String, Double> map = new HashMap<>();

        for (String ticket : tickets) {
            String url = urlTemplate.replace("<SECID>", ticket);
            String body = restTemplate.getForObject(url, String.class);
            try {
                JsonNode root = mapper.readTree(body);
                double price = root.at("/marketdata/data/0/0").asDouble();
                if (price == 0.0) {
                    url = url.replace("TQBR", "TQPI");
                    body = restTemplate.getForObject(url, String.class);
                    root = mapper.readTree(body);
                    price = root.at("/marketdata/data/0/0").asDouble();
                }
                if (price == 0.0) {
                    map.put(ticket, null);
                } else {
                    map.put(ticket, price);
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }
}
