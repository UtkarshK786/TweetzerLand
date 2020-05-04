package com.example.tweetzerland;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Feed extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> tweeting;
    private ArrayList<String> tweets;
    private HashMap<String, ArrayList<String>> tweeters;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    int i,j,k;
    String str,str1,str2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        tweeting=new ArrayList<String>();
        tweets=new ArrayList<String>();
        ArrayList<String> stringss=new ArrayList<String>();
        stringss.add(0,"lelelel");
        tweeters=new HashMap<String, ArrayList<String>>();
        tweeters.put("lelele",stringss);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Tweeters");
        firebaseAuth=FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); //increases performance and only have to set when recycler view size is to be fixed
        layoutManager = new LinearLayoutManager(Feed.this);
        adapter = new Feed_Adapter(tweeting,tweets);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i=0;
                j=0;
                str="";
                str1="";
                if(dataSnapshot.exists())
                      for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                          for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren()){
                              if(dataSnapshot2.getKey().equals("email")&&dataSnapshot2.getValue().equals(firebaseAuth.getCurrentUser().getEmail())){
                                  i=1;
                                  continue;
                              }
                              if(dataSnapshot2.getKey().equals("follows")&&i==1){
                                  for(final DataSnapshot dataSnapshot3:dataSnapshot2.getChildren()){
//                                      Log.i("Lists",dataSnapshot3.toString());
                                      if(dataSnapshot3.getValue()!=""){//got the name of the people who are followed, now let's find there tweets
//                                          str1=dataSnapshot3.getValue().toString();
                                          databaseReference.addValueEventListener(new ValueEventListener() {
                                              @Override
                                              public void onDataChange(@NonNull DataSnapshot dataSnapshot4) {
                                                  k = 0;
//                                                  }
                                                  if(dataSnapshot4.exists()) {
                                                     for(DataSnapshot dataSnapshot5:dataSnapshot4.getChildren()) {
                                                          for(DataSnapshot dataSnapshot6:dataSnapshot5.getChildren()) {
                                                              if(dataSnapshot6.getKey().equals("email")&&dataSnapshot6.getValue().equals(dataSnapshot3.getValue().toString())) {
                                                                  Log.i("DataSnapshot6", dataSnapshot6.toString());
                                                                  Log.i("Map", tweeters.toString());
                                                                  k=1;
                                                                  str1=dataSnapshot3.getValue().toString();
                                                              }
                                                              if(k==1&&dataSnapshot6.getKey().equals("tweets")){
                                                                           for(DataSnapshot dataSnapshot7:dataSnapshot6.getChildren()){
                                                                               Log.i("DataSnapshot7", dataSnapshot7.getValue().toString());
//                                                                               checkwan.add(dataSnapshot7.getValue().toString());
//                                                                               tweeters.put(dataSnapshot3.getValue().toString(),checkwan);
//                                                                               Log.i("Mapping",tweeters.toString());
//                                                                               tweeting.add("dssdc");
                                                                               str2=dataSnapshot7.getValue().toString();
//                                                                               Toast.makeText(Feed.this, tweeters.toString(), Toast.LENGTH_SHORT).show();
                                                                               tweeting.add(str1);
                                                                               tweets.add(str2);

                                                                           }
                                                                           k=0;
                                                              }

                                                          }
                                                     }
                                                  }

                                                  Log.i("tweeting",tweeting.toString());
                                                  Log.i("tweets",tweets.toString());
                                                if(!tweets.isEmpty()&&!tweeting.isEmpty())
                                                   recyclerView.setAdapter(adapter);

                                              }

                                              @Override
                                              public void onCancelled(@NonNull DatabaseError databaseError) {

                                              }
                                          });
                                      }
                                  }
                              }
                          }
                      }
         Log.i("tweeting",tweeting.toString());
                      Log.i("tweets",tweets.toString());
          }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        Log.i("Maps hi Maps",tweeters.toString());
//
    }

}


//                                                  if (dataSnapshot4.exists()) {
//                                                          for (DataSnapshot dataSnapshot5 : dataSnapshot4.getChildren()) {
//                                                          for (DataSnapshot dataSnapshot6 : dataSnapshot5.getChildren())
//                                                          for(DataSnapshot dataSnapshot7:dataSnapshot6.getChildren()){
//                                                          Log.i("dataSnapshot7",dataSnapshot7.toString());
//                                                          if (dataSnapshot7.getKey().equals("email") && dataSnapshot7.getValue().equals(dataSnapshot3.getValue()))
////                                                                  ;//that email
//                                                          {
//                                                          Log.i("Lists",dataSnapshot3.toString());
//                                                          k = 1;
//                                                          str= (String) dataSnapshot3.getValue();
////                                                                  continue;
//                                                          }
//                                                          if (k == 1 && dataSnapshot7.getKey().equals("tweets")){
////                                                                      tweeters.put(str,)
//                                                          for(DataSnapshot dataSnapshot8:dataSnapshot7.getChildren()){
//                                                          tweeters.put(str,dataSnapshot7.getValue().toString());
//                                                          }
//                                                          }
//                                                          }
//                                                          }