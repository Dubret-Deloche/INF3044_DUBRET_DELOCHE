package org.esiea.delochedenoyelle_dubret.dd1;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BiersDataActivity extends AppCompatActivity {
     TextView tv_ns;
    TextView tv_ds;
     TextView tv_is;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biers_data);

        tv_ns = (TextView) findViewById(R.id.namesolo);
        tv_ds = (TextView) findViewById(R.id.descriptionsolo);
        tv_is = (TextView) findViewById(R.id.idsolo);
        String nom = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        String id = getIntent().getStringExtra("id");

        tv_ns.setText(nom);
        tv_ds.setText(description);
        tv_is.setText(id);






    }


}
