package com.siemens.core.model;


import lombok.*;
import org.joda.time.DateTime;

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
public class CurrencyExchange extends BaseEntity<Long> {


    @Column(name = "currency1")
    private Currency currency1;

    @Column(name = "currency2")
    private Currency currency2;

    @Column(name = "factor")
    private int factor;

    @Column(name = "dateOfExchange")
    private DateTime dateOfExchange;
}
