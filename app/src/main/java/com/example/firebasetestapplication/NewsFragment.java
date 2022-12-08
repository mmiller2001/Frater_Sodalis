package com.example.firebasetestapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasetestapplication.databinding.FragmentNewsBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    ArrayList<News> newsArrayList = new ArrayList<>();

    private boolean afterArrayData = false;
//    FragmentNewsBinding binding;
//    Context thiscontext;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_news,container,false);
//        TextView textview = v.findViewById(R.id.textview);
        ListView listview = v.findViewById(R.id.listview);

//        thiscontext = container.getContext();
//        textview.setText("");

//        textView.setText("Trying for the first time");

//        Inflate the layout for this fragment

        newsArrayList.add(new News("Frats","Social Club at Abilene Christian University","Frater Sodalis","Haunted Forrest","Be the first to register to Haunted Forest 2023! This year we will be having an exciting opportunity to scare you with the members of our fraternity!","8:15PM","Friday, December 30, 2022"));
        newsArrayList.add(new News("Gammas","Social Club at Abilene Christian University","Gamma Sigma Phi","68 Hour Volleyball","Enter our exciting, non-stop, 68 hour volleyball. Lot's of fun with friends, volleyball and no rest!","3:25PM","Tuesday, December 20, 2022"));
        newsArrayList.add(new News("Kojies","Social Club at Abilene Christian University","Ko Jo Kai","Marathon","Sign up for our annual Marathon. Run and support the local communities in Abilene as they battle through violence and poverty.","7:00PM","Wednesday, December 28, 2022"));
        newsArrayList.add(new News("Trojans","Social Club at Abilene Christian University","Trojans","Chapel","We welcome you to our private chapel where everyone will listen and get to know how men are trying to be better men for Trojans. We will shout in your ear if necessary until you remember us","6:00PM","Thursday, December 29, 2022"));
        newsArrayList.add(new News("Delta Theta","Social Club at Abilene Christian University","Delta Theta","Soccer Practice","We invite you to form part of our tradition in playing soccer for 24 consecutive straight hours. Every club is welcome to participate with how many teams they feel necessary to win and the first place price gets an iPhoneX","12:00PM","Thursday, December 22, 2022"));
        newsArrayList.add(new News("Galaxy","Social Club at Abilene Christian University","Galaxy","Football Intramural Tournament","Get ready to create your own team and compete in our tournament. Winner takes a discount from your favorite restaurant and a trofee!","7:00PM","Saturday, December 24, 2022"));
        newsArrayList.add(new News("Gata","Social Club at Abilene Christian University","Gata","Friendsgiving","Exclusive for Frater Sodalis and Gata Members only. Feel free to come and hang out with us from 6:00PM-8:00PM on our Friendsgiving event. We will have lots of fun with friends, food, games and fun conversations for you to meet new people or see old ones as well.","6:00PM","Sunday, December 25, 2022"));
        newsArrayList.add(new News("Siggies","Social Club at Abilene Christian University","Sigma Theta Chi","Siggie Pavilion Concert","Exclusive for this one in a lifetime only, please come to our concert for Harry Styles. Performing for our dear Abilene Christian University Students, Harry Styles will personally perform his new upcoming songs for his new album. Don't miss it","11:00PM","Monday, December 26, 2022"));
        //        buildRequest(); // takes too much time so it skips to ListAdapter

        ListAdapter listAdapter = new ListAdapter(getActivity(),newsArrayList);
        listview.setAdapter(listAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News myNews = newsArrayList.get(i);
                Toast.makeText(getActivity(), myNews.getClub() + " " + myNews.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                ListAdapter listAdapter = new ListAdapter(getActivity(),newsArrayList);
//                listview.setAdapter(listAdapter); // line that breaks program
//            }
//        });

//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                ListAdapter listAdapter = new ListAdapter(getActivity(),newsArrayList);
//                listview.setAdapter(listAdapter); // line that breaks program
//            }
//        });


        return v;
    }

    private void buildRequest() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.jsonbin.io/v3/b/636b51910e6a79321e444107")
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String myResponse = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(myResponse);
                        JSONObject record = jsonObject.getJSONObject("record");
                        JSONArray club_news = record.getJSONArray("Club_News");
                        Gson gson = new Gson();

                        Type listType = new TypeToken<ArrayList<News>>(){}.getType();
                        List<News> total_News = gson.fromJson(club_news.toString(),listType);

                        for(News news : total_News) {

                            //textview.append("Club: " + news.club + " " + news.description + "\n");
                            newsArrayList.add(news);
                        }

                        // tell UI to update NOW
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}