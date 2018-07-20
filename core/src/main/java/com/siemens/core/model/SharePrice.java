package com.siemens.core.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "share_price")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SharePrice extends BaseEntity<Integer>{



    @OneToOne
    @JoinColumn(name = "companyid")
    private Company company;

    @Column(name = "price")
    private int price;

    @Column(name = "date")
    private String date;
}
