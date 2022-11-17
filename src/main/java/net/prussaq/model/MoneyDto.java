package net.prussaq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.prussaq.constant.Action;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDto {

    @NonNull
    private BigDecimal money;

    @Nullable
    private Action action;

    @Nullable
    private String note;
}
