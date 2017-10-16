package com.mstztrk.j10_5xmlwebparsing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.mstztrk.j10_5xmlwebparsing.async.DovizServiceTask;
import com.mstztrk.j10_5xmlwebparsing.model.Doviz;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final String XML_URL = "http://www.tcmb.gov.tr/kurlar/today.xml";
    ListView listView;
    ArrayList<Doviz> dovizler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        dovizleriDoldur();
    }

    private void dovizleriDoldur() {
        DovizServiceTask serviceTask = new DovizServiceTask(this, listView);
        serviceTask.execute(XML_URL);
        try {
            dovizler = serviceTask.get();
        } catch (Exception e) {
            Log.e("Main", e.getMessage());
        }
    }
}
