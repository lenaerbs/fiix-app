package com.fiix.app;

import java.util.List;

import com.fiix.app.Entity.FiixEventEntity;

class FiixCalendar {
    String title = "";
    // list fiixevent events
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<FiixEventEntity> events;

    public List getEvents() {
        return events;
    }

    public void setEvents(List<FiixEventEntity> events) {
        this.events = events;
    }

}

