package org.esiea.delochedenoyelle_dubret.dd1;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.*;
import java.lang.*;


public class MainActivity extends AppCompatActivity {
    DatePickerDialog dpd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("JFL", "debut de oncreate");


        final TextView tv_hw = (TextView) findViewById(R.id.tv_hello_world);
        Button btn_hw = (Button) findViewById(R.id.btn_hello_world);

        RecyclerView rv_b = (RecyclerView) findViewById(R.id.rv_biere);
        getString(R.string.hello_world);
        String now = DateUtils.formatDateTime(getApplicationContext(), (new Date()).getTime(), DateFormat.FULL);
        tv_hw.setText(now);



        btn_hw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), getString(R.string.modifier_date), Toast.LENGTH_LONG).show();
                dpd.show();
                notification_test();

            }
        });



        DatePickerDialog.OnDateSetListener odsl = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {// Cette fonction modifie la date, affichée ci dessus
                int i11 = i1 + 1;
                tv_hw.setText(i + "/" + i11 + "/" + i2);
            }
        };
        dpd = new DatePickerDialog(this, odsl, 2016, 11, 14);


        IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(),intentFilter);

        rv_b.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        BiersAdapter ba = new BiersAdapter(getBiersFromFile());
        rv_b.setAdapter(ba);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Log.i("JFL", "Fin de onCreate");
    }

    public void notification_test() {
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_basket)
                .setContentTitle("WARNING !")
                .setContentText("Notification du bouton Date");

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(100, mBuilder.build());


    }

    public void notification_biers() {
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_basket)
                .setContentTitle("Téléchargement terminé")
                .setContentText("La liste de bières a été téléchargée");

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(100, mBuilder.build());

    }

    public void notification_actionbar() {
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_basket)
                .setContentTitle("WARNING !")
                .setContentText("Notification de l'ActionBar");

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(100, mBuilder.build());

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.i("JFL" , "inflate menu")
;        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int id2 = item.getItemId();
        int id3 = item.getItemId();
        int id4 = item.getItemId();
        if(id == R.id.Toast_me){
            Toast.makeText(getApplicationContext(),"Toast depuis l'action bar !", Toast.LENGTH_LONG).show();
        }
        if(id2 == R.id.Notify_me){
            notification_actionbar();
        }

        if(id3 == R.id.New_Intent){
            Intent i = new Intent(this,SecondeActivity.class);
            startActivity(i);
        }

        if(id4 == R.id.Biers_list){
          GetBiersServices.startActionBiers(this);
            Log.i("jfl", "ici");

        }
        return super.onOptionsItemSelected(item);
    }



public static final String BIERS_UPDATE = "com.octip.cours.inf4042_11.BIERS_UPDATE";
    public class BierUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            notification_biers();
        }
    }

    public JSONArray getBiersFromFile(){
        try{
            InputStream is = new FileInputStream(getCacheDir() + "/" + "bieres.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer,"UTF-8"));
        }catch (IOException e){
            e.printStackTrace();
            return new JSONArray();
        }catch(JSONException e){
            e.printStackTrace();
            return new JSONArray();
        }
    }


    class BiersAdapter extends RecyclerView.Adapter<BiersAdapter.BierHolder>{
        private JSONArray biers;

        public BiersAdapter(JSONArray bieres){
            this.biers = bieres;

        }

        @Override
        public BierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BierHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_bier_element,parent,false));
        }

        @Override
        public void onBindViewHolder(BierHolder holder,int position) {
            try {
                Log.i("JFL", "Get a bier");
                JSONObject bieres = biers.getJSONObject(position);
                holder.name.setText(bieres.getString("name"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return biers.length();
        }

        public class BierHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView name;

            public BierHolder(View view){
                super(view);
;
                name = (TextView) itemView.findViewById(R.id.rv_bier_element_name);
                name.setOnClickListener(this);


            }

            @Override
            public void onClick(View view) {

                try{
                    Intent g = new Intent(getApplicationContext(),BiersDataActivity.class);
                    int position = getAdapterPosition();
                    JSONObject bieres = biers.getJSONObject(position);
                    String description = bieres.getString("description");
                    String name = bieres.getString("name");
                    String id = bieres.getString("id");
                    g.putExtra("name", name);
                    g.putExtra("description", description);
                    g.putExtra("name", id);
                    startActivity(g);} catch (JSONException e) {
                e.printStackTrace();
            }

            }
        }



    }
}
