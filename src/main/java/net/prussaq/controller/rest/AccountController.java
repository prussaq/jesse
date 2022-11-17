package net.prussaq.controller.rest;

import lombok.AllArgsConstructor;
import net.prussaq.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@RequestMapping("account")
public class AccountController {

    private final AccountService service;

    @GetMapping("balance")
    public BigDecimal getBalance() {
        return service.getMoney();
    }

}
