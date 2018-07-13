package com.siemens.core.model;

import javax.persistence.*;

public class Share {

    @Id
    @Column(name = "sid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sid;

    @Column(name = "price")
    private int price;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL, optional = false)
    private Company company;
}
