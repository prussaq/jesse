package net.prussaq.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sell")
public class SellEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="buy_id")
    private Long buyId;

    @Column(name="date")
    private LocalDate date;

    @Column(name="ticket")
    private String ticket;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name="amount")
    private Integer amount;
}
