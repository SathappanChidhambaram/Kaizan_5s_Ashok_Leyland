package com.example.kaizan5s;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bauditee,bauditor,bstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bauditee=findViewById(R.id.bauditee);
        bauditor=findViewById(R.id.bauditor);
        bstatus=findViewById(R.id.bstatus);

        bauditee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Clicked:","auditee");
                Intent intent=new Intent(MainActivity.this,select.class);
                startActivity(intent);
            }
        });


    }

}
