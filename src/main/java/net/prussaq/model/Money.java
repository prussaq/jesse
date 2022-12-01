package net.prussaq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.prussaq.constant.Action;
import net.prussaq.constant.Reason;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Money {

    @NonNull
    private BigDecimal money;

    @NonNull
    private Action action;

    @NonNull
    private Reason reason;

    @Nullable
    private String note;
}
