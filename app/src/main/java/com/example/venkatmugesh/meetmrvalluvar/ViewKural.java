package com.example.venkatmugesh.meetmrvalluvar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViewKural extends AppCompatActivity {

    TextView line1;
    TextView line2;
    TextView kuralText;
    TextView transtext;
    Button saveOffline;
    String Line1 = "";
    String Line2 = "";
    String Number = "";
    String trans = "";
    OfflineDataBase myDb;
    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle mToggle;
    NavigationView mNavView;

    public void makingToast (String a){
        if(a == "a"){
            Toast.makeText(this , "Saved To Offline..!!!" , Toast.LENGTH_LONG).show();
        }
        if (a == "b") {

            Toast.makeText(this , "Error Saving" , Toast.LENGTH_LONG).show();
        }
    }
    boolean internet_connection() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_kural);
        myDb = new OfflineDataBase(this);
        saveOffline = (Button)findViewById(R.id.saveOffline);
        line1 = (TextView)findViewById(R.id.line1);
        line2 = (TextView)findViewById(R.id.line2);
        kuralText = (TextView)findViewById(R.id.kuralNo);
        transtext = (TextView)findViewById(R.id.transText);
        myDrawer = (DrawerLayout) findViewById(R.id.viewKuralDrawer);
        mToggle = new ActionBarDrawerToggle(this,myDrawer , R.string.open,R.string.close);
        mNavView = (NavigationView)findViewById(R.id.navView);
        myDrawer.addDrawerListener(mToggle);
        ConstraintLayout consLayout = (ConstraintLayout)findViewById(R.id.consLayout);
        mToggle.syncState();
        //mNavView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DownloadTask task = new DownloadTask();
        if (internet_connection()) {
            task.execute("https://getthirukkural.appspot.com/api/2.0/kural/rnd?appid=vtzhxktz2hhiu&format=json");
        }else {
            consLayout.setVisibility(View.INVISIBLE);
            final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                    "No internet connection.",
                    Snackbar.LENGTH_INDEFINITE );
            snackbar.setActionTextColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.colorAccent));
            snackbar.setAction(R.string.goOffline, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ViewKural.this , offlineListView.class);
                    startActivity(intent);
                }
            }).show();
        }





        saveOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean adding = myDb.addData(Number , Line1 , Line2 ,trans);
                if(adding){
                    Log.i("Offline" , "sved");
                    makingToast("a");
                }
                else{
                makingToast("b");
                Log.i("Error" , "hsjd");
                }
            }
        });


        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mainMenu :
                       Intent intent = new Intent(ViewKural.this , MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.searchKural :
                       Intent intent2 = new Intent(ViewKural.this , SearchKural.class);
                        startActivity(intent2);
                        break;

                    case R.id.playGame :
                        Intent intent3 = new Intent(ViewKural.this , PlayGame.class);
                        startActivity(intent3);
                        break;

                    case R.id.viewOffline :
                        Intent intent4 = new Intent(ViewKural.this , offlineListView.class);
                        startActivity(intent4);
                        break;
                    case R.id.exit:
                        finish();

                        default: return false;

                }

            return false;}
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }
                Log.i("Url Content" , result);

                return result;

            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG);

            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                String message = "";

                JSONObject jsonObject = new JSONObject(result);

                String kuralInfo = jsonObject.getString("KuralSet");

                Log.i("Url", kuralInfo);

                JSONObject kural = new JSONObject(kuralInfo);

                String kuralSet = kural.getString("Kural");
                Log.i("line1" , kuralSet);
                JSONArray arr = new JSONArray(kuralSet);

                for (int i = 0; i < arr.length(); i++) {

                    JSONObject jsonPart = arr.getJSONObject(i);


                    Line1 = jsonPart.getString("Line1");
                    Line2 = jsonPart.getString("Line2");
                    Number = jsonPart.getString("Number");
                    trans = jsonPart.getString("Translation");


                    kuralText.setText("Kural No: " + Number);
                    transtext.setText(trans);
                    line1.setText(Line1);
                    line2.setText(Line2);

                }



            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG);

            }


        }
    }
}