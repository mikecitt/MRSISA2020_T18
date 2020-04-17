package com.tim18.bolnicar.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TimeOff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;
}
