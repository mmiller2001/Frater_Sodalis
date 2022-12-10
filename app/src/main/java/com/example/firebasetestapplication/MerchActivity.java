package com.example.firebasetestapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MerchActivity extends AppCompatActivity {

    private ListView listview;
    ArrayList<Merch> merchArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merch);

        ImageView backButton = findViewById(R.id.backButton);
        listview = findViewById(R.id.listview);

        merchArrayList.add(new Merch("black_shirt","Black T-Shirt","Customized Black T-Shirt given as passdown since 2006.","$13.99"));
        merchArrayList.add(new Merch("blue_cap","Frater Sodalis Blue Cap","Original and Temporary Blue Cap on Sale. Buy Now!","$25.99"));
        merchArrayList.add(new Merch("white_cap","White Cap","Original and Temporary White Cap on Sale. Buy Now!","$25.99"));
        merchArrayList.add(new Merch("white_shirt","White Milk Shirt","White US BASH T-Shirt. Pass down from 2019","$8.99"));
        merchArrayList.add(new Merch("yellow_hoodie","Yellow Hoodie","Frater Sodalis Yellow Hoodie. Fresh and Clean for low price!","$13.99"));

        MerchAdapter merchAdapter = new MerchAdapter(MerchActivity.this,merchArrayList);
        listview.setAdapter(merchAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Merch myMerch = merchArrayList.get(i);
                Toast.makeText(MerchActivity.this, myMerch.getMerch_item() + " - " + myMerch.getMerch_price(), Toast.LENGTH_SHORT).show();
            }
        });

        // Back Arrow Button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MerchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}