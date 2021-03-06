package com.development.daedalus.gametime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AJR on 2015/02/15.
 */
public class EventsDbHelper extends SQLiteOpenHelper {

    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_START = "startMillis";
    public static final String COLUMN_END = "endMillis";
    public static final String COLUMN_TEAMXCODE= "teamXCode";
    public static final String COLUMN_TEAMYCODE= "teamYCode";
    public static final String COLUMN_LOCATION= "location";

    private static final String DATABASE_NAME = "gametime2.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_EVENTS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_START + " INTEGER NOT NULL, " +
            COLUMN_END + " INTEGER NOT NULL, " +
            COLUMN_TEAMXCODE + " TEXT NOT NULL, " +
            COLUMN_TEAMYCODE + " TEXT NOT NULL, " +
            COLUMN_LOCATION+ " TEXT " + ");";

    public EventsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(EventsDbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    public void ClearEvents() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS,null,null);
        db.close();
    }


    public void InsertEvent(Event event){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_START, event.GetStartMillis());
        values.put(COLUMN_END, event.GetEndMillis());
        values.put(COLUMN_TEAMXCODE, event.GetTeamXCode());
        values.put(COLUMN_TEAMYCODE, event.GetTeamYCode());
        values.put(COLUMN_LOCATION, event.GetLocation());
        db.insert(TABLE_EVENTS,null,values);
        db.close();

    }


    public ArrayList<Event> GetAllEvents() {
        ArrayList events_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENTS;
        Cursor cursor =  db.rawQuery(query,null);

        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            Event event = new Event();
            event.SetStartMillis(cursor.getLong(cursor.getColumnIndex(COLUMN_START)));
            event.SetEndMillis(cursor.getLong(cursor.getColumnIndex(COLUMN_END)));
            event.SetTeamXCode(cursor.getString(cursor.getColumnIndex(COLUMN_TEAMXCODE)));
            event.SetTeamYCode(cursor.getString(cursor.getColumnIndex(COLUMN_TEAMYCODE)));
            event.SetLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
            events_list.add(event);
            cursor.moveToNext();
        }
        return events_list;
    }

    public ArrayList<Event> GetEventFromTeamCode(String TeamCode) {
        ArrayList events_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_TEAMXCODE + " = " + "'" + TeamCode +"'" + " OR " + COLUMN_TEAMYCODE + " = " + "'" + TeamCode +"'";
        Log.d("EventsDbHelper","Query: " + query);
        Cursor cursor =  db.rawQuery(query,null);
        Log.d("EventsDbHelper","cursor size " + cursor.getCount());
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            Event event = new Event();
            event.SetStartMillis(cursor.getLong(cursor.getColumnIndex(COLUMN_START)));
            event.SetEndMillis(cursor.getLong(cursor.getColumnIndex(COLUMN_END)));
            event.SetTeamXCode(cursor.getString(cursor.getColumnIndex(COLUMN_TEAMXCODE)));
            event.SetTeamYCode(cursor.getString(cursor.getColumnIndex(COLUMN_TEAMYCODE)));
            event.SetLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
            events_list.add(event);
            cursor.moveToNext();
        }
        return events_list;
    }

}
