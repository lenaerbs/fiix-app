package com.fiix.app.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FiixEventEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String description;
    public boolean isInPast;
    public String start;
    public String startDateFormatted;
    public String startTime;
}

