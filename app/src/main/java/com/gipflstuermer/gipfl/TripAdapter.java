package com.gipflstuermer.gipfl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alex on 04.12.15.
 */
public class TripAdapter extends BaseAdapter{

    private final Context context;
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<Trip> tripList;

    public TripAdapter(Context context, LayoutInflater mInflater) {
        this.context = context;
        this.mInflater = mInflater;
        tripList = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return tripList.size();
    }

    @Override
    public Object getItem(int position) {
        return tripList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // check if the view already exists
        // if so, no need to inflate and findViewById again!
        if (convertView == null) {

            // Inflate the custom row layout from your XML.
            convertView = mInflater.inflate(R.layout.row_trip, null);

            // create a new "Holder" with subviews
            holder = new ViewHolder();
            //holder.tripImageView = (ImageView) convertView.findViewById(R.id.img_thumbnail);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.text_title);
            holder.authorTextView = (TextView) convertView.findViewById(R.id.text_author);

            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        } else {

            // skip all the expensive inflation/findViewById
            // and just get the holder you already made
            holder = (ViewHolder) convertView.getTag();
        }
        // More code after this
        // Get the current book's data in JSON form
        Trip trip = (Trip) getItem(position);

        // get the infos & send these Strings to the TextViews for display
        holder.titleTextView.setText(trip.getTitle());
        holder.authorTextView.setText(trip.getAuthor());
        // If there is an image add that too.

        return convertView;
    }

    // this is used so you only ever have to do
    // inflation and finding by ID once ever per View
    private static class ViewHolder {
        //public ImageView tripImageView;
        public TextView titleTextView;
        public TextView authorTextView;
    }
}
