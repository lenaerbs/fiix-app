package com.fiix.app;

import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Room;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;

import com.fiix.app.Entity.FiixCalendarEntity;
import com.fiix.app.Entity.FiixEventEntity;

public class FiixGetCalendarAsyncTask extends AsyncTask<Void, Void, ArrayList<FiixEventEntity>> {

    private final FiixService fiixService;
    private final MainActivity mainActivity;

    public FiixGetCalendarAsyncTask(MainActivity activity, FiixService fiixService) {
        this.mainActivity = activity;

        this.fiixService = fiixService;
    }

    @Override
    protected ArrayList<FiixEventEntity> doInBackground(Void... voids) {
        ArrayList<FiixEventEntity> copyList = new ArrayList<FiixEventEntity>();
        FiixDatabase database = Room.databaseBuilder(this.mainActivity, FiixDatabase.class, "fiix-db16")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
        if (!this.mainActivity.isNetworkConnected()) {
            List<FiixEventEntity> events = database.eventDao().getFiixEvents();
            Log.d(events.toString(), "VORES EVENTS fra Async");
            for (FiixEventEntity ev : events) {
                // create new instance of MyMemo and add to the list
                FiixEventEntity f = new FiixEventEntity();
                //f.startDateFormatted = ev.startDateFormatted;
                f.title = ev.title;
                f.isInPast = ev.isInPast;
                f.start = ev.start;
                f.startDateFormatted = ev.startDateFormatted;
                f.startTime = ev.startTime;
                copyList.add(f);
            }
            database.close();
        } else {
            database.eventDao().deleteAllEvents();
            List<FiixCalendarEntity> calendars = database.calendarDao().getFiixCalendars();
            ArrayList<String> codes = new ArrayList<String>();
            for(int i=0; i<calendars.size(); i++){
                codes.add(calendars.get(i).code);
            }
            Call<FiixCalendar> calendar1 = fiixService.getOnlineCalendar(codes.get(0));
            Call<FiixCalendar> calendar2 = fiixService.getOnlineCalendar(codes.get(1));
            Call<FiixCalendar> calendar3 = fiixService.getOnlineCalendar(codes.get(2));
            try {
                final FiixCalendar calendarObject1 = calendar1.execute().body();
                if(calendarObject1 != null) {
                    for (FiixEventEntity ev : calendarObject1.events) {
                        FiixEventEntity f = new FiixEventEntity();
                        f.title = ev.title;
                        f.isInPast = ev.isInPast;
                        f.start = ev.start;
                        f.startDateFormatted = ev.startDateFormatted;
                        f.startTime = ev.startTime;
                        if(!ev.isInPast) {
                            copyList.add(f);
                            database.eventDao().insertEvents(f);
                        }
                    }
                }
                final FiixCalendar calendarObject2 = calendar2.execute().body();
                if(calendarObject2 != null) {
                    for (FiixEventEntity ev : calendarObject2.events) {
                        FiixEventEntity f = new FiixEventEntity();
                        f.title = ev.title;
                        f.isInPast = ev.isInPast;
                        f.start = ev.start;
                        f.startDateFormatted = ev.startDateFormatted;
                        f.startTime = ev.startTime;
                        if(!ev.isInPast) {
                            copyList.add(f);
                            database.eventDao().insertEvents(f);
                        }
                    }
                }
                final FiixCalendar calendarObject3 = calendar3.execute().body();
                if(calendarObject3 != null) {
                    for (FiixEventEntity ev : calendarObject3.events) {
                        FiixEventEntity f = new FiixEventEntity();
                        f.title = ev.title;
                        f.isInPast = ev.isInPast;
                        f.start = ev.start;
                        f.startDateFormatted = ev.startDateFormatted;
                        f.startTime = ev.startTime;
                        if(!ev.isInPast) {
                            copyList.add(f);
                            database.eventDao().insertEvents(f);
                        }
                    }
                }
                database.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        Comparator<FiixEventEntity> compareById = new Comparator<FiixEventEntity>() {
            @Override
            public int compare(FiixEventEntity o1, FiixEventEntity o2) {
                return o1.start.compareTo(o2.start);
            }
        };
        Collections.sort(copyList,compareById);
        return copyList;
    }

}
