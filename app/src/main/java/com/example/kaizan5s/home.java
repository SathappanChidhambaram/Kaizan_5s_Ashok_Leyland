package com.example.kaizan5s;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class home extends AppCompatActivity {
    CardView auditee,auditor,status,dashb;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pref=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        editor=pref.edit();

        auditee=findViewById(R.id.cvauditee);
        auditor=findViewById(R.id.cvauditor);
        status=findViewById(R.id.cvstatus);
        dashb=findViewById(R.id.cvdashboard);

        checkAndRequestPermissions();


    }

    public void auditorgo(View v)
    {
            editor.putString("clicked","auditor");
            editor.apply();
            Intent intent2 = new Intent(home.this, select.class);
            startActivity(intent2);

    }

    public void auditeego(View v)
    {
        editor.putString("clicked","auditee");
        editor.apply();
        Intent intent1=new Intent(home.this,select.class);
        startActivity(intent1);
    }

    public void statusgo(View v)
    {
        editor.putString("clicked","status");
        editor.apply();
        Intent intent3=new Intent(home.this,overall_status.class);
        startActivity(intent3);
    }
    public void dashboardgo(View v)
    {
        editor.putString("clicked","dashboard");
        editor.apply();
        Intent intent4=new Intent(home.this,coreteam.class);
        startActivity(intent4);
    }

    private  void checkAndRequestPermissions() {
        int readstorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int writestorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (readstorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (writestorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            //checkAndRequestPermissions();
           // return false;
        }
       // return true;
    }
}
