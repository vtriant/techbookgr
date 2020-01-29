package com.webcraft.techbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class nointernet extends AppCompatActivity {


    Button btn;

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
            setContentView(R.layout.nointernet);
        btn = findViewById(R.id.btn_retry);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (InternetStatus.getInstance(nointernet.this).isOnline()) {


                    Intent activity32Intent = new Intent(getApplicationContext(), MainActivity.class);
                    overridePendingTransition(R.transition.hold, R.transition.push_out_to_right);
                    startActivity(activity32Intent);


                } else {

                    Toast.makeText(nointernet.this, "Ελέξτε την σύνδεση σας στο INTERNET", Toast.LENGTH_LONG).show();

                }


            }
        });

    }

    }



