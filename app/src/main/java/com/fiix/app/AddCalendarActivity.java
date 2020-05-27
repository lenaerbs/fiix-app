package com.fiix.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.room.Room;

import com.fiix.app.Entity.FiixCalendarEntity;

import java.util.List;

public class AddCalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar);

        FiixDatabase database = Room.databaseBuilder(this, FiixDatabase.class, "fiix-db15")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
        List<FiixCalendarEntity> calendars = database.calendarDao().getFiixCalendars();
        EditText calendarcode1 = (EditText) findViewById(R.id.editText1);
        calendarcode1.setText(calendars.get(0).code);
        EditText calendarcode2 = (EditText) findViewById(R.id.editText2);
        calendarcode2.setText(calendars.get(1).code);
        EditText calendarcode3 = (EditText) findViewById(R.id.editText3);
        calendarcode3.setText(calendars.get(2).code);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                saveCalendars();
            }
        });

    }


    protected void saveCalendars() {
        FiixDatabase database = Room.databaseBuilder(this, FiixDatabase.class, "fiix-db15")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();

        String code1 = ((EditText)findViewById(R.id.editText1)).getText().toString();
        String code2 = ((EditText)findViewById(R.id.editText2)).getText().toString();
        String code3 = ((EditText)findViewById(R.id.editText3)).getText().toString();


        database.calendarDao().updateCalendar(1, code1);
        database.calendarDao().updateCalendar(2, code2);
        database.calendarDao().updateCalendar(3, code3);
        database.close();
        super.onBackPressed();

    }
}
