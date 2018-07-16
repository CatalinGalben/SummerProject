package com.siemens.core.model;

import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "share_price")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SharePrice extends BaseEntity<Long>{

    @OneToOne
    @JoinColumn(name = "companyid")
    private Company company;

    @Column(name = "price")
    private int price;

    @Column(name = "date")
    private DateTime date;
}
