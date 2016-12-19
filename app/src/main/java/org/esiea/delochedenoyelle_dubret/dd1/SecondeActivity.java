package org.esiea.delochedenoyelle_dubret.dd1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.lang.*;
public class SecondeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seconde);
        Toast.makeText(getApplicationContext(),"Toast depuis la SecondeActivity!", Toast.LENGTH_LONG).show();
        Button btn_retour = (Button) findViewById(R.id.button_retour);
        Button btn_actionview = (Button) findViewById(R.id.button_actionview);

        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Retour vers le premier écran", Toast.LENGTH_LONG).show();
                Intent i2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i2);


            }
        });



        btn_actionview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=21 avenue de Paris Bonneuil-sur-Marne 94380")));

            }
        });
    }
}
