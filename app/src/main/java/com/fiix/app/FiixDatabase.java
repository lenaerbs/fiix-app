package com.fiix.app;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.fiix.app.Entity.FiixCalendarEntity;
import com.fiix.app.Entity.FiixEventEntity;

@Database(entities = {FiixEventEntity.class, FiixCalendarEntity.class}, version = 5)
public abstract class FiixDatabase extends RoomDatabase {

        private static FiixDatabase INSTANCE;

        public abstract FiixEventDao eventDao();

        public abstract FiixCalendarDao calendarDao();

}
