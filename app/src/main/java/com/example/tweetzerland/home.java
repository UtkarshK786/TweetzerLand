package com.example.tweetzerland;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class home extends AppCompatActivity {
    private RecyclerView recyclerView;
    private home_itemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<String> twitters;
    ArrayList<String> following;
    ArrayList<Image> image;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    int i = 0,j=0,k=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Tweeters");
        twitters = new ArrayList<String>();
        following=new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); //increases performance and only have to set when recycler view size is to be fixed
        layoutManager = new LinearLayoutManager(home.this);
        adapter = new home_itemAdapter(twitters,following);
        recyclerView.setLayoutManager(layoutManager);

        databaseReference.addValueEventListener(new ValueEventListener() {
            String usrnm;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        k=0;
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            if (dataSnapshot2.getKey().equals("email")) {
                                usrnm = (String) dataSnapshot2.getValue();
                                if(!twitters.contains(usrnm)&&!usrnm.equals(firebaseAuth.getCurrentUser().getEmail()))
                                twitters.add(usrnm);
                            }

                            Log.i("WE are",dataSnapshot2.toString());
                            if(dataSnapshot2.getValue().equals(firebaseAuth.getCurrentUser().getEmail())){
                                k=1;
                            }
                            if(dataSnapshot2.getKey().equals("follows")&&k==1){
                                for(DataSnapshot dataSnapshot3:dataSnapshot2.getChildren()){
                                    if(!dataSnapshot3.getValue().equals("")&&dataSnapshot2.getKey().equals("follows")&&!following.contains(dataSnapshot3.getValue().toString())) {
                                        following.add(dataSnapshot3.getValue().toString());
                                      Log.i("follow these",dataSnapshot3.getValue().toString());
                                    }
                                }
                                break;
                            }

                        }
                    }
                Log.i("The following list is:",following.toString());
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new home_itemAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(final int position) {
                         i=0;
                        j=0;
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                         if(dataSnapshot.exists()){
                                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                                Log.i("dataSnapshot1",dataSnapshot1.toString());
                                                for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren()){
                                                    Log.i("dataSnapshot2",dataSnapshot2.toString());
                                                    if(dataSnapshot2.getValue().equals(firebaseAuth.getCurrentUser().getEmail())&&!dataSnapshot2.getValue().equals(twitters.get(position))){
                                                        j=1;
                                                    }
                                                    if(dataSnapshot2.getKey().equals("follows")&&j==1&&i==0){
                                                        for(DataSnapshot dataSnapshot3:dataSnapshot2.getChildren()){

                                                            if(!dataSnapshot3.getValue().equals("")&&dataSnapshot2.getChildrenCount()<2){
                                                                databaseReference.child(dataSnapshot1.getKey()).child(dataSnapshot2.getKey()).push().setValue("");
                                                            }
                                                            if(dataSnapshot3.getValue().equals(twitters.get(position))) {
                                                                i = 1;
                                                                databaseReference.child(dataSnapshot1.getKey()).child(dataSnapshot2.getKey()).child(dataSnapshot3.getKey()).removeValue();
                                                                for(int x=-5;x<following.size();x++){
                                                                following.remove(twitters.get(position));}
                                                                Toast.makeText(home.this, "unfollowed", Toast.LENGTH_SHORT).show();

                                                                Log.i("Follow list is:",following.toString());
                                                                break;
                                                            }
                                                        }
                                                        if(i==0&&j==1) {
                                                            databaseReference.child(dataSnapshot1.getKey()).child(dataSnapshot2.getKey()).push().setValue(twitters.get(position));
                                                            if(!following.contains(twitters.get(position)))
                                                            following.add(twitters.get(position));
                                                            Toast.makeText(home.this, "followed", Toast.LENGTH_SHORT).show();

                                                            Log.i("Follow list is:",following.toString());
                                                            i = 1;
                                                            break;
                                                        }
                                                    }
                                                }
                                            }

                                         }

                                Log.i("Follow list is:",following.toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });

        following.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.tweet: openDialogue();
                             break;
            case R.id.feed: startActivity(new Intent(this,Feed.class));
                       break;
            case R.id.logout:
                 firebaseAuth.signOut();
                 startActivity(new Intent(this,MainActivity.class));
                 finish();
                Toast.makeText(this, "You are logged out", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    public void openDialogue(){
           tweetJava tj=new tweetJava();
           tj.show(getSupportFragmentManager(),"TweetzerLand");
    }

}
