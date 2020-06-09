package com.example.kaizan5s;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class part_stat extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    TextView gnum;
    TextView tvweek1,tvweek3;

    String gemba,clicked;
    int sdate;

    String year,month,week;
    String auditor,auditee;

    String sweek1,sweek3;

    Intent intent;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    int colorCode;
    Boolean func=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_stat);

        tvweek1=findViewById(R.id.tvweek1);
        tvweek3=findViewById(R.id.tvweek3);

        pref= getSharedPreferences("MySharedPref", MODE_PRIVATE);
        editor = pref.edit();


        year=pref.getString("ayear","");
        month=pref.getString("amonth","");
        sdate= Calendar.getInstance().get(Calendar.DATE);

        auditor=pref.getString("auditor","");
        auditee=pref.getString("auditee","");

        gnum=findViewById(R.id.gnum);

        gemba=pref.getString("zonenum","");
        Log.e("numb",gemba);
        gnum.setText("Gemba No: "+gemba);

        clicked=pref.getString("clicked","");

        check();

        tvweek1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(part_stat.this,"Hai",Toast.LENGTH_SHORT).show();
//                if (tvweek1.getBackground() instanceof ColorDrawable) {
//                    ColorDrawable cd = (ColorDrawable) tvweek1.getBackground();
//                    colorCode = cd.getColor();
//                    Log.e("color",colorCode+" ");
//                }

                if(func)
                {
                    checkweek1();
                }

