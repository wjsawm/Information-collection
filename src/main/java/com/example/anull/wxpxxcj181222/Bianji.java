package com.example.anull.wxpxxcj181222;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Bianji extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bianji);
        Button button = (Button) findViewById(R.id.bianji1);
        Button button2 = (Button) findViewById(R.id.bianji2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Bianji.this, Banzubianji.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Bianji.this, Renyuanbianji.class);
                startActivity(intent);
            }
        });
    }
}
