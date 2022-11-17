package net.prussaq.service;

import lombok.AllArgsConstructor;
import net.prussaq.entity.BuyEntity;
import net.prussaq.entity.SellEntity;
import net.prussaq.model.*;
import net.prussaq.repository.BuyRepository;
import net.prussaq.repository.SellRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CompleteService {

    private final BuyRepository buyRepository;
    private final SellRepository sellRepository;
    private final EquityService equityService;
    private final ModelMapper mapper;

    public Profile getProfile() {
        List<BuyEntity> buyEntities = buyRepository.findAll();
        List<SellEntity> sellEntities = sellRepository.findAll();
        Profile profile = new Profile();

        buyEntities.forEach(buyEntity -> {
            List<Sell> sells = new ArrayList<>();
            int sellAmount = sellEntities.stream()
                    .filter(sellEntity -> Objects.equals(sellEntity.getBuyId(), buyEntity.getId()))
                    .peek(sellEntity -> sells.add(mapper.map(sellEntity, Sell.class)))
                    .mapToInt(SellEntity::getAmount).sum();
            if (buyEntity.getAmount() == sellAmount) {
                BigDecimal total = buyEntity.getPrice().multiply(new BigDecimal(sellAmount));
                Buy buy = mapper.map(buyEntity, Buy.class);
                profile.getShares().compute(buy.getTicket(), (ticket, share) -> {
                    if (share == null) {
                        share = new Share(ticket, equityService.getName(ticket));
                    }
                    share.addAmount(sellAmount);
                    share.addTotal(total);
                    share.addTrade(new Trade(buy, sells));
                    return share;
                });
                profile.addBalance(total);
            }
        });
        return profile;
    }

}
