package com.example.kaizan5s;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class chart_activity extends AppCompatActivity {
    AnyChartView anyChartView;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    String[] discipline = {"SORT","SET IN ORDER","SHINE","STANDARDIZE","SUSTAIN"};
    //double[] avg_score ={3.0,3.0,3.0,2.5,3.0};
    List<Double> avg_score=new ArrayList<>();

    String year,month,week,gemba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);

        pref=getSharedPreferences("MySharedPref",MODE_PRIVATE);

        year=pref.getString("year","");
        month=pref.getString("month","");
        week=pref.getString("week","");
        gemba=pref.getString("zonenum","");

//        avg_score.add(3.0);
//        avg_score.add(2.0);
//        avg_score.add(3.0);
//        avg_score.add(3.0);
//        avg_score.add(2.0);

        getdata();



        anyChartView = findViewById(R.id.any_chart_view);


    }

    public void getdata()
    {

        myRef=myRef.child("audit").child(year).child(month).child(week).child(gemba).child("ans");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot d1:dataSnapshot.getChildren())
                {
                    double av=0;
                    for(DataSnapshot d2:d1.getChildren())
                    {
                        Log.e("got",d2.child("score").getValue(String.class));
                        double sc=Double.parseDouble(d2.child("score").getValue(String.class));
                        av=av+sc;
                    }
                    av=av/(double)d1.getChildrenCount();
                    Log.e("aver",av+" ");
                   avg_score.add(av);
                }
                setupPieChart();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setupPieChart(){
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>( );

        for(int i=0;i< discipline.length; i++){
            dataEntries.add(new ValueDataEntry(discipline[i], avg_score.get(i)));
        }
         pie.data(dataEntries);
        anyChartView.setChart(pie);
    }
}
