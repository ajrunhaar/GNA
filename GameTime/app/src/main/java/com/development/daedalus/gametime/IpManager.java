package com.development.daedalus.gametime;

import android.os.AsyncTask;

/**
 * Created by AJR on 2015/02/15.
 */
public class IpManager {

    IpManager(){

    }

    public void UpdateDatabase(){
    }

    private class UpdateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            hierarchyEntry tmpHierarchyEntry;
            ServiceHandler jsonParser = new ServiceHandler();

            List<NameValuePair> myNameValuePairList = new ArrayList<NameValuePair>();
            myNameValuePairList.add(new BasicNameValuePair("username", "root"));
            myNameValuePairList.add(new BasicNameValuePair("password",
                    "password"));
            myNameValuePairList
                    .add(new BasicNameValuePair("table", "hierarchy"));

            String json = jsonParser.makeServiceCall("http://www.daedalus-tech.com/getHierarchy.php",
                    ServiceHandler.GET, myNameValuePairList);

            //Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray Entries = jsonObj.getJSONArray("result");

                        for (int i = 0; i < Entries.length(); i++) {
                            JSONObject entryObj = (JSONObject) Entries.get(i);
                            tmpHierarchyEntry = new hierarchyEntry();
                            tmpHierarchyEntry.setHierarchyEntry(
                                    entryObj.getString("name"),
                                    entryObj.getString("image"),
                                    entryObj.getString("tree_parent"),
                                    entryObj.getString("tree_code"),
                                    entryObj.getString("entity_code"));
                            myDbAdapter.insertEntry(tmpHierarchyEntry);
                            //Log.v("Debug", tmpHierarchyEntry.getTreeCode());
                            //Log.v("Debug", entryObj.getString("name"));
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

            Toast.makeText(getActivity().getApplicationContext(),"Successfully Updated", Toast.LENGTH_SHORT).show();

        }

    }
}




