package com.development.daedalus.gametime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AJR on 2015/02/15.
 */
public class EntityListViewAdapter extends BaseAdapter {

    private List<Entity> entityList;
    private Context context;

    public EntityListViewAdapter(List<Entity> entityList, Context context) {
        super();
        this.entityList = entityList;
        this.context = context;
    }

    @Override
    public int getCount() {

        return entityList.size();
    }

    @Override
    public Entity getItem(int position) {
        return entityList.get(position);
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
            v = mInflater.inflate(R.layout.entity_list_view_single, null);
        }

        final TextView textViewEntityName = (TextView) v.findViewById(R.id.entity_name);
        final TextView textViewEntityLocation = (TextView) v.findViewById(R.id.entity_location);
        final TextView textViewEntityCode = (TextView) v.findViewById(R.id.entity_code);

        textViewEntityName.setText(entityList.get(position).GetName());
        textViewEntityLocation.setText(entityList.get(position).GetLocation());
        textViewEntityCode.setText(entityList.get(position).GetCode());

        return v;
    }
}
