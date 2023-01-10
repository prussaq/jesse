package net.prussaq.jesse.controller.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.prussaq.jesse.model.Money;
import net.prussaq.jesse.service.MoneyService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("money")
public class MoneyController {

    private final MoneyService service;

    @PostMapping("change")
    public void change(@RequestBody Money money) {
        log.info("POST money/change requested; body: {}", money);
        service.change(money);
    }

    @GetMapping
    public BigDecimal get() {
        log.info("GET money/ requested");
        return service.get();
    }

}
