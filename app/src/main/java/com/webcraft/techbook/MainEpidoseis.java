package com.webcraft.techbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
import org.eazegraph.lib.charts.BarChart;


import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import se.simbio.encryption.Encryption;

public class MainEpidoseis extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ProgressBar spinner;
    public String token;
    public String giamathima,date_epidosis,eidos;
    public float epidosi;
    public float batmoi1 = 0;
    public float batmoi2 = 0;
    public float batmoi3 = 0;
    public Text text1,text2,text3;
    public TextView mycontent,mycontent2,mycontent3;
    public float sinolo1 = 0;
    public float sinolo2 = 0;
    public float sinolo3 = 0;
    String server_response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epidosis);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        overridePendingTransition(R.transition.hold, R.transition.push_out_to_right);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);

        if (InternetStatus.getInstance(this).isOnline()) {


        } else {

            Toast.makeText(this, "Παρακαλώ συνδεθείτε στο INTERNET",
                    Toast.LENGTH_LONG).show();

        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //spinner.setVisibility(View.VISIBLE);
                final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Encryption encryption = Encryption.getDefault("Key", "Salt", new byte[16]);
                token = encryption.decryptOrNull(preferences.getString("token", ""));
                new GetMethodDemo().execute("https://techbook.pythonanywhere.com/epidosis/");


            }
        }, 300);


    }










    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(activity2Intent);
            // overridePendingTransition(R.transition.hold,R.transition.push_out_to_right );

           // spinner.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_apusies) {

            spinner.setVisibility(View.VISIBLE);
            Intent activity2Intent = new Intent(getApplicationContext(), MainApusies.class);
            overridePendingTransition(R.transition.hold,R.transition.push_out_to_right );
            startActivity(activity2Intent);

        } else if (id == R.id.nav_links) {
           // spinner.setVisibility(View.VISIBLE);
            Intent activity2Intent = new Intent(getApplicationContext(), MainLinks.class);
            overridePendingTransition(R.transition.hold,R.transition.push_out_to_right );
            startActivity(activity2Intent);

        } else if (id == R.id.nav_epidoseis) {

//            spinner.setVisibility(View.VISIBLE);
            Intent activity2Intent = new Intent(getApplicationContext(), MainEpidoseis.class);
            overridePendingTransition(R.transition.hold,R.transition.push_out_to_right );
            startActivity(activity2Intent);

        } else if (id == R.id.nav_documents) {
            spinner.setVisibility(View.VISIBLE);
            Intent activity2Intent = new Intent(getApplicationContext(), MainDocs.class);
            overridePendingTransition(R.transition.hold,R.transition.push_out_to_right );
            startActivity(activity2Intent);

        } else if (id == R.id.nav_logout) {
            PreferenceManager.getDefaultSharedPreferences(getBaseContext()).
                    edit().clear().apply();
            Intent activity23Intent = new Intent(getApplicationContext(), loginscreen.class);
            startActivity(activity23Intent);
            overridePendingTransition(R.transition.hold,R.transition.push_out_to_right );

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public class GetMethodDemo extends AsyncTask<String , Void ,String> {
        String server_response;

        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(strings[0]);
                String userPassword = token;
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Authorization", "Token " + userPassword);
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    server_response = readStream(urlConnection.getInputStream());
                    Log.v("CatalogClient", server_response);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }






        @Override
        protected void onPostExecute(String s) {



            super.onPostExecute(s);

            try {



                JSONArray aryJSONStrings = new JSONArray(server_response);




                BarChart mBarChart = (BarChart) findViewById(R.id.barchart);
                BarChart mBarChart2 = (BarChart) findViewById(R.id.barchart2);
                BarChart mBarChart3 = (BarChart) findViewById(R.id.barchart3);

                JSONArray aryJSONStrings2 = new JSONArray(server_response);

                for (int i = 0; i < aryJSONStrings2.length(); i++) {

                    giamathima = aryJSONStrings.getJSONObject(i).getString("giamathima");
                    date_epidosis = aryJSONStrings2.getJSONObject(i).getString("date_epidosis").toString();
                    epidosi = aryJSONStrings2.getJSONObject(i).getInt("epidosi");
                    // eidos = aryJSONStrings.getJSONObject(i).getString("eidos");



if(giamathima.equals("sae")){
    batmoi1 = batmoi1+1;
    sinolo1 = (sinolo1 + epidosi)/batmoi1;
    mycontent = (TextView) findViewById(R.id.text1);
    mycontent.setText("MO ΣΥΣΤΗΜΑΤΑ ΑΥΤΟΚΙΝΗΤΟΥ " + sinolo1);
    //Integer result = Integer.valueOf(epidosi);

    mBarChart.addBar(new BarModel(date_epidosis.toString() ,epidosi, 0xFF123456));
}else if (giamathima.equals("psiksi"))
                {
                    batmoi2 = batmoi2+1;
                    sinolo2 = (sinolo2 + epidosi)/batmoi2;
                    mycontent2 = (TextView) findViewById(R.id.text2);
                    mycontent2.setText("MO ΨΥΞΗ " + sinolo2);

                        mBarChart2.addBar(new BarModel(date_epidosis.toString() ,epidosi, 0xFF1FF4AC));
}
else if(giamathima.equals("ergaliomihanes"))
{
    batmoi3 = batmoi3+1;
    sinolo3 = (sinolo3 + epidosi)/batmoi3;
    mycontent3 = (TextView) findViewById(R.id.text3);
    mycontent3.setText("MO ΨΥΞΗ " + sinolo3);
                        mBarChart3.addBar(new BarModel(date_epidosis.toString() ,epidosi, 0xFF56B7F1));
}

else{}
                }





                mBarChart.startAnimation();
                mBarChart2.startAnimation();
                mBarChart3.startAnimation();

                spinner.setVisibility(View.GONE);


            } catch (JSONException e) {
            }


            //mycontent2 = (TextView) findViewById(R.id.onomaid);
            //mycontent2.setText(onomaid);



            // spinner.setVisibility(View.GONE);



        }
    }

// Converting InputStream to String

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    public static JSONArray getJsonArray(String jsonString){
        JSONArray jArr = null;
        // try parse the string to a JSON Array
        try {
            jArr = new JSONArray(jsonString);
        } catch (JSONException e) {
            jArr = null;
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jArr;
    }


}
