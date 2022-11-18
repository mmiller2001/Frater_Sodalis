package com.example.firebasetestapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.firebasetestapplication.databinding.FragmentHomeBinding;
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
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<News> newsArrayList = new ArrayList<>();
    FragmentHomeBinding binding;
    Context thiscontext;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

//        thiscontext = container.getContext();
//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("https://api.jsonbin.io/v3/b/636b51910e6a79321e444107")
//                .get()
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                if(response.isSuccessful()){
//                    String myResponse = response.body().string();
//                    try {
//                        JSONObject jsonObject = new JSONObject(myResponse);
//                        JSONObject record = jsonObject.getJSONObject("record");
//                        JSONArray club_news = record.getJSONArray("Club_News");
//                        Gson gson = new Gson();
//
//                        Type listType = new TypeToken<ArrayList<News>>(){}.getType();
//                        List<News> total_News = gson.fromJson(club_news.toString(),listType);
//
//                        for(News news : total_News) {
//                            newsArrayList.add(news);
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    Runnable runnable = new Runnable() {
//                        @Override
//                        public void run() {
//                            ListAdapter listAdapter = new ListAdapter(thiscontext, newsArrayList);
//                            binding.listview.setAdapter(listAdapter);
//                            binding.listview.setClickable(true);
//                            binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                                }
//                            });
//                        }
//                    };
//
////                    MainActivity.this.runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            ListAdapter listAdapter = new ListAdapter(HomeFragment.this, newsArrayList);
////                            binding
////                        }
////                    });
//                }
//            }
//        });
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}