package com.fiix.app;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import com.fiix.app.Entity.FiixEventEntity;

@Dao
public interface FiixEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertEvents(FiixEventEntity... events);

    @Query("SELECT * FROM FiixEventEntity")
    public List<FiixEventEntity> getFiixEvents();

    @Query("DELETE FROM FiixEventEntity")
    public void deleteAllEvents();
}

