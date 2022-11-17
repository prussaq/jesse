package net.prussaq.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Profile {

    private int count;
    @Getter
    private BigDecimal balance = BigDecimal.ZERO;
    @Getter @Setter
    private BigDecimal money = BigDecimal.ZERO;
    @Getter
    private final Map<String, Share> shares = new HashMap<>();

    public int getCount() {
        return count > 0 ? count : shares.size();
    }

    public void addCount() {
        count++;
    }

    public void addBalance(BigDecimal total) {
        balance = balance.add(total);
    }

    public void addShare(Share share) {
        shares.put(share.getTicket(), share);
    }

}
