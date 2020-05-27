package com.fiix.app.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FiixCalendarEntity {
    @PrimaryKey
    public int id;

    public String code;
    public String title;
    public String description;
}

