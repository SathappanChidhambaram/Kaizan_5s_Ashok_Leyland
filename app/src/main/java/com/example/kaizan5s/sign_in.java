package com.example.kaizan5s;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class sign_in extends AppCompatActivity {

    EditText epass;
    Button submit;
    TextView tvname;

    SharedPreferences pref;

    String stname,stpass;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tvname=findViewById(R.id.tvname);
        epass=findViewById(R.id.epass);

        submit=findViewById(R.id.submit);

        pref=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        stname=pref.getString("send","").trim();
        tvname.setText(stname);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nonempty())
                {
                   isauth();
                }
            }
        });




    }

    private boolean nonempty() {
        //String sname=ename.getText().toString();
        String spass=epass.getText().toString();
//        if(sname.isEmpty())
//        {
//         ename.setError("Name can't be empty");
//         return false;
//        }
        if(spass.isEmpty())
        {
            epass.setError("Password can't be empty");
            return false;
        }
        return true;
    }

    private void isauth()
    {

        if(stname.contains(" "))
        stname=stname.replace(" ","_");
        if(stname.contains("."))
        stname=stname.replace(".",",");

        stpass=epass.getText().toString().trim();
        //Log.e("get",stname+" "+stpass);

       myRef.child("password").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String pass=dataSnapshot.child(stname).getValue(String.class);
               if(pass.equals(stpass.trim()))
               {
                   Intent intent=new Intent(sign_in.this,part_stat.class);
                   startActivity(intent);
                   finish();
               }
               else
               {
                   epass.setError("Password doesn't match");
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }
}
