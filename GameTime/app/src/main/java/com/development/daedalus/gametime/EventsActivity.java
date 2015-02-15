package com.development.daedalus.gametime;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class EventsActivity extends ActionBarActivity {
    List<Event> eventsList;
    BaseAdapter eventsListAdapter;
    ListView eventsListView;
    EventsDbHelper eventsDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        Intent intent = getIntent();
        String receivedEntity = intent.getStringExtra("Entity");

        eventsDbHelper = new EventsDbHelper(this);

        //eventsList = new ArrayList<>();
        eventsList = eventsDbHelper.GetEventFromTeamCode(receivedEntity.substring(4));
        Log.d("EventsActivity", "Search Entity: " + receivedEntity.substring(4));
        eventsListView = (ListView) findViewById(R.id.events_list_view);
        eventsListAdapter = new EventsListViewAdapter(eventsList,this,receivedEntity.substring(4));
        eventsListView.setAdapter(eventsListAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
