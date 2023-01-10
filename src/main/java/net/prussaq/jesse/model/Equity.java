package net.prussaq.jesse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Equity {

    @NonNull
    private String ticket;

    @NonNull
    private String name;
}
