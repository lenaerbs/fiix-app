package com.fiix.app;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import com.fiix.app.Entity.FiixCalendarEntity;

@Dao
public interface FiixCalendarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCalendars(FiixCalendarEntity... calendars);

    @Query("UPDATE FiixCalendarEntity SET code = :code WHERE id = :calendarId")
    int updateCalendar(long calendarId, String code);

    @Query("SELECT * FROM FiixCalendarEntity order by id asc")
    public List<FiixCalendarEntity> getFiixCalendars();

    @Query("DELETE FROM FiixCalendarEntity")
    public void deleteAllCalenders();
}

