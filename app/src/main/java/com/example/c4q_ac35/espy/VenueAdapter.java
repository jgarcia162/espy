package com.example.c4q_ac35.espy;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.c4q_ac35.espy.db.FavoritesHelper;
import com.example.c4q_ac35.espy.foursquare.Location;
import com.example.c4q_ac35.espy.foursquare.Venue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by c4q-marbella on 8/22/15.
 */
public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.ViewHolder> {
    private static final String PRE_ENDPOINT = "https://maps.googleapis.com/maps/api/streetview?&size=800x400&location=";
    private static final String TAG = "VenueActivity";
    private Location mLocation;
    public List<Venue> mVenues;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_name)
        TextView name;
        @Bind(R.id.item_address)
        TextView address;
        @Bind(R.id.item_phone)
        TextView phone;
        @Bind(R.id.venue_picture)
        ImageView mImageViewVenue;
        @Bind(R.id.menu)
        ImageButton menuBt;
      //  @Bind(R.id.plus)
        FloatingActionButton favButton;
//        @Bind(R.id.shareBt)
//        ImageView mShareButton;
//        @Bind(R.id.ratingBar1)
//        TextView ratingBar;


        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

    public VenueAdapter(Context context, List<Venue> venues) {
        this.mContext = context;
        this.mVenues = venues;
    }


    @Override
    public VenueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(mContext).inflate(R.layout.venue_layout, parent, false);


        return new VenueAdapter.ViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final VenueAdapter.ViewHolder holder, int position) {
        final Venue venue = mVenues.get(position);

        //if(venue.getCategories().equals("food") && venue.getCategories().equals("nightlife spot")) {
        holder.name.setText(venue.getName());
        holder.address.setText(venue.getLocation().getCity());
        holder.phone.setText(venue.getContact().phone);
//        holder.ratingBar.setText("" + venue.getStats().getUsersCount());
//        holder.favButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (FavoritesFragment.venueList != null) {
//                    FavoritesFragment.venueList.add(venue);
//                } else {
//                    FavoritesFragment.venueList = new ArrayList<Venue>();
//                    FavoritesFragment.venueList.add(venue);
//                    Toast.makeText(view.getContext(), FavoritesFragment.venueList.size() + " Favorites ", Toast.LENGTH_SHORT).show();
//                    holder.favButton.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
//
////            final double venueLat = venue.getLocation().getLat();
//            final double venueLon = venue.getLocation().getLng();

        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String venuePhone = venue.getContact().getPhone();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + venuePhone));
                mContext.startActivity(callIntent);
            }
        });

//        holder.mShareButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                String shareIntent= new Intent();
//                Intent sendInvite = new Intent();
//                sendInvite.setAction(Intent.ACTION_SEND);
//                sendInvite.putExtra(Intent.EXTRA_TEXT, venue.getName() + venue.getLocation().getFormattedAddress());
//                sendInvite.setType("text/plain");
//                mContext.startActivity(sendInvite);
//
//                Log.d(TAG, "share button:" + venue.getName() + venue.getLocation().getFormattedAddress());
//            }
//        });
//
//        holder.menuBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Todo: create a webview for menu items
//            }
//        });

        mLocation = venue.getLocation();

        String urlString = PRE_ENDPOINT + mLocation.getLat() + "," + mLocation.getLng();
        URL url = null;
        try {
            url = new URL(urlString);
            Log.d(TAG, "Latitud:  " + mLocation.getLat());
            Log.d(TAG, "Longitud:  " + mLocation.getLng());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        holder.mImageViewVenue.setImageBitmap(null);
        Glide.with(mContext).load(url).centerCrop().into(holder.mImageViewVenue);
        Log.w("TAG", "Called");
    }

    @Override
    public int getItemCount() {
        return mVenues.size();
    }

    private void addToFavorites(String name, String address, String phone, String hours, String tableName, double lat, double lon, SQLiteDatabase database) {
        FavoritesHelper.insertRow(name, address, phone, hours, tableName, lat, lon, database);
    }

    private void addToFaves(View v, int position) {

    }
}

