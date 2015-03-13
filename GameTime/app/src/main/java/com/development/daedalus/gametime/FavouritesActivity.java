package com.development.daedalus.gametime;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class FavouritesActivity extends ActionBarActivity implements IpManager.UpdateDatabaseAsyncTaskCompleteListener{

    List<Entity> entityList;
    BaseAdapter entityListAdapter;
    ListView entityListView;

    EntitiesDbHelper entitiesDbHelper;
    EventsDbHelper eventsDbHelper;
    IpManager ipManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        /*
         Create empty list with team entities.
         Create and adapter that uses the list of entities.
         Link list view to the adapter
        */
        entityList = new ArrayList<>();
        entityListView = (ListView) findViewById(R.id.entity_list_view);
        entityListAdapter = new EntityListViewAdapter(entityList,this);
        entityListView.setAdapter(entityListAdapter);

        /*
        Get the age/version of all tables.
        */

        /*
        For each table out of date, clear and reload.
        */

        entitiesDbHelper = new EntitiesDbHelper(this);
        eventsDbHelper = new EventsDbHelper(this);

        entitiesDbHelper.ClearEntities();
        eventsDbHelper.ClearEvents();

        ipManager = new IpManager(this,this);



        ipManager.UpdateEventsDatabase();
        ipManager.UpdateEntitiesDatabase();


        entityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Toast.makeText(getApplicationContext(),
//                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
//                        .show();
//
                Intent intent = new Intent(getApplicationContext(),EventsActivity.class);
                intent.putExtra("Entity",entityList.get(position).GetCode());
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favourites, menu);
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
        if (id == R.id.action_calendar) {
            Intent intent = new Intent(this,CalendarActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void UpdateEntitiesDatabaseAsyncTaskComplete(){
        //Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show();
        List<Entity> tmpEntityList = entitiesDbHelper.GetAllEntities();
        entityList.clear();
        for(int i = 0;i<tmpEntityList.size();i++){
            entityList.add(tmpEntityList.get(i));
        }
        entityListAdapter.notifyDataSetChanged();
    }

    public void UpdateEventsDatabaseAsyncTaskComplete(){
        Toast.makeText(this, "Events Successfully Updated", Toast.LENGTH_SHORT).show();
    }
}
