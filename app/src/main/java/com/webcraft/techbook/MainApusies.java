package com.webcraft.techbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import se.simbio.encryption.Encryption;

public class MainApusies extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public TextView mycontent, mycontent2;
    public String token;
    public String date_apusias,no_apusion,giamathiti;
    public ListView listview;
    private ProgressBar spinner;
    public String theflag;
    public int sinolo = 0;
    final ArrayList<String> mylist = new ArrayList<String>();
    final ArrayList<String> mylist2 = new ArrayList<String>();


    public TextView mycontent3;
    List<String> combined = new ArrayList<String>();
    List<String> combined2 = new ArrayList<String>();

    // Array of strings...
    String[] mobileArray = {"Android", "IPhone", "WindowsMobile", "Blackberry",
            "WebOS", "Ubuntu", "Windows7", "Max OS X"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apusies);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);
        if (InternetStatus.getInstance(this).isOnline()) {

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    Encryption encryption = Encryption.getDefault("Key", "Salt", new byte[16]);
                    token = encryption.decryptOrNull(preferences.getString("token", ""));
                    new GetMethodDemo().execute("https://techbook.pythonanywhere.com/apusies/");




                }
            }, 300);


        } else {

            Intent activity32Intent = new Intent(getApplicationContext(), nointernet.class);
            overridePendingTransition(R.transition.hold,R.transition.push_out_to_right );
            startActivity(activity32Intent);
            finish();

        }




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

            spinner.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_apusies) {

            spinner.setVisibility(View.VISIBLE);
            Intent activity2Intent = new Intent(getApplicationContext(), MainApusies.class);
            overridePendingTransition(R.transition.hold,R.transition.push_out_to_right );
            startActivity(activity2Intent);

        } else if (id == R.id.nav_links) {
            spinner.setVisibility(View.VISIBLE);
            Intent activity2Intent = new Intent(getApplicationContext(), MainLinks.class);
            overridePendingTransition(R.transition.hold,R.transition.push_out_to_right );
            startActivity(activity2Intent);

        } else if (id == R.id.nav_epidoseis) {

            spinner.setVisibility(View.VISIBLE);
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


    public class GetMethodDemo extends AsyncTask<String, Void, String> {


        String server_response;

        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpsURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);
                String userPassword = token;
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Authorization", "Token " + userPassword);
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
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


                ListView listView = (ListView) findViewById(R.id.listview);

                if (aryJSONStrings != null) {


                    for (int i = 0; i < aryJSONStrings.length(); i++) {
                        try {
                            no_apusion = aryJSONStrings.getJSONObject(i).getString("no_apusion");
                            date_apusias = aryJSONStrings.getJSONObject(i).getString("date_apusias");

                            Integer result = Integer.valueOf(no_apusion);
                            sinolo = sinolo + result;
                            mylist.add("Ημερομηνία: " + date_apusias + " " + "Απουσίες: " + no_apusion);

                        } catch (JSONException ignored) {
                        }


                    }

                }

                combined.addAll(mylist);

                Log.d("MYINT", "value: " + sinolo);

                mycontent3 = (TextView) findViewById(R.id.textView5);
                mycontent3.setText("Σύνολο Απουσιών: " + sinolo);


                ArrayAdapter adapter = new ArrayAdapter<String>(MainApusies.this, R.layout.listad, R.id.textview, combined);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Get the selected item text from ListView
                       // String selectedItem = (String) parent.getItemAtPosition(position);

                        // Display the selected item text on TextView
                      //  String url = "https://techbook.pythonanywhere.com/" + combined2.get(position);
                       // Log.d("url11111", url);
                       // Intent i = new Intent(Intent.ACTION_VIEW);
                       // i.setData(Uri.parse(url));
                       // startActivity(i);
                    }
                });

                spinner.setVisibility(View.GONE);









            } catch (JSONException e) {


            }


        }







    }





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
