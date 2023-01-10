package net.prussaq.jesse.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.prussaq.jesse.constant.Action;
import net.prussaq.jesse.constant.Reason;
import net.prussaq.jesse.entity.BuyEntity;
import net.prussaq.jesse.entity.MoneyEntity;
import net.prussaq.jesse.entity.SellEntity;
import net.prussaq.jesse.model.Sell;
import net.prussaq.jesse.repository.BuyRepository;
import net.prussaq.jesse.repository.MoneyRepository;
import net.prussaq.jesse.repository.SellRepository;
import net.prussaq.jesse.model.Buy;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Service
@AllArgsConstructor
public class DealService {

    private final BuyRepository buyRepository;
    private final SellRepository sellRepository;
    private final AccountService accountService;
    private final MoneyRepository moneyRepository;
    private final ModelMapper mapper;

    public void buy(Buy buy) {
        BuyEntity buyEntity = mapper.map(buy, BuyEntity.class);
        if (buy.getDate() == null) {
            buyEntity.setDate(LocalDate.now());
        }
        buyRepository.save(buyEntity);
        log.info("BuyEntity saved: {}", buyEntity);

        BigDecimal result = buyEntity.getPrice().multiply(new BigDecimal(buyEntity.getAmount()));
        accountService.decreaseMoney(result);

        MoneyEntity moneyEntity = MoneyEntity.builder()
                .date(buyEntity.getDate())
                .flow(result)
                .action(Action.DECREASE)
                .reason(Reason.BUY)
                .link(buyEntity.getId())
                .balance(accountService.getMoney())
                .build();
        moneyRepository.save(moneyEntity);
    }

    public void sell(Sell sell) {
        SellEntity sellEntity = mapper.map(sell, SellEntity.class);
        if (sell.getDate() == null) {
            sellEntity.setDate(LocalDate.now());
        }
        sellRepository.save(sellEntity);

        BigDecimal result = sellEntity.getPrice().multiply(new BigDecimal(sellEntity.getAmount()));
        accountService.increaseMoney(result);

        MoneyEntity moneyEntity = MoneyEntity.builder()
                .date(sellEntity.getDate())
                .flow(result)
                .action(Action.INCREASE)
                .reason(Reason.SELL)
                .link(sellEntity.getId())
                .balance(accountService.getMoney())
                .build();
        moneyRepository.save(moneyEntity);
    }
}
