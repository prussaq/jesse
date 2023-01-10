package net.prussaq.jesse.service;

import lombok.AllArgsConstructor;
import net.prussaq.jesse.entity.BuyEntity;
import net.prussaq.jesse.entity.SellEntity;
import net.prussaq.jesse.model.*;
import net.prussaq.jesse.repository.BuyRepository;
import net.prussaq.jesse.repository.SellRepository;
import net.prussaq.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ActiveService {

    private final BuyRepository buyRepository;
    private final SellRepository sellRepository;
    private final AccountService accountService;
    private final EquityService equityService;
    private final ModelMapper mapper;

    public Profile getProfile() {
        List<BuyEntity> buyEntities = buyRepository.findAll();
        List<SellEntity> sellEntities = sellRepository.findAll();
        Profile profile = new Profile();
        profile.setMoney(accountService.getMoney());

        buyEntities.forEach(buyEntity -> {
            List<Sell> sells = new ArrayList<>();
            int sellAmount = sellEntities.stream()
                    .filter(sellEntity -> Objects.equals(sellEntity.getBuyId(), buyEntity.getId()))
                    .peek(sellEntity -> sells.add(mapper.map(sellEntity, Sell.class)))
                    .mapToInt(SellEntity::getAmount).sum();
            if (buyEntity.getAmount() > sellAmount) {
                int remains = buyEntity.getAmount() - sellAmount;
                BigDecimal total = buyEntity.getPrice().multiply(new BigDecimal(remains));
                Buy buy = mapper.map(buyEntity, Buy.class);
                buy.setRemains(remains);
                profile.getShares().compute(buy.getTicket(), (ticket, share) -> {
                    if (share == null) {
                        share = new Share(ticket, equityService.getName(ticket));
                    }
                    share.addAmount(remains);
                    share.addTotal(total);
                    share.addTrade(new Trade(buy, sells));
                    return share;
                });
                profile.addBalance(total);
            }
        });
        return profile;
    }

    public Profile getProfileSummary() {
        Profile profile = new Profile();
        List<BuyEntity> buyEntities = buyRepository.findAll();
        List<SellEntity> sellEntities = sellRepository.findAll();

        buyEntities.forEach(buyEntity -> {
            int amount = sellEntities.stream()
                    .filter(sellEntity -> Objects.equals(sellEntity.getBuyId(), buyEntity.getId()))
                    .mapToInt(SellEntity::getAmount).sum();
            if (amount == 0) {
                profile.addBalance(buyEntity.getPrice().multiply(new BigDecimal(buyEntity.getAmount())));
                profile.addCount();
            } else if (amount < buyEntity.getAmount()) {
                profile.addBalance(buyEntity.getPrice().multiply(new BigDecimal(buyEntity.getAmount() - amount)));
                profile.addCount();
            } // otherwise do nothing, sold out
        });
        profile.setMoney(accountService.getMoney());
        return profile;
    }

    public List<Share> getSharesSummary() {
        List<Share> shares = new ArrayList<>();
        List<String> tickets = buyRepository.getDistinctTickets();

        tickets.forEach(ticket -> {
            Share share = new Share(ticket, equityService.getName(ticket));
            List<BuyEntity> buyEntities = buyRepository.findAllByTicket(ticket);

            buyEntities.forEach(buyEntity -> {
                int amount = sellRepository.findAllByBuyId(buyEntity.getId()).stream()
                        .mapToInt(SellEntity::getAmount).sum();
                share.addAmount(buyEntity.getAmount() - amount);
                share.addTotal(buyEntity.getPrice().multiply(new BigDecimal(buyEntity.getAmount() - amount)));
            });
            if (share.getAmount() > 0) {
                shares.add(share);
            }
        });
        return shares;
    }

    public List<Trade> getTrades(String ticket) {
        List<Trade> trades = new ArrayList<>();
        List<BuyEntity> buyEntities = buyRepository.findAllByTicket(ticket);

        buyEntities.forEach(buyEntity -> {
            List<SellEntity> sellEntities = sellRepository.findAllByBuyId(buyEntity.getId());
            int amount = sellEntities.stream().mapToInt(SellEntity::getAmount).sum();

            if (buyEntity.getAmount() > amount) {
                Buy buyDto = mapper.map(buyEntity, Buy.class);
                buyDto.setRemains(buyEntity.getAmount() - amount);
                List<Sell> sells = sellEntities.stream()
                        .map(sellEntity -> mapper.map(sellEntity, Sell.class))
                        .collect(Collectors.toList());
                trades.add(new Trade(buyDto, sells));
            }
        });
        return trades;
    }

}
