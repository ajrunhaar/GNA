package com.development.daedalus.gametime;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by AJR on 2015/02/15.
 */
public class EventsListViewAdapter extends BaseAdapter {

    private List<Event> eventsList;
    private Context context;
    private String entity;

    public EventsListViewAdapter(List<Event> eventsList, Context context, String entity) {
        super();
        this.eventsList = eventsList;
        this.context = context;
        this.entity = entity;
    }

    @Override
    public int getCount() {

        return eventsList.size();
    }

    @Override
    public Event getItem(int position) {
        return eventsList.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = mInflater.inflate(R.layout.event_list_view_single, null);
        }

        final TextView textViewEventOpponent = (TextView) v.findViewById(R.id.event_opponent);
        final TextView textViewEventLocation = (TextView) v.findViewById(R.id.event_location);
        final TextView textViewEventDateTime = (TextView) v.findViewById(R.id.event_start_date_time);

        if(eventsList.get(position).GetTeamXCode().equals(entity)) {
            textViewEventOpponent.setText(eventsList.get(position).GetTeamYCode());
        }else{
            textViewEventOpponent.setText(eventsList.get(position).GetTeamXCode());
        }
        textViewEventLocation.setText(eventsList.get(position).GetLocation());

        //TODO:Calendar creating null pointer exception
        Calendar calendar = Calendar.getInstance();
        //calendar.setTimeInMillis(eventsList.get(position).GetStartMillis());
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd - HH:mm:ss");
        //Log.d("Test", "XX=" + sdf.format(calendar));
        //textViewEventDateTime.setText(sdf.format(calendar));

        return v;
    }
}
