package com.siemens.core.model;


import lombok.*;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "currency_exchange")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CurrencyExchange extends BaseEntity<Integer> {


    @Column(name = "currency1")
    private Currency currency1;

    @Column(name = "currency2")
    private Currency currency2;

    @Column(name = "factor")
    private int factor;

    @Column(name = "dateOfExchange")
    private String dateOfExchange;
}
