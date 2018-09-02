package com.erwin.historygo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.erwin.historygo.R;
import com.erwin.historygo.api.PlaceModel;
import com.erwin.historygo.api.UserModel;

import java.util.ArrayList;

public class PlaceAdapter extends ArrayAdapter<PlaceModel> {




    public PlaceAdapter(Context context, ArrayList<PlaceModel> users) {
        super(context, 0, users);
    }

    private static class ViewHolder {
        TextView name;
        TextView points;
        TextView description;
        TextView rating;
        TextView year;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PlaceModel place = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_place, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.points = (TextView) convertView.findViewById(R.id.tvPoints);
          //  viewHolder.description = (TextView) convertView.findViewById(R.id.tvDescription);
         //   viewHolder.year = (TextView) convertView.findViewById(R.id.tvYear);
       //     viewHolder.rating = (TextView) convertView.findViewById(R.id.tvRating);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(place.getName());
        viewHolder.points.setText(Integer.toString(place.getPoints()));
     //   viewHolder.description.setText(place.getDescription());
     //   viewHolder.year.setText(Integer.toString(place.getYear()));
     //   viewHolder.rating.setText(Double.toString(place.getRating()));
        return convertView;
    }



}
