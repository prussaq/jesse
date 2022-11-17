package net.prussaq.controller.rest;

import lombok.AllArgsConstructor;
import net.prussaq.model.Market;
import net.prussaq.service.MarketService;
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
