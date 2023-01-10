package net.prussaq.jesse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Trade {

    private final Buy buy;
    private final List<Sell> sells;
    private BigDecimal sum;

}
