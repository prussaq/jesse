package net.prussaq.controller.rest;

import lombok.AllArgsConstructor;
import net.prussaq.model.MoneyDto;
import net.prussaq.service.MoneyService;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("money")
public class MoneyController {

    private final MoneyService service;

    @PostMapping("input")
    public void input(@RequestBody MoneyDto moneyDto) {
        service.input(moneyDto.getMoney(), moneyDto.getNote());
    }

    @PostMapping("output")
    public void output(@RequestBody MoneyDto moneyDto) {
        service.output(moneyDto.getMoney(), moneyDto.getNote());
    }

    @PostMapping("correct")
    public void correct(@RequestBody MoneyDto moneyDto) {
        assert moneyDto.getAction() != null;
        service.correct(moneyDto.getMoney(), moneyDto.getAction(), moneyDto.getNote());
    }

}
