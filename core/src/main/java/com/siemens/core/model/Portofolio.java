package com.siemens.core.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Entity
@Table(name="portofolio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Portofolio {

    @Id
    @Column(name="pid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;

    @OneToOne
    @JoinColumn(name = "uid")
    private User user;

    @OneToMany(mappedBy="portofolio")
    private Set<HoldingRecord> holdingRecords;

}
