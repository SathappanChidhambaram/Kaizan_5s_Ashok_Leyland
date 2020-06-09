package com.example.kaizan5s;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class overall_status extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    LinearLayout llstat;
    Spinner spinyears,spinmonths;
    Button check;

    ArrayAdapter<String> adapmonth;
    ArrayAdapter<Integer> adapyear;

    String arrmonth[]={"January (01)","February (02)","March (03)","April (04)","May (05)","June (06)","July (07)","August (08)","September (09)",
            "October (10)", "November (11)","December (12)"};
    int gyear= Calendar.getInstance().get(Calendar.YEAR);

    TextView e101w1,e102w1,e105w1,e106w1,e109w1,e110w1,e111w1,e117w1,e119w1,e128w1,e130w1,e135w1,e136w1,e137w1,e138w1;
    TextView e101w3,e102w3,e105w3,e106w3,e109w3,e110w3,e111w3,e117w3,e119w3,e128w3,e130w3,e135w3,e136w3,e137w3,e138w3;

    String syear,smonth;
    int sdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_status);

        llstat=findViewById(R.id.llstat);
        spinyears=findViewById(R.id.spinyears);
        spinmonths=findViewById(R.id.spinmonths);
        check=findViewById(R.id.check);

        int nyear=gyear-1;
        Integer[] arryear={gyear,nyear};
        adapyear=new ArrayAdapter<>(overall_status.this,android.R.layout.simple_spinner_dropdown_item,arryear);
        spinyears.setAdapter(adapyear);

        adapmonth=new ArrayAdapter<>(overall_status.this,android.R.layout.simple_spinner_dropdown_item,arrmonth);
        spinmonths.setAdapter(adapmonth);

        e101w1=findViewById(R.id.e101w1);
        e102w1=findViewById(R.id.e102w1);
        e105w1=findViewById(R.id.e105w1);
        e106w1=findViewById(R.id.e106w1);
        e109w1=findViewById(R.id.e109w1);

        e110w1=findViewById(R.id.e110w1);
        e111w1=findViewById(R.id.e111w1);
        e117w1=findViewById(R.id.e117w1);
        e119w1=findViewById(R.id.e119w1);
        e128w1=findViewById(R.id.e128w1);

        e130w1=findViewById(R.id.e130w1);
        e135w1=findViewById(R.id.e135w1);
        e136w1=findViewById(R.id.e136w1);
        e137w1=findViewById(R.id.e137w1);
        e138w1=findViewById(R.id.e138w1);

        e101w3=findViewById(R.id.e101w3);
        e102w3=findViewById(R.id.e102w3);
        e105w3=findViewById(R.id.e105w3);
        e106w3=findViewById(R.id.e106w3);
        e109w3=findViewById(R.id.e109w3);

        e110w3=findViewById(R.id.e110w3);
        e111w3=findViewById(R.id.e111w3);
        e117w3=findViewById(R.id.e117w3);
        e119w3=findViewById(R.id.e119w3);
        e128w3=findViewById(R.id.e128w3);

        e130w3=findViewById(R.id.e130w3);
        e135w3=findViewById(R.id.e135w3);
        e136w3=findViewById(R.id.e136w3);
        e137w3=findViewById(R.id.e137w3);
        e138w3=findViewById(R.id.e138w3);

        sdate= Calendar.getInstance().get(Calendar.DATE);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syear=spinyears.getSelectedItem().toString();
                smonth=spinmonths.getSelectedItem().toString();
                smonth=smonth.substring(smonth.length()-3,smonth.length()-1);
                checke101();
                checke102();
                checke105();
                checke106();
                checke109();

                checke110();
                checke111();
                checke117();
                checke119();
                checke128();

                checke130();
                checke135();
                checke136();
                checke137();
                checke138();

                llstat.setVisibility(View.VISIBLE);
            }
        });


    }

    public void checke101()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sweek1=dataSnapshot.child("audit").child(syear).child(smonth).child("Week1").child("E101").child("date").getValue(String.class);
                String sweek3=dataSnapshot.child("audit").child(syear).child(smonth).child("Week3").child("E101").child("date").getValue(String.class);
                if(sweek1==null && sdate<15)
                {
                    e101w1.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek1==null && sdate>=15)
                {
                    e101w1.setBackgroundColor(getResources().getColor(R.color.holo_red));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)>=15)
                {
                    e101w1.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)<15)
                {
                    e101w1.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }

                if(sweek3==null)
                {
                    e101w3.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek3!=null)
                {
                    e101w3.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }
                //if(sweek3!=null)
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
    }

    public void checke102()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sweek1=dataSnapshot.child("audit").child(syear).child(smonth).child("Week1").child("E102").child("date").getValue(String.class);
                String sweek3=dataSnapshot.child("audit").child(syear).child(smonth).child("Week3").child("E102").child("date").getValue(String.class);
                if(sweek1==null && sdate<15)
                {
                    e102w1.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek1==null && sdate>=15)
                {
                    e102w1.setBackgroundColor(getResources().getColor(R.color.holo_red));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)>=15)
                {
                    e102w1.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)<15)
                {
                    e102w1.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }

                if(sweek3==null)
                {
                    e102w3.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek3!=null)
                {
                    e102w3.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }
                //if(sweek3!=null)
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
    }

    public void checke105()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sweek1=dataSnapshot.child("audit").child(syear).child(smonth).child("Week1").child("E105").child("date").getValue(String.class);
                String sweek3=dataSnapshot.child("audit").child(syear).child(smonth).child("Week3").child("E105").child("date").getValue(String.class);
                if(sweek1==null && sdate<15)
                {
                    e105w1.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek1==null && sdate>=15)
                {
                    e105w1.setBackgroundColor(getResources().getColor(R.color.holo_red));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)>=15)
                {
                    e105w1.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)<15)
                {
                    e105w1.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }

                if(sweek3==null)
                {
                    e105w3.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek3!=null)
                {
                    e105w3.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }
                //if(sweek3!=null)
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
    }

    public void checke106()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sweek1=dataSnapshot.child("audit").child(syear).child(smonth).child("Week1").child("E106").child("date").getValue(String.class);
                String sweek3=dataSnapshot.child("audit").child(syear).child(smonth).child("Week3").child("E106").child("date").getValue(String.class);
                if(sweek1==null && sdate<15)
                {
                    e106w1.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek1==null && sdate>=15)
                {
                    e106w1.setBackgroundColor(getResources().getColor(R.color.holo_red));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)>=15)
                {
                    e106w1.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)<15)
                {
                    e106w1.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }

                if(sweek3==null)
                {
                    e106w3.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek3!=null)
                {
                    e106w3.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }
                //if(sweek3!=null)
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
    }


    public void checke109()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sweek1=dataSnapshot.child("audit").child(syear).child(smonth).child("Week1").child("E109").child("date").getValue(String.class);
                String sweek3=dataSnapshot.child("audit").child(syear).child(smonth).child("Week3").child("E109").child("date").getValue(String.class);
                if(sweek1==null && sdate<15)
                {
                    e109w1.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek1==null && sdate>=15)
                {
                    e109w1.setBackgroundColor(getResources().getColor(R.color.holo_red));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)>=15)
                {
                    e109w1.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)<15)
                {
                    e109w1.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }

                if(sweek3==null)
                {
                    e109w3.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek3!=null)
                {
                    e109w3.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }
                //if(sweek3!=null)
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
    }

    public void checke110()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sweek1=dataSnapshot.child("audit").child(syear).child(smonth).child("Week1").child("E110").child("date").getValue(String.class);
                String sweek3=dataSnapshot.child("audit").child(syear).child(smonth).child("Week3").child("E110").child("date").getValue(String.class);
                if(sweek1==null && sdate<15)
                {
                    e110w1.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek1==null && sdate>=15)
                {
                    e110w1.setBackgroundColor(getResources().getColor(R.color.holo_red));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)>=15)
                {
                    e110w1.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)<15)
                {
                    e110w1.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }

                if(sweek3==null)
                {
                    e110w3.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek3!=null)
                {
                    e110w3.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }
                //if(sweek3!=null)
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
    }

    public void checke111()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sweek1=dataSnapshot.child("audit").child(syear).child(smonth).child("Week1").child("E111").child("date").getValue(String.class);
                String sweek3=dataSnapshot.child("audit").child(syear).child(smonth).child("Week3").child("E111").child("date").getValue(String.class);
                if(sweek1==null && sdate<15)
                {
                    e111w1.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek1==null && sdate>=15)
                {
                    e111w1.setBackgroundColor(getResources().getColor(R.color.holo_red));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)>=15)
                {
                    e111w1.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)<15)
                {
                    e111w1.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }

                if(sweek3==null)
                {
                    e111w3.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek3!=null)
                {
                    e111w3.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }
                //if(sweek3!=null)
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
    }

    public void checke117()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sweek1=dataSnapshot.child("audit").child(syear).child(smonth).child("Week1").child("E117").child("date").getValue(String.class);
                String sweek3=dataSnapshot.child("audit").child(syear).child(smonth).child("Week3").child("E117").child("date").getValue(String.class);
                if(sweek1==null && sdate<15)
                {
                    e117w1.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek1==null && sdate>=15)
                {
                    e117w1.setBackgroundColor(getResources().getColor(R.color.holo_red));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)>=15)
                {
                    e117w1.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)<15)
                {
                    e117w1.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }

                if(sweek3==null)
                {
                    e117w3.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek3!=null)
                {
                    e117w3.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }
                //if(sweek3!=null)
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
    }

    public void checke119()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sweek1=dataSnapshot.child("audit").child(syear).child(smonth).child("Week1").child("E119").child("date").getValue(String.class);
                String sweek3=dataSnapshot.child("audit").child(syear).child(smonth).child("Week3").child("E119").child("date").getValue(String.class);
                if(sweek1==null && sdate<15)
                {
                    e119w1.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek1==null && sdate>=15)
                {
                    e119w1.setBackgroundColor(getResources().getColor(R.color.holo_red));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)>=15)
                {
                    e119w1.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)<15)
                {
                    e119w1.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }

                if(sweek3==null)
                {
                    e119w3.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek3!=null)
                {
                    e119w3.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }
                //if(sweek3!=null)
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
    }

    public void checke128()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sweek1=dataSnapshot.child("audit").child(syear).child(smonth).child("Week1").child("E128").child("date").getValue(String.class);
                String sweek3=dataSnapshot.child("audit").child(syear).child(smonth).child("Week3").child("E128").child("date").getValue(String.class);
                if(sweek1==null && sdate<15)
                {
                    e128w1.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek1==null && sdate>=15)
                {
                    e128w1.setBackgroundColor(getResources().getColor(R.color.holo_red));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)>=15)
                {
                    e128w1.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)<15)
                {
                    e128w1.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }

                if(sweek3==null)
                {
                    e128w3.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek3!=null)
                {
                    e128w3.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }
                //if(sweek3!=null)
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
    }

    public void checke130()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sweek1=dataSnapshot.child("audit").child(syear).child(smonth).child("Week1").child("E130").child("date").getValue(String.class);
                String sweek3=dataSnapshot.child("audit").child(syear).child(smonth).child("Week3").child("E130").child("date").getValue(String.class);
                if(sweek1==null && sdate<15)
                {
                    e130w1.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek1==null && sdate>=15)
                {
                    e130w1.setBackgroundColor(getResources().getColor(R.color.holo_red));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)>=15)
                {
                    e130w1.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)<15)
                {
                    e130w1.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }

                if(sweek3==null)
                {
                    e130w3.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek3!=null)
                {
                    e130w3.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }
                //if(sweek3!=null)
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
    }

    public void checke135()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sweek1=dataSnapshot.child("audit").child(syear).child(smonth).child("Week1").child("E135").child("date").getValue(String.class);
                String sweek3=dataSnapshot.child("audit").child(syear).child(smonth).child("Week3").child("E135").child("date").getValue(String.class);
                if(sweek1==null && sdate<15)
                {
                    e135w1.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek1==null && sdate>=15)
                {
                    e135w1.setBackgroundColor(getResources().getColor(R.color.holo_red));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)>=15)
                {
                    e135w1.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)<15)
                {
                    e135w1.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }

                if(sweek3==null)
                {
                    e135w3.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek3!=null)
                {
                    e135w3.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }
                //if(sweek3!=null)
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
    }

    public void checke136()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sweek1=dataSnapshot.child("audit").child(syear).child(smonth).child("Week1").child("E136").child("date").getValue(String.class);
                String sweek3=dataSnapshot.child("audit").child(syear).child(smonth).child("Week3").child("E136").child("date").getValue(String.class);
                if(sweek1==null && sdate<15)
                {
                    e136w1.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek1==null && sdate>=15)
                {
                    e136w1.setBackgroundColor(getResources().getColor(R.color.holo_red));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)>=15)
                {
                    e136w1.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)<15)
                {
                    e136w1.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }

                if(sweek3==null)
                {
                    e136w3.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek3!=null)
                {
                    e136w3.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }
                //if(sweek3!=null)
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
    }

    public void checke137()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sweek1=dataSnapshot.child("audit").child(syear).child(smonth).child("Week1").child("E137").child("date").getValue(String.class);
                String sweek3=dataSnapshot.child("audit").child(syear).child(smonth).child("Week3").child("E137").child("date").getValue(String.class);
                if(sweek1==null && sdate<15)
                {
                    e137w1.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek1==null && sdate>=15)
                {
                    e137w1.setBackgroundColor(getResources().getColor(R.color.holo_red));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)>=15)
                {
                    e137w1.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)<15)
                {
                    e137w1.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }

                if(sweek3==null)
                {
                    e137w3.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek3!=null)
                {
                    e137w3.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }
                //if(sweek3!=null)
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
    }

    public void checke138()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sweek1=dataSnapshot.child("audit").child(syear).child(smonth).child("Week1").child("E138").child("date").getValue(String.class);
                String sweek3=dataSnapshot.child("audit").child(syear).child(smonth).child("Week3").child("E138").child("date").getValue(String.class);
                if(sweek1==null && sdate<15)
                {
                    e138w1.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek1==null && sdate>=15)
                {
                    e138w1.setBackgroundColor(getResources().getColor(R.color.holo_red));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)>=15)
                {
                    e138w1.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)<15)
                {
                    e138w1.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }

                if(sweek3==null)
                {
                    e138w3.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek3!=null)
                {
                    e138w3.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }
                //if(sweek3!=null)
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
    }
}
