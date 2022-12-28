package net.prussaq.service;

import lombok.AllArgsConstructor;
import net.prussaq.entity.MoneyEntity;
import net.prussaq.model.Money;
import net.prussaq.repository.MoneyRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class MoneyService {

    private final AccountService accountService;
    private final MoneyRepository moneyRepository;

    public void change(Money money) {
        switch (money.getAction()) {
            case INCREASE:
                accountService.increaseMoney(money.getMoney());
                break;
            case DECREASE:
                accountService.decreaseMoney(money.getMoney());
                break;
        }
        MoneyEntity moneyEntity = MoneyEntity.builder()
                .date(LocalDate.now())
                .flow(money.getMoney())
                .action(money.getAction())
                .reason(money.getReason())
                .balance(accountService.getMoney())
                .note(money.getNote())
                .build();
        moneyRepository.save(moneyEntity);
    }

    public BigDecimal get() {
        return accountService.getMoney();
    }

}
