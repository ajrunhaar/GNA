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
public class EntitiesDbHelper extends SQLiteOpenHelper{

    public static final String TABLE_ENTITIES = "entities";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_CODE= "code";

    private static final String DATABASE_NAME = "gametime.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_ENTITIES + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_LOCATION + " TEXT, " +
            COLUMN_CODE + " TEXT NOT NULL " + ");";

    public EntitiesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(EntitiesDbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTITIES);
        onCreate(db);
    }

    public void ClearEntities() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ENTITIES,null,null);
        db.close();
    }


    public void InsertEntity(Entity entity){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, entity.GetName());
        values.put(COLUMN_LOCATION, entity.GetLocation());
        values.put(COLUMN_CODE, entity.GetCode());

        db.insert(TABLE_ENTITIES,null,values);
        db.close();

    }

    public List<Entity> GetAllEntities() {
        ArrayList entity_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ENTITIES;
        Cursor cursor =  db.rawQuery(query,null);

        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            Entity entity = new Entity();

            entity.SetName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            entity.SetLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
            entity.SetCode(cursor.getString(cursor.getColumnIndex(COLUMN_CODE)));
            entity_list.add(entity);
            cursor.moveToNext();
        }
        db.close();
        return entity_list;
    }
}
