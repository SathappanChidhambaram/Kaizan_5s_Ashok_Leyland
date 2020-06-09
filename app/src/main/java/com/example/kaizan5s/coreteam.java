package com.example.kaizan5s;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class coreteam extends AppCompatActivity {

    LinearLayout llpass,llact,llnew,lldel;

    EditText cpass,nname,npass;

    Spinner spinact,spinemp;

    Button submit,badd,brem;

    String cval;
    String sname,spass;

    String arract[]={"ADD","REMOVE"};
    ArrayList<String> arremp=new ArrayList<>();
    ArrayAdapter<String> adapact,adapemp;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference mpost=database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coreteam);

        llpass=findViewById(R.id.llpass);
        llact=findViewById(R.id.llact);
        llnew=findViewById(R.id.llnew);
        lldel=findViewById(R.id.lldel);

        cpass=findViewById(R.id.cpass);
        nname=findViewById(R.id.nname);
        npass=findViewById(R.id.npass);

        spinact=findViewById(R.id.spinact);
        spinemp=findViewById(R.id.spinemp);

        submit=findViewById(R.id.submit);
        badd=findViewById(R.id.badd);
        brem=findViewById(R.id.brem);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

       spinact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(spinact.getSelectedItem().toString().equals("ADD"))
               {
                   lldel.setVisibility(View.GONE);
                   llnew.setVisibility(View.VISIBLE);
               }
               else
               {
                   llnew.setVisibility(View.GONE);
                   lldel.setVisibility(View.VISIBLE);
                   myRef.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           for(DataSnapshot d1:dataSnapshot.child("password").getChildren())
                           {
                               String name=d1.getKey();
                               name=name.replace("_"," ");
                               name=name.replace(",",".");
                               arremp.add(name);

                           }
                           adapemp=new ArrayAdapter<>(coreteam.this,android.R.layout.simple_spinner_dropdown_item,arremp);
                           spinemp.setAdapter(adapemp);
                       }

                       @Override
                       public void onCancelled(DatabaseError error) {

                       }
                   });

               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

       badd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(checkadd())
               {
                   sname=sname.replace(" ","_");
                   sname=sname.replace(".",",");
                   myRef.child("password").child(sname).setValue(spass);
                   Toast.makeText(coreteam.this,"Employee Details Added",Toast.LENGTH_SHORT).show();
                   nname.setText("");
                   npass.setText("");
               }
           }
       });

       brem.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               try {
                   String srem = spinemp.getSelectedItem().toString();
                   srem = srem.replace(" ", "_");
                   srem = srem.replace(".", ",");
                   mpost = mpost.child("password").child(srem);
                   mpost.removeValue();
                   Toast.makeText(coreteam.this,"Employee Details Removed",Toast.LENGTH_SHORT).show();
               }
               catch (Exception e)
               {

               }
           }
       });
    }

    private void check()
    {
        cval=cpass.getText().toString().trim();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pass = dataSnapshot.child("coreteam").getValue(String.class);
                if(cval.equals(pass))
                {
                    llpass.setVisibility(View.GONE);
                    llact.setVisibility(View.VISIBLE);
                    adapact=new ArrayAdapter<>(coreteam.this,android.R.layout.simple_spinner_dropdown_item,arract);
                    spinact.setAdapter(adapact);
                }
                else
                {
                    cpass.setError("Password doesn't match");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private boolean checkadd()
    {
        sname=nname.getText().toString().trim();
        spass=npass.getText().toString().trim();
        if(sname.isEmpty())
        {
            nname.setError("This field is required");
            return false;
        }
        if(spass.isEmpty())
        {
            npass.setError("This field is required");
            return false;
        }
        return true;
    }
}
