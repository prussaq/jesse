package net.prussaq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sell {

    @Nullable
    private Long id;
    @Nullable
    private LocalDate date;
    @NonNull
    private long buyId;
    @NonNull
    private String ticket;
    @NonNull
    private BigDecimal price;
    @NonNull
    private int amount;
}