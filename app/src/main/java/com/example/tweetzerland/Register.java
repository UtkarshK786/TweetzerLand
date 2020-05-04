package com.example.tweetzerland;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText username,password;
    TextView signin;
    Button register;
    private FirebaseAuth firebaseAuth;
   DatabaseReference databaseReference;
   Twitters twitters;
   int maxId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        register=findViewById(R.id.register);
        signin=findViewById(R.id.login);
        signin.setOnClickListener(this);
        register.setOnClickListener(this);

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Tweeters");
        twitters=new Twitters();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 maxId= (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                Toast.makeText(this, "You are logged in now", Toast.LENGTH_SHORT).show();
                break;
            case R.id.register:
                  register();
                break;
        }
    }

    private void register(){
        final String usrnm=username.getText().toString();
        final String pswrd=password.getText().toString();
        if(usrnm.isEmpty()){
            username.setError("Email required");
            username.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(usrnm).matches()){
            username.setError("Please enter a valid email");
            username.requestFocus();
            return;
        }

        else if(pswrd.isEmpty()){
                password.setError("Password required");
                password.requestFocus();
                return;
            }
        else if(pswrd.length()<6){
            password.setError("Password length should be minimum 6");
            password.requestFocus();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(usrnm,pswrd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                  if(task.isSuccessful()){
                      twitters.setEmail(usrnm.trim());
                      twitters.setPassword(pswrd.trim());
                      twitters.setFollows("");
                      twitters.setTweets("");
                      databaseReference.child(String.valueOf(maxId+1)).setValue(twitters);
                      Toast.makeText(Register.this, "Regisration successful", Toast.LENGTH_SHORT).show();
                      startActivity(new Intent(Register.this,MainActivity.class));
                      finish();
                  }else{
                      Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                  }
            }
        });
    }
}
