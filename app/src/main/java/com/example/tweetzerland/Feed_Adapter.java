package com.example.tweetzerland;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class Feed_Adapter extends RecyclerView.Adapter<Feed_Adapter.Feed_AdapterHolder> {
//private ArrayList<String> tweeters;
private ArrayList<String> tweeting;
private ArrayList<String> tweets;

private HashMap<String,ArrayList<String>> tweeters;
    @NonNull
    @Override
    public Feed_AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item,parent,false);
        Feed_AdapterHolder feed_adapterHolder=new Feed_AdapterHolder(v);
        return feed_adapterHolder;

    }

    public Feed_Adapter(ArrayList<String> tweeting,ArrayList<String> tweets){
        this.tweeting=tweeting;
        this.tweets=tweets;
//        this.tweets=tweets;
    }

    @Override
    public void onBindViewHolder(@NonNull Feed_AdapterHolder holder, int position) {
//           tweeters.toString();
           String currentItem1= tweeting.get(position);
           String currentItem2=tweets.get(position);
//          String s= currentItem[0];
//              ArrayList<String> currentItem1=tweeters.get(position);
//              String key=tweeters.get
//              String currentItem2=tweeters.get(position);
//        String str= currentItem1.get(0);
              holder.textView2.setText(currentItem1);
              holder.textView3.setText(currentItem2);
    }

    @Override
    public int getItemCount() {
        return tweeting.size();
    }

    public static class Feed_AdapterHolder extends RecyclerView.ViewHolder{
        public TextView textView2;  //tweeter's name/id
        public TextView textView3;  //tweet

        public Feed_AdapterHolder(@NonNull View itemView) {
            super(itemView);
            textView2=itemView.findViewById(R.id.textView2);
            textView3=itemView.findViewById(R.id.textView3);
        }
    }
}
