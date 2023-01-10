package net.prussaq.jesse.model;

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
public class Buy {

    @Nullable
    private Long id;
    @Nullable
    private LocalDate date;
    @NonNull
    private String ticket;
    @NonNull
    private BigDecimal price;
    @NonNull
    private int amount;
    @Nullable
    private Integer remains;
}
