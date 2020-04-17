package com.tim18.bolnicar.model;

import javax.persistence.*;

@Entity
public class Room {
    @Id
    private Integer roomNumber;

    @Enumerated(EnumType.STRING)
    private RoomType type;
}
