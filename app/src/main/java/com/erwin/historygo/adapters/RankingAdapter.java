package com.erwin.historygo.adapters;


// https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.erwin.historygo.MainActivity;
import com.erwin.historygo.R;
import com.erwin.historygo.api.UserModel;

import java.util.ArrayList;

public class RankingAdapter extends ArrayAdapter<UserModel> {

    public RankingAdapter(Context context, ArrayList<UserModel> users) {
        super(context, 0, users);
    }

    private static class ViewHolder {
        TextView name;
        TextView points;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UserModel user = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
           // convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_user, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.points = (TextView) convertView.findViewById(R.id.tvPoints);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }
/*
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvPoints = (TextView) convertView.findViewById(R.id.tvPoints);

        tvName.setText(user.getName());
        tvPoints.setText(Integer.toString(user.getPoints()));
*/
        viewHolder.name.setText(user.getName());
        viewHolder.points.setText(Integer.toString(user.getPoints()));

        return convertView;
    }



}
