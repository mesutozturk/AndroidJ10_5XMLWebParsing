package com.mstztrk.j10_5xmlwebparsing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mstztrk.j10_5xmlwebparsing.model.Doviz;

public class DovizActivity extends AppCompatActivity {
    Doviz gelenDoviz = null;
    TextView txtDoviz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doviz);
        txtDoviz = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        //Toast.makeText(this, intent.getStringExtra("doviz"), Toast.LENGTH_SHORT).show();
        gelenDoviz = (Doviz) intent.getSerializableExtra("doviz");
        //Toast.makeText(this, gelenDoviz.toString(), Toast.LENGTH_SHORT).show();
        txtDoviz.setText(gelenDoviz.toString());
    }
}
