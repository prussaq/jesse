package net.prussaq.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.prussaq.constant.Action;
import net.prussaq.constant.Reason;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "money")
public class MoneyEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="date")
    private LocalDate date;

    @Column(name = "flow")
    private BigDecimal flow;

    @Column(name="action")
    @Enumerated(EnumType.STRING)
    private Action action;

    @Column(name="reason")
    @Enumerated(EnumType.STRING)
    private Reason reason;

    @Column(name="link")
    private Long link;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name="note")
    private String note;

}
