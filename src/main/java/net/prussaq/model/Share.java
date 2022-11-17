package net.prussaq.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Share {

    private final String ticket;
    private final String name;
    private int amount;
    private BigDecimal total = BigDecimal.ZERO;
    private final List<Trade> trades = new ArrayList<>();

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void addTotal(BigDecimal total) {
        this.total = this.total.add(total);
    }

    public void addTrade(Trade trade) {
        trades.add(trade);
    }
}
