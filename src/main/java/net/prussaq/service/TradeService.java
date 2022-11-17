package net.prussaq.service;

import lombok.AllArgsConstructor;
import net.prussaq.entity.BuyEntity;
import net.prussaq.entity.SellEntity;
import net.prussaq.model.Buy;
import net.prussaq.model.Sell;
import net.prussaq.repository.BuyRepository;
import net.prussaq.repository.SellRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TradeService {

    private final BuyRepository buyRepository;
    private final SellRepository sellRepository;
    private final ModelMapper mapper;

    public Buy getBuy(Long id) {
        return buyRepository.findById(id).map(buyEntity -> mapper.map(buyEntity, Buy.class)).orElseThrow();
    }

    public void updateBuy(Buy buy) {
        if (buy == null || buy.getId() == null) {
            throw new RuntimeException("Cannot update BuyEntity due to buy is null or buy.id is null");
        }
        BuyEntity buyEntity = buyRepository.findById(buy.getId()).orElseThrow();
        mapper.map(buy, buyEntity);
        buyRepository.save(buyEntity);
        // todo: add money flow record
    }

    public void deleteBuy(Buy buy) {
        if (buy == null || buy.getId() == null) {
            throw new RuntimeException("Cannot delete BuyEntity due to buy is null or buy.id is null");
        }
        buyRepository.deleteById(buy.getId());
        // todo: add money flow record
    }

    public Sell getSell(Long id) {
        return sellRepository.findById(id).map(sellEntity -> mapper.map(sellEntity, Sell.class)).orElseThrow();
    }

    public void updateSell(Sell sell) {
        if (sell == null || sell.getId() == null) {
            throw new RuntimeException("Cannot update SellEntity due to sell is null or sell.id is null");
        }
        SellEntity sellEntity = sellRepository.findById(sell.getId()).orElseThrow();
        mapper.map(sell, sellEntity);
        sellRepository.save(sellEntity);
        // todo: add money flow record
    }

    public void deleteSell(Sell sell) {
        if (sell == null || sell.getId() == null) {
            throw new RuntimeException("Cannot delete SellEntity due to sell is null or sell.id is null");
        }
        sellRepository.deleteById(sell.getId());
        // todo: add money flow record
    }

}
