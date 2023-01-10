package net.prussaq.jesse.controller.rest;

import lombok.AllArgsConstructor;
import net.prussaq.jesse.service.MarketService;
import net.prussaq.jesse.model.Market;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("market")
public class MarketController {

    private final MarketService service;

    @PostMapping("prices")
    public Map<String, Double> getPrices(@RequestBody Market market) {
        return service.getPrices(market.getTickets());
    }
}