//                ColorDrawable viewColor = (ColorDrawable) tvweek1.getBackground();
//                colorCode= viewColor.getColor();
//                Log.e("color",colorCode+" ");
//                if(clicked.equals("auditor"))
//                {
//                    if(colorCode==R.color.white || colorCode==R.color.holo_red) {
//                        myRef=myRef.child("audit").child(year).child(month).child("Week1").child(gemba);
//                        myRef.child("auditor").setValue(auditor);
//                        myRef.child("auditee").setValue(auditee);
//                        editor.putString("date",Integer.toString(sdate));
//                        editor.apply();
//                        intent = new Intent(part_stat.this, question.class);
//                        startActivity(intent);
//                    }
//                    else
//                    {
//                        Toast.makeText(part_stat.this,"Audit already done",Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else {
//                    if(colorCode==R.color.holo_green || colorCode==R.color.yellow) {
//                        editor.putInt("stopic", 1);
//                        editor.putInt("sqn", 1);
//                        editor.apply();
//                        intent = new Intent(part_stat.this, review_ques.class);
//                        startActivity(intent);
//                    }
//                    else
//                    {
//                        Toast.makeText(part_stat.this,"Audit not yet done",Toast.LENGTH_SHORT).show();
//                    }
//                }

            }
        });

        tvweek3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (tvweek3.getBackground() instanceof ColorDrawable) {
//                    ColorDrawable cd = (ColorDrawable) tvweek3.getBackground();
//                    colorCode = cd.getColor();
//                }

                if(func)
                {
                    checkweek3();
                }


//                if(clicked.equals("auditor"))
//                {
//                    if(colorCode==R.color.white && sdate>=15) {
//                        myRef = myRef.child("audit").child(year).child(month).child("Week3").child(gemba);
//                        myRef.child("auditor").setValue(auditor);
//                        myRef.child("auditee").setValue(auditee);
//                        editor.putString("date",Integer.toString(sdate));
//                        editor.apply();
//                        intent = new Intent(part_stat.this, question.class);
//                        startActivity(intent);
//                    }
//                    else if(colorCode==R.color.holo_green)
//                    {
//                        Toast.makeText(part_stat.this,"Audit already done",Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                        Toast.makeText(part_stat.this,"Week3 audit can't be done now",Toast.LENGTH_SHORT).show();
//                }
//                else {
//                   if(colorCode==R.color.holo_green) {
//                       editor.putInt("stopic", 1);
//                       editor.putInt("sqn", 1);
//                       editor.apply();
//                       intent = new Intent(part_stat.this, review_ques.class);
//                       startActivity(intent);
//                   }
//                   else
//                   {
//                       Toast.makeText(part_stat.this,"Audit not yet done",Toast.LENGTH_SHORT).show();
//                   }
//                }

            }
        });




    }

    public void check()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sweek1=dataSnapshot.child("audit").child(year).child(month).child("Week1").child(gemba).child("date").getValue(String.class);
                sweek3=dataSnapshot.child("audit").child(year).child(month).child("Week3").child(gemba).child("date").getValue(String.class);
                if(sweek1==null && sdate<15)
                {
                    tvweek1.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek1==null && sdate>=15)
                {
                    tvweek1.setBackgroundColor(getResources().getColor(R.color.holo_red));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)>=15)
                {
                    tvweek1.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                if(sweek1!=null && Integer.parseInt(sweek1)<15)
                {
                    tvweek1.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }

                if(sweek3==null)
                {
                    tvweek3.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(sweek3!=null)
                {
                    tvweek3.setBackgroundColor(getResources().getColor(R.color.holo_green));
                }
                //if(sweek3!=null)
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
        func=true;

    }

    public void checkweek1() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sweek1 = dataSnapshot.child("audit").child(year).child(month).child("Week1").child(gemba).child("date").getValue(String.class);
                if(clicked.equals("auditor"))
                {
                    if(sweek1 == null)
                    {
                        editor.putString("week","Week1");
                        editor.apply();
                        myRef = database.getReference();
                        myRef=myRef.child("audit").child(year).child(month).child("Week1").child(gemba);
                        myRef.child("auditor").setValue(auditor);
                        myRef.child("auditee").setValue(auditee);
                        editor.putString("date",Integer.toString(sdate));
                        editor.apply();
                        intent = new Intent(part_stat.this, question.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(part_stat.this,"Audit already done",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    if(sweek1!=null)
                    {
                        editor.putString("week","Week1");
                        editor.apply();
                        editor.putInt("stopic", 1);
                        editor.putInt("sqn", 1);
                        editor.apply();
                        intent = new Intent(part_stat.this, review_ques.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(part_stat.this,"Audit not yet done",Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
    }

    public void checkweek3()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sweek3=dataSnapshot.child("audit").child(year).child(month).child("Week3").child(gemba).child("date").getValue(String.class);

                if(clicked.equals("auditor"))
                {
                    if(sweek3==null && sdate>=15) {
                        editor.putString("week","Week3");
                        editor.apply();
                        myRef = database.getReference();
                        myRef = myRef.child("audit").child(year).child(month).child("Week3").child(gemba);
                        myRef.child("auditor").setValue(auditor);
                        myRef.child("auditee").setValue(auditee);
                        editor.putString("date",Integer.toString(sdate));
                        editor.apply();
                        intent = new Intent(part_stat.this, question.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(sweek3!=null)
                    {
                        Toast.makeText(part_stat.this,"Audit already done",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(part_stat.this,"Week3 audit can't be done now",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(sweek3!=null) {
                        editor.putString("week","Week3");
                        editor.apply();
                        editor.putInt("stopic", 1);
                        editor.putInt("sqn", 1);
                        editor.apply();
                        intent = new Intent(part_stat.this, review_ques.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(part_stat.this,"Audit not yet done",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("problem", "Failed to read value.", error.toException());
            }
        });
    }

//    public void audit1(View V)
//    {
//        editor.putString("week","Week1");
//
//        editor.apply();
//        myRef=myRef.child("audit").child(year).child(month).child("Week1").child(gemba);
//        myRef.child("auditor").setValue(auditor);
//        myRef.child("auditee").setValue(auditee);
//        if(clicked.equals("auditor"))
//            intent=new Intent(part_stat.this,question.class);
//        else {
//
//            editor.putInt("stopic",1);
//            editor.putInt("sqn",1);
//            editor.apply();
//            intent = new Intent(part_stat.this, review_ques.class);
//        }
//        startActivity(intent);
//    }
//
//    public void audit3(View V)
//    {
//        editor.putString("week","Week3");
//        editor.apply();
//        myRef=myRef.child("audit").child(year).child(month).child("Week3").child(gemba);
//        myRef.child("auditor").setValue(auditor);
//        myRef.child("auditee").setValue(auditee);
//        if(clicked.equals("auditor"))
//            intent=new Intent(part_stat.this,question.class);
//        else {
//
//            editor.putInt("stopic",1);
//            editor.putInt("sqn",1);
//            editor.apply();
//            intent = new Intent(part_stat.this, review_ques.class);
//        }
//        startActivity(intent);
//    }

}
