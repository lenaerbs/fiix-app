package com.fiix.app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextSwitcher;
import android.widget.Toast;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.fiix.app.R;

import com.fiix.app.Entity.FiixCalendarEntity;
import com.fiix.app.Entity.FiixEventEntity;
import com.fiix.app.dummy.DummyContent;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, EventListFragment.OnListFragmentInteractionListener {

    FragmentManager manager;
    Fragment currentFragment;

    final String url = "https://fiix.dk/";
    TextSwitcher textSwitcher;
    //Retrofit stuff
    Retrofit retrofit;
    FiixService fiixService;
    ArrayList<FiixEventEntity> copyList = new ArrayList<FiixEventEntity>();

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }

    public void UpdateEventRecyclerView(ArrayList<FiixEventEntity> copyList) {
        EventListFragment myEventlistFragment = (EventListFragment) getSupportFragmentManager().findFragmentById(R.id.eventlist_fragment);
        myEventlistFragment.myAdapter.mValues.clear();
        for (int i = 0; i < copyList.size(); i++) {
            myEventlistFragment.myAdapter.mValues.add(new DummyContent.DummyItem(Integer.toString(i), copyList.get(i).title, null, copyList.get(i).startDateFormatted+"\n"+copyList.get(i).startTime));
        }
        myEventlistFragment.notifyAdapter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Dasebase save and get
        FiixDatabase database = Room.databaseBuilder(this, FiixDatabase.class, "fiix-db15")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();

        List<FiixCalendarEntity> calendars = database.calendarDao().getFiixCalendars();


        if(calendars.size()==0){
            FiixCalendarEntity mycalendar1 = new FiixCalendarEntity();
            mycalendar1.title = "Min test calendar1";
            mycalendar1.id = 1;
            mycalendar1.description = "Her er en lang beskrivelse";
            mycalendar1.code = "";
            database.calendarDao().insertCalendars(mycalendar1);

            FiixCalendarEntity mycalendar2 = new FiixCalendarEntity();
            mycalendar2.title = "Min test calendar2";
            mycalendar2.id = 2;
            mycalendar2.description = "Her er en lang beskrivelse";
            mycalendar2.code = "";
            database.calendarDao().insertCalendars(mycalendar2);

            FiixCalendarEntity mycalendar3 = new FiixCalendarEntity();
            mycalendar3.title = "Min test calendar3";
            mycalendar3.id = 3;
            mycalendar3.description = "Her er en lang beskrivelse";
            mycalendar3.code = "";
            database.calendarDao().insertCalendars(mycalendar3);
        }

        database.close();


      UpdateCalendars();
        if(!isNetworkConnected()) {
            this.setTitle("fiix.dk (Offline)");
        }
    }

    private void UpdateCalendars() {
        // Get fiix.dk events
        retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //create retrofit instance of FiixService
        fiixService = retrofit.create(FiixService.class);

        FiixGetCalendarAsyncTask asyncTask = new FiixGetCalendarAsyncTask(this, fiixService) {
            protected void onPostExecute(ArrayList<FiixEventEntity> events) {
                //Log.d("Response From Asynchronous task:", events.get(0).title);
                UpdateEventRecyclerView(events);
            }
        };
        asyncTask.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateCalendars();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!isNetworkConnected()) {
            this.setTitle("fiix.dk (Offline)");
        }
        else{
            this.setTitle("fiix.dk");
            }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_white) {
            Intent myIntent = new Intent(MainActivity.this, AddCalendarActivity.class);
            MainActivity.this.startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        int id = Integer.parseInt(item.id)+1;
        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
    }

}
