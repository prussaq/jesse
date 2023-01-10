package net.prussaq.jesse.controller.rest;

import lombok.AllArgsConstructor;
import net.prussaq.jesse.model.Sell;
import net.prussaq.jesse.model.Buy;
import net.prussaq.jesse.service.TradeService;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("trade")
public class TradeController {

    private final TradeService service;

    @GetMapping("buy/{id}")
    public Buy getBuy(@PathVariable Long id) {
        return service.getBuy(id);
    }

    @PutMapping("buy")
    public void updateBuy(@RequestBody Buy buy) {
        service.updateBuy(buy);
    }

    @DeleteMapping("buy")
    public void deleteBuy(@RequestBody Buy buy) {
        service.deleteBuy(buy);
    }

    @GetMapping("sell/{id}")
    public Sell getSell(@PathVariable Long id) {
        return service.getSell(id);
    }

    @PutMapping("sell")
    public void updateSell(@RequestBody Sell sell) {
        service.updateSell(sell);
    }

    @DeleteMapping("sell")
    public void deleteSell(@RequestBody Sell sell) {
        service.deleteSell(sell);
    }
}
