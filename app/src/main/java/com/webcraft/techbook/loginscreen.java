package com.webcraft.techbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import se.simbio.encryption.Encryption;

public class loginscreen extends AppCompatActivity {

    private String URLline = "https://techbook.pythonanywhere.com/api-token-auth/";
    public String token;
    private EditText etUname, etPass;
    public Button btn;
    public static String firstName, hobby ,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_login);

        etUname = findViewById(R.id.etusername);
        etPass = findViewById(R.id.etpassword);

        if (InternetStatus.getInstance(this).isOnline()) {



        } else {

            Toast.makeText(this, "Παρακαλώ συνδεθείτε στο INTERNET",
                    Toast.LENGTH_LONG).show();

        }


        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Encryption encryption = Encryption.getDefault("Key", "Salt", new byte[16]);
        token  = encryption.decryptOrNull(preferences.getString("token",""));
        if (token.length()!=0) {

            Intent intent = new Intent(loginscreen.this,MainActivity.class);

            startActivity(intent);

        } else {}


        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    private void loginUser(){

        final String username = etUname.getText().toString().trim();
        final String password = etPass.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(),"Γίνεται είσοδος.",Toast.LENGTH_SHORT).show();
                        parseData(response);
                         // Toast.makeText(MainActivity.this,status,Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Λάθος στοιχεία!",Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",username);
                params.put("password",password);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void parseData(String response) {

        try {

            String MY_PREFS_NAME = "datoken";

            JSONObject teste = new JSONObject(response);
            String status = teste.getString("token");
            System.out.println(status);






            Encryption encryption = Encryption.getDefault("Key", "Salt", new byte[16]);
            SharedPreferences preferences =    PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("token", encryption.encryptOrNull(status));
            editor.apply();



            Intent intent = new Intent(loginscreen.this,MainActivity.class);
            // intent.putExtra("STRING_I_NEED", status);
            startActivity(intent);

        } catch (JSONException e) {

        }

    }



}
