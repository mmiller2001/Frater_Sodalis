package com.marlon.firebasetestapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<News> {


    public ListAdapter(@NonNull Context context, ArrayList<News> newsArrayList) {
        super(context,R.layout.custom_news_item,newsArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        News news = getItem(position);

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_news_item,parent,false);
        }

        ImageView club_logo = listItemView.findViewById(R.id.club_logo);
        TextView club = listItemView.findViewById(R.id.club);
        TextView club_description = listItemView.findViewById(R.id.club_description);
        TextView title = listItemView.findViewById(R.id.title);
        TextView description = listItemView.findViewById(R.id.description);
        TextView time = listItemView.findViewById(R.id.time);
        TextView date = listItemView.findViewById(R.id.club_date);

//        club_logo.setImageResource(R.drawable.frats_color); // Temporary
        club.setText(news.getClub());
        club_description.setText(news.getClub_description());
        title.setText(news.getTitle());
        description.setText(news.getDescription());
        time.setText(news.getTime());
        date.setText(news.getClub_date());

        if(news.getClub_logo().equalsIgnoreCase("Frats")) club_logo.setImageResource(R.drawable.frats_color);
        else if(news.getClub_logo().equalsIgnoreCase("Gammas")) club_logo.setImageResource(R.drawable.gsp);
        else if(news.getClub_logo().equalsIgnoreCase("Galaxy")) club_logo.setImageResource(R.drawable.galaxy_swoosh_navy);
        else if(news.getClub_logo().equalsIgnoreCase("Kojies")) club_logo.setImageResource(R.drawable.kojies);
        else if(news.getClub_logo().equalsIgnoreCase("Siggies")) club_logo.setImageResource(R.drawable.sigma_theta_chi_graphic_2022);
        else if(news.getClub_logo().equalsIgnoreCase("Gata")) club_logo.setImageResource(R.drawable.gata);
        else if(news.getClub_logo().equalsIgnoreCase("Delta Theta")) club_logo.setImageResource(R.drawable.deltatheta);
        else if(news.getClub_logo().equalsIgnoreCase("Trojans")) club_logo.setImageResource(R.drawable.trojans);

        return listItemView;
    }
}
