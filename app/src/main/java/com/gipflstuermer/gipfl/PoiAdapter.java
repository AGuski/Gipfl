package com.gipflstuermer.gipfl;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alex on 04.12.15.
 */
public class PoiAdapter extends BaseAdapter{

    private final Context context;
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<PointOfInterest> poiList;

    public PoiAdapter(Context context, LayoutInflater mInflater) {
        this.context = context;
        this.mInflater = mInflater;
        poiList = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return poiList.size();
    }

    @Override
    public Object getItem(int position) {
        return poiList.get(position);
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
            convertView = mInflater.inflate(R.layout.row_poi, null);

            // create a new "Holder" with subviews
            holder = new ViewHolder();
            holder.poiImageView = (ImageView) convertView.findViewById(R.id.img_thumbnail);
            holder.nameTextView = (TextView) convertView.findViewById(R.id.text_name);
            //holder.authorTextView = (TextView) convertView.findViewById(R.id.text_author);

            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        } else {

            // skip all the expensive inflation/findViewById
            // and just get the holder you already made
            holder = (ViewHolder) convertView.getTag();
        }
        // More code after this
        // Get the current book's data in JSON form
        PointOfInterest poi = (PointOfInterest) getItem(position);

        // get the infos & send these Strings to the TextViews for display
        holder.nameTextView.setText(poi.getName());

        try {
            Uri imagePath = Uri.parse(poi.getImageURL());
            holder.poiImageView.setImageURI(imagePath);
        } catch ( NullPointerException e) {
            holder.poiImageView.setImageResource(R.drawable.poi_default_img);
        }

        // If there is an image add that too.

        return convertView;
    }

    public void updateData(ArrayList<PointOfInterest> poiList) {
        // update the adapter's dataset
        this.poiList = poiList;
        notifyDataSetChanged();
    }

    // this is used so you only ever have to do
    // inflation and finding by ID once ever per View
    private static class ViewHolder {
        //public ImageView poiImageView;
        public TextView nameTextView;
        public ImageView poiImageView;
    }
}
