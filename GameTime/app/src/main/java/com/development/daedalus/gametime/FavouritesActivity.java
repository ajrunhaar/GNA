package com.development.daedalus.gametime;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class FavouritesActivity extends ActionBarActivity implements IpManager.UpdateDatabaseAsyncTaskCompleteListener{

    List<Entity> entityList;
    BaseAdapter entityListAdapter;
    ListView entityListView;

    EntitiesDbHelper entitiesDbHelper;
    IpManager ipManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        entityList = new ArrayList<>();
        entityListView = (ListView) findViewById(R.id.entity_list_view);


        entitiesDbHelper = new EntitiesDbHelper(this);
        //entitiesDbHelper.ClearEntities();

        ipManager = new IpManager(this,this);

        ipManager.UpdateDatabase();


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

        return super.onOptionsItemSelected(item);
    }

    public void UpdateDatabaseAsyncTaskComplete(){

    }
}
