package com.example.tweetzerland;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.app.AlertDialog.*;

public class tweetJava extends AppCompatDialogFragment {
   int t=0,i=0;
    private EditText editText;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.tweet_dialogue,null);
        builder.setView(view).setTitle("Tweet").setNegativeButton("cancel", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("tweet", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                  String tweetz=editText.getText().toString().trim();
                  saveTweetz(tweetz);
            }
        });
        editText=view.findViewById(R.id.editText);
                return builder.create();
    }
public void saveTweetz(final String tweetz){
        firebaseAuth=FirebaseAuth.getInstance();
         t=0;
         i=0;
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Tweeters");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren()){
                            if(dataSnapshot2.getKey().equals("email")&&dataSnapshot2.getValue().equals(firebaseAuth.getCurrentUser().getEmail())){
                                t=1;
                                continue;
                            }

                            if(t==1&&dataSnapshot2.getKey().equals("tweets")&&i==0){
                                Log.i("DataSnapshot2",dataSnapshot2.toString());
                                t=0;
                                i=1;
                                databaseReference.child(dataSnapshot1.getKey()).child(dataSnapshot2.getKey()).push().setValue(tweetz);
                                break;
                            }
                        }
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
}
}

