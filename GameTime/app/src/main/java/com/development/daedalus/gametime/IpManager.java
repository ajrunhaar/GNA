package com.development.daedalus.gametime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AJR on 2015/02/15.
 */
public class IpManager {

    static InputStream is = null;
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    EntitiesDbHelper entities_db;
    EventsDbHelper events_db;

    private UpdateDatabaseAsyncTaskCompleteListener listener;


    Context context;

    IpManager(Context context,UpdateDatabaseAsyncTaskCompleteListener listener){
        this.context = context;
        this.listener = listener;
        entities_db = new EntitiesDbHelper(context);
        events_db = new EventsDbHelper(context);
    }

    public void UpdateTableVersions(){
        UpdateTableVersionsAsyncTask updateTableVersionsAsyncTask = new UpdateTableVersionsAsyncTask();
        updateTableVersionsAsyncTask.execute();
    }

    private class UpdateTableVersionsAsyncTask extends AsyncTask<Void, Void, Void> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            List<NameValuePair> myNameValuePairList = new ArrayList<NameValuePair>();
            myNameValuePairList.add(new BasicNameValuePair("username", "root"));
            myNameValuePairList.add(new BasicNameValuePair("password", "password"));

            String json = MakeServiceCall("http://10.0.0.5/GameTime_UpdateTables.php",GET, myNameValuePairList);

            //Log.d("Response: "," "+ json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray Entries = jsonObj.getJSONArray("result");

                        for (int i = 0; i < Entries.length(); i++) {
                            JSONObject entryObj = (JSONObject) Entries.get(i);
                            SharedPreferences sharedPref = context.getSharedPreferences("GNA_Preferences",Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPref.edit();
                            edit.putInt("entities",entryObj.getInt("entities"));
                            edit.putInt("events",entryObj.getInt("events"));

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            SharedPreferences sharedPref = context.getSharedPreferences("GNA_Preferences",Context.MODE_PRIVATE);
            if(sharedPref.getInt("entities",0)!=123){
                UpdateEntitiesDatabase();
            }
            if(sharedPref.getInt("events",0)!=123){
                UpdateEventsDatabase();
            }

        }

    }

    public void UpdateEntitiesDatabase(){
        UpdateEntitiesDatabaseAsyncTask updateEntitiesDatabaseAsyncTask  = new UpdateEntitiesDatabaseAsyncTask();
        updateEntitiesDatabaseAsyncTask.execute();
    }

    private class UpdateEntitiesDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            List<NameValuePair> myNameValuePairList = new ArrayList<NameValuePair>();
            myNameValuePairList.add(new BasicNameValuePair("username", "root"));
            myNameValuePairList.add(new BasicNameValuePair("password", "password"));

            String json = MakeServiceCall("http://10.0.0.5/GameTime_Entities.php",GET, myNameValuePairList);

            //Log.d("Response: "," "+ json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray Entries = jsonObj.getJSONArray("result");

                        for (int i = 0; i < Entries.length(); i++) {
                            JSONObject entryObj = (JSONObject) Entries.get(i);
                            Entity entity = new Entity();
                            entity.SetName(entryObj.getString("name"));
                            entity.SetCode(entryObj.getString("code"));
                            entity.SetLocation(entryObj.getString("location"));

                            entities_db.InsertEntity(entity);

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            listener.UpdateEntitiesDatabaseAsyncTaskComplete();

        }

    }

    public void UpdateEventsDatabase(){
        UpdateEventsDatabaseAsyncTask updateEventsDatabaseAsyncTask  = new UpdateEventsDatabaseAsyncTask();
        updateEventsDatabaseAsyncTask.execute();
    }

    private class UpdateEventsDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            List<NameValuePair> myNameValuePairList = new ArrayList<NameValuePair>();
            myNameValuePairList.add(new BasicNameValuePair("username", "root"));
            myNameValuePairList.add(new BasicNameValuePair("password", "password"));

            String json = MakeServiceCall("http://10.0.0.5/GameTime_Events.php",GET, myNameValuePairList);

            Log.d("Response: "," "+ json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray Entries = jsonObj.getJSONArray("result");

                        for (int i = 0; i < Entries.length(); i++) {
                            JSONObject entryObj = (JSONObject) Entries.get(i);
                            Event event  = new Event();
                            event.SetStartMillis(entryObj.getLong("startMillis"));
                            event.SetEndMillis(entryObj.getLong("endMillis"));
                            event.SetTeamXCode(entryObj.getString("teamXCode"));
                            event.SetTeamYCode(entryObj.getString("teamYCode"));
                            event.SetLocation(entryObj.getString("location"));
                            Log.d("IpManager","X:" + event.GetTeamXCode() + " Y:" + event.GetTeamYCode());
                            events_db.InsertEvent(event);

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            listener.UpdateEventsDatabaseAsyncTaskComplete();

        }

    }

    public String MakeServiceCall(String url, int method,List<NameValuePair> params) {
        try {

            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);

                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {
                // appending params to url

                if (params != null) {
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "?" + paramString;

                }
                Log.e("php URL",url);
                HttpGet httpGet = new HttpGet(url);

                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            response = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error: " + e.toString());
        }

        return response;

    }

    public interface UpdateDatabaseAsyncTaskCompleteListener{
        void UpdateEventsDatabaseAsyncTaskComplete();
        void UpdateEntitiesDatabaseAsyncTaskComplete();
    }
}




