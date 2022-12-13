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

public class MerchAdapter extends ArrayAdapter<Merch> {

    public MerchAdapter(@NonNull Context context, ArrayList<Merch> merchArrayList) {
        super(context,R.layout.custom_news_merch,merchArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        Merch newMerch = getItem(position);

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_news_merch,parent,false);
        }

        ImageView merch_item = listItemView.findViewById(R.id.merch_item);
        TextView merch_title = listItemView.findViewById(R.id.merch_title);
        TextView merch_description = listItemView.findViewById(R.id.merch_description);
        TextView merch_price = listItemView.findViewById(R.id.merch_price);

        merch_title.setText(newMerch.getMerch_title());
        merch_description.setText(newMerch.getMerch_description());
        merch_price.setText(newMerch.getMerch_price());

        if(newMerch.getMerch_item().equalsIgnoreCase("black_shirt")) merch_item.setImageResource(R.drawable.black_shirt);
        else if(newMerch.getMerch_item().equalsIgnoreCase("blue_cap")) merch_item.setImageResource(R.drawable.blue_cap);
        else if(newMerch.getMerch_item().equalsIgnoreCase("white_cap")) merch_item.setImageResource(R.drawable.white_cap);
        else if(newMerch.getMerch_item().equalsIgnoreCase("white_shirt")) merch_item.setImageResource(R.drawable.white_shirt);
        else if(newMerch.getMerch_item().equalsIgnoreCase("yellow_hoodie")) merch_item.setImageResource(R.drawable.yellow_hoodie);

        return listItemView;
    }
}
