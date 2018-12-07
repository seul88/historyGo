package com.erwin.historygo.adapters;


// https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_user, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.points = (TextView) convertView.findViewById(R.id.tvPoints);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(user.getName());
        viewHolder.points.setText(Integer.toString(user.getPoints()));

        return convertView;
    }



}
