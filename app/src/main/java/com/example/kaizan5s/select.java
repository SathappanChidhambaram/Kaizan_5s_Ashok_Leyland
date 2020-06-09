package com.example.kaizan5s;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class select extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    Spinner spinzone,spinyear,spinmonth;

    String[] arrtee,arrtor,arrzone;
    ArrayAdapter<String> adap,adapzone,adapmonth;
    ArrayAdapter<Integer> adapyear;
    TextView etee,etor;
    Button next;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    int fyear,fmonth;
    String syear,smonth;

    String sweek;

//    final SimpleDateFormat year = new SimpleDateFormat("yyyy", Locale.getDefault());
//    final SimpleDateFormat month= new SimpleDateFormat("MM", Locale.getDefault());
//    final SimpleDateFormat date = new SimpleDateFormat("dd", Locale.getDefault());

    String gemmno[]={"E102","E105","E106","E109","E110","E111","E117","E119","E128","E130","E135","E136","E137","E138","E101"};
    String arrmonth[]={"January (01)","February (02)","March (03)","April (04)","May (05)","June (06)","July (07)","August (08)","September (09)",
                         "October (10)", "November (11)","December (12)"};
    String array[]=new String[15];
    HashMap<String,String> htee=new HashMap<>();
    HashMap<Integer,String> htor=new HashMap<>();
    String zonesel,torname,teename;

    int gyear=Calendar.getInstance().get(Calendar.YEAR);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();


        spinyear=findViewById(R.id.spinyear);
        spinmonth=findViewById(R.id.spinmonth);

//        htee.put("E102","Prakasam S");
//        htee.put("E105","Venkata Krishnan M");
//        htee.put("E106","Sanajay");
//        htee.put("E109","Karthick.P");
//        htee.put("E110","Jayasankar B");
//        htee.put("E111","Yuvakiran Sivala");
//        htee.put("E117","M Alex Pandi");
//        htee.put("E119","Balaji K");
//        htee.put("E128","Naveen Kumar");
//        htee.put("E130","Karthick.P");
//        htee.put("E135","Perumal");
//        htee.put("E136","John");
//        htee.put("E137","Manohar");
//        htee.put("E138","Thiyagarajan");
//        htee.put("E101","Karthick.P");

        myRef.child("teeset").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d1:dataSnapshot.getChildren())
                {
                    String gem=d1.getKey();
                    String tee=d1.getValue(String.class);
                    htee.put(gem,tee);
                    Log.e("htee",gem+tee);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef.child("torset").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d1:dataSnapshot.getChildren())
                {
                    int num=Integer.parseInt(d1.getKey());
                    String tor=d1.getValue(String.class);
                    htor.put(num,tor);
                    Log.e("htor",num+tor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        htor.put(1,"Muralidhar V.S");
//        htor.put(2,"Akash Raj ");
//        htor.put(3,"Balaji K");
//        htor.put(4,"Kalpana S");
//        htor.put(5,"Palani K");
//        htor.put(6,"Suresh Babu R.S");
//        htor.put(7,"Srinivasan S");
//        htor.put(8,"Dharani Babu B");
//        htor.put(9,"Subramanian K");
//        htor.put(10,"Ravishankar R ");
//        htor.put(11,"Sivakumar S.S");
//        htor.put(12,"Satheesh S");
//        htor.put(13,"Sakthivel V");
//        htor.put(14,"Naveen A");
//        htor.put(15,"Porchselvan E");






        next=findViewById(R.id.next);

        etor=findViewById(R.id.etor);
        etee=findViewById(R.id.etee);
        spinzone=findViewById(R.id.spinzone);
       // spintor=findViewById(R.id.spintor);
     //   spintee=findViewById(R.id.spintee);

       // spintee.getBackground().setColorFilter(getResources().getColor(R.color.spinarr), PorterDuff.Mode.SRC_ATOP);
        int nyear=gyear-1;
        Integer[] arryear={gyear,nyear};
        adapyear=new ArrayAdapter<>(select.this,android.R.layout.simple_spinner_dropdown_item,arryear);
        spinyear.setAdapter(adapyear);

        adapmonth=new ArrayAdapter<>(select.this,android.R.layout.simple_spinner_dropdown_item,arrmonth);
        spinmonth.setAdapter(adapmonth);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                String szone=dataSnapshot.child("Zone").child("name").getValue(String.class);
                //Log.e("name",szone+"yes");
                arrzone=szone.split(",");
                adapzone=new ArrayAdapter<String>(select.this,android.R.layout.simple_spinner_dropdown_item,arrzone);
                //adapzone.setDropDownViewResource(R.layout.spinner_textview);
                spinzone.setAdapter(adapzone);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Error is", "Failed to read value.", error.toException());
            }
        });

        spinzone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // etee.setText(arrtee[position]);

                findperson();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                findperson();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinmonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               // Log.e("month",fmonth+"m");
                if(arrzone!=null)
                findperson();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(arrzone!=null)
                findperson();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                editor.putInt("qn",1);
                editor.putInt("subqn",1);

                Log.e("onnext",zonesel);
                editor.putString("zonenum",zonesel);
               // editor.apply();

                Log.e("Select is","Success");




                String clicked=sharedPreferences.getString("clicked","");
                String send;
                if(clicked.equals("auditee"))
                    send=etee.getText().toString();
                else
                    send=etor.getText().toString();

                editor.putString("send",send);
                editor.putString("ayear",syear);
                editor.putString("amonth",smonth);
                editor.putString("auditor",etor.getText().toString());
                editor.putString("auditee",etee.getText().toString());
                editor.apply();
                Intent intent=new Intent(select.this,sign_in.class);
                startActivity(intent);
                finish();

            }
        });

    }

    public void findperson()
    {

        int dyear=2020;
        //int fmonth,fyear;
        zonesel=spinzone.getSelectedItem().toString();
        teename= htee.get(zonesel);
        Log.e("teename:",zonesel);
        etee.setText(teename);

        syear=spinyear.getSelectedItem().toString();
        fyear=Integer.parseInt(syear);


        smonth=spinmonth.getSelectedItem().toString();
        smonth=smonth.substring(smonth.length()-3,smonth.length()-1);
        fmonth=Integer.parseInt(smonth);

//         fyear=Calendar.getInstance().get(Calendar.YEAR);
//        fmonth=Calendar.getInstance().get(Calendar.MONTH);

//        Log.e("month",Integer.toString(cyear));
//        Log.e("year",year.toString());
//        fmonth=Integer.parseInt(month.toString());
//        fyear=Integer.parseInt(year.toString());

        fyear=fyear-dyear;
        if(fyear==0){
            fyear=0;
        }else{
            fyear=fyear*12;
        }
        //cout<<year;
        int result=(fmonth+fyear)%15-1;
        if(result!=0)
        {
            //cout<<result;
        }
        else
        {
            result=15;
            // cout<<15;
        }
        int j=0;
        int d=result;
        for(int i=0; i<15;i++) {
            array[(i+15-d)%15] = gemmno[j];
            j++;
        }
        //cout<<endl;
        for(int i=0; i<15;i++) {
           // System.out.println(array[i]);
            Log.e("zone",array[i]);
            if(array[i].trim().equals(zonesel.trim()))
            {
                Log.e("number",i+"yes");
//                if(i==0)
//                    i=;
             etor.setText(htor.get(i+1));
             break;
            }
//            else
//            {
//                Log.e("missed:",array[i]);
//            }
        }

    }


}
