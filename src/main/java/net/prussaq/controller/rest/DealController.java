package net.prussaq.controller.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.prussaq.model.Buy;
import net.prussaq.model.Sell;
import net.prussaq.service.DealService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("deal")
public class DealController {

    private final DealService service;

    @PostMapping("buy")
    @ResponseStatus(HttpStatus.CREATED)
    public void buy(@RequestBody Buy buyDto) {
        log.info("request: deal/buy; body: {}", buyDto);
        service.buy(buyDto);
    }

    @PostMapping("sell")
    @ResponseStatus(HttpStatus.CREATED)
    public void sell(@RequestBody Sell sellDto) {
        service.sell(sellDto);
    }
}
