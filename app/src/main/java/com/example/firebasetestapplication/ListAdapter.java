package com.example.firebasetestapplication;

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
        super(context, R.layout.custom_news_item,newsArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        News news = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_news_item,parent,false);
        }

        ImageView club_logo = convertView.findViewById(R.id.club_logo);
        TextView club = convertView.findViewById(R.id.club);
        TextView club_description = convertView.findViewById(R.id.club_description);
        TextView title = convertView.findViewById(R.id.title);
        TextView description = convertView.findViewById(R.id.description);
        TextView time = convertView.findViewById(R.id.time);
        
        club_logo.setImageResource(R.drawable.frats_color); // Temporary
        club.setText(news.club);
        club_description.setText(news.club_description);
        title.setText(news.title);
        description.setText(news.description);
        time.setText(news.time);

        return convertView;
    }
}
