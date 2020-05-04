package com.example.tweetzerland;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText username,password;
    TextView signup;
    Button signin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        signin=findViewById(R.id.login);
        signup=findViewById(R.id.reg);
        signin.setOnClickListener(this);
        signup.setOnClickListener(this);

        firebaseAuth=FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(this,home.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case R.id.login:
                            login();
                       break;
           case R.id.reg:startActivity(new Intent(this,Register.class));
                     finish();
                     break;
        }
    }

   public void login(){
       String usrnm=username.getText().toString().trim();
       String pswrd=password.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(usrnm,pswrd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                  if(task.isSuccessful()){
                     startActivity(new Intent(MainActivity.this,home.class));
                      Toast.makeText(MainActivity.this, "You are logged in now", Toast.LENGTH_SHORT).show();
                      finish();
                  }else{
                      Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                  }
            }
        });
   }
}
