package com.codebrain.luckytimes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

public class Revision extends AppCompatActivity {

    TextView txt;
    private String android_id;

    public String direccionURL = "http://master.luckytimes.net/"+"principal-puesto.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision);

        android_id =  Settings.Secure.getString(getApplication().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        txt = (TextView) findViewById(R.id.textView);
        //txt.setText(android_id);

        String[] sunmis = {
                "948248f2651dc847", //
                "c853024a84a4593", //
                "cda16d5d89d00bee", //
                "d76c1a571218f3c2", //

                "78a8dba272f6f810", //166-TIEMPOS MICHELLE (1 o 2)
                "fbdf131dfe36a642", //166-TIEMPOS MICHELLE (1 o 2)
                "7a5b93cb57a63ff8", //319-TIEMPOS GABY
                "dbe482eef6805962", //315-TIEMPOS GALAN
                "491d9860d35f4db4", //318-TIEMPOS LUCY
                "f40944c0a438c02f", //161-TIEMPOS BUSTOS
                "8828ec3c6ea6bbd3", //165-TIEMPOS TRIGUERO
                "22bc064a056e0ac4", // TIEMPOS AAA
                "8a335cd5c0086e16", //313-TIEMPOS GENESIS
                "501c33490cc6990d", //186-TIEMPOS TERE
                "d88f01a13cd94afe", //188-TIEMPOS TERE-2
                "6e25be61e0e51f7e", //
                "6cb5a7f13d3ff2b9", //
                "2b0b16076238d859" //
        };

        boolean dejarPasar = false;

        for(int i = 0; i< sunmis.length; i++){
            if(android_id.contains(sunmis[i])){
                dejarPasar = true;
            }
        }

        if(dejarPasar == true){
            Thread background = new Thread() {
                public void run() {
                    try {
                        // Thread will sleep for 5 seconds
                        sleep(1500);

                        // After 5 seconds redirect to another intent
                        Intent i = new Intent(getApplicationContext(), Principal.class);
                        finish();  //Kill the activity from which you will go to next activity
                        startActivity(i);

                        //Remove activity
                        finish();
                    } catch (Exception e) {
                    }
                }
            };

            background.start();
        }else{
            txt.setText("Este dispositivo no tiene permiso para ejecutar la aplicación.");
        }

    }
}