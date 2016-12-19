package org.esiea.delochedenoyelle_dubret.dd1;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetBiersServices extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_get_all_biers = "org.esiea.delochedenoyelle_dubret.dd1.action.FOO";
    private static final String ACTION_get_all_country = "org.esiea.delochedenoyelle_dubret.dd1.action.FOO";
    private static final String ACTION_get_all_categories = "org.esiea.delochedenoyelle_dubret.dd1.action.FOO";

    // TODO: Rename parameters


    public GetBiersServices() {
        super("GetBiersServices");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBiers(Context context) {
        Intent intent = new Intent(context, GetBiersServices.class);
        intent.setAction(ACTION_get_all_biers);
        context.startService(intent);

    }

    public static void startActionCountry(Context context) {
        Intent intent2 = new Intent(context, GetBiersServices.class);
        intent2.setAction(ACTION_get_all_country);
        context.startService(intent2);

    }

    public static void startActionCategories(Context context) {
        Intent intent3 = new Intent(context, GetBiersServices.class);
        intent3.setAction(ACTION_get_all_categories);
        context.startService(intent3);

    }

    // TODO: Customize helper method

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {

            final String action = intent.getAction();
            if (ACTION_get_all_biers.equals(action)) {
            handleActionBiers();
            }
            if(ACTION_get_all_categories.equals(action)){
                handleActionCategories();
            }
            if(ACTION_get_all_country.equals(action)){
                handleActionCountry();
            }
        }
    }




    private void handleActionBiers() {
        Log.i("JFL" , "bonjour" );
        Log.d(TAG,"Thread service name:" +Thread.currentThread().getName());
        URL url = null;

        try{
            url = new URL("http://binouze.fabrigli.fr/bieres.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                copyInputStreamToFile(conn.getInputStream(), new File(getCacheDir(),"bieres.json"));
                Log.d(TAG,"Bieres json downloaded !");
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MainActivity.BIERS_UPDATE));
    }

    private void handleActionCountry() {
        Log.i("JFL" , "bonjour" );
        Log.d(TAG,"Thread service name:" +Thread.currentThread().getName());
        URL url = null;

        try{
            url = new URL("http://binouze.fabrigli.fr/country.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                copyInputStreamToFile(conn.getInputStream(), new File(getCacheDir(),"country.json"));
                Log.d(TAG,"Country json downloaded !");
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MainActivity.BIERS_UPDATE));
    }

    private void handleActionCategories() {
        Log.i("JFL" , "bonjour" );
        Log.d(TAG,"Thread service name:" +Thread.currentThread().getName());
        URL url = null;

        try{
            url = new URL("http://binouze.fabrigli.fr/categories.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                copyInputStreamToFile(conn.getInputStream(), new File(getCacheDir(),"categories.json"));
                Log.d(TAG,"Bieres json downloaded !");
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MainActivity.BIERS_UPDATE));
    }
    private void copyInputStreamToFile(InputStream in, File file){
        try{
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }



}
