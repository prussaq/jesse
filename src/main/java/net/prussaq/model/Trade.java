package net.prussaq.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Trade {

    private Buy buy;
    private List<Sell> sells;

}
