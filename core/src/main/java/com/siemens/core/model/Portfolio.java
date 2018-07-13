package com.siemens.core.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Entity
@Table(name="portfolio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Portfolio extends BaseEntity<Long>{

    @Id
    @Column(name="pid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;

    @OneToOne
    @JoinColumn(name = "uid")
    private User user;

    @OneToMany(mappedBy="portfolio")
    private Set<HoldingRecord> holdingRecords;

}
