package com.tim18.bolnicar.model;

import javax.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //TODO: insert room name, or use id as name?

    @Enumerated(EnumType.STRING)
    private RoomType type;
}
