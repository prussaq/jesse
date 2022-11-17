package net.prussaq.service;

import lombok.AllArgsConstructor;
import net.prussaq.constant.Action;
import net.prussaq.constant.Reason;
import net.prussaq.entity.MoneyEntity;
import net.prussaq.repository.MoneyRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class MoneyService {

    private final AccountService accountService;
    private final MoneyRepository moneyRepository;

    public void input(BigDecimal money, String note) {
        accountService.increaseMoney(money);

        MoneyEntity moneyEntity = MoneyEntity.builder()
                .date(LocalDate.now())
                .flow(money)
                .action(Action.INCREASE)
                .reason(Reason.INPUT)
                .balance(accountService.getMoney())
                .note(note)
                .build();
        moneyRepository.save(moneyEntity);
    }

    public void output(BigDecimal money, String note) {
        accountService.decreaseMoney(money);

        MoneyEntity moneyEntity = MoneyEntity.builder()
                .date(LocalDate.now())
                .flow(money)
                .action(Action.DECREASE)
                .reason(Reason.OUTPUT)
                .balance(accountService.getMoney())
                .note(note)
                .build();
        moneyRepository.save(moneyEntity);
    }

    public void correct(BigDecimal money, Action action, String note) {
        MoneyEntity moneyEntity = MoneyEntity.builder()
                .date(LocalDate.now())
                .flow(money)
                .reason(Reason.CORRECTION)
                .note(note)
                .build();

        switch (action) {
            case INCREASE:
                accountService.increaseMoney(money);
                moneyEntity.setAction(Action.INCREASE);
                break;
            case DECREASE:
                accountService.decreaseMoney(money);
                moneyEntity.setAction(Action.DECREASE);
                break;
        }
        moneyEntity.setBalance(accountService.getMoney());

        moneyRepository.save(moneyEntity);
    }
}
