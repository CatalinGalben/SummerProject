package com.siemens.core.model;


import lombok.*;


import javax.persistence.*;

@Entity
@Table(name = "currency_exchange")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CurrencyExchange extends BaseEntity<Integer> {


    @ManyToOne
    @JoinColumn(name = "currency1Id")
    private Currency currency1;

    @ManyToOne
    @JoinColumn(name = "currency2Id")
    private Currency currency2;

    @Column(name = "factor")
    private Double factor;

    @Column(name = "dateOfExchange")
    private String dateOfExchange;
}
