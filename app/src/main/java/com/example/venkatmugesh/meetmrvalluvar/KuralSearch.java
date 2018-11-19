package com.example.venkatmugesh.meetmrvalluvar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class KuralSearch extends AppCompatActivity {

    EditText searchEdit;
    TextView searchLine1;
    TextView searchLine2;
    TextView searchTrans;
    TextView transView2;
    Button searchOffline;
    String kuralNumber3 = "";
    String Line1 = "";
    String Line2 = "";
    String trans = "";
    int kurNo;
    OfflineDataBase myDb;
    boolean internet_connection() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    public void saveOffline(View view){
        boolean adding = myDb.addData(kuralNumber3 , Line1 , Line2 ,trans);
        if (adding)
        {
            Toast.makeText(KuralSearch.this , "Saved Successfully..!!" , Toast.LENGTH_LONG).show();
        }
    }

    public void searchKural(View view){
        if(internet_connection()){
            transView2.setVisibility(View.VISIBLE);
            searchOffline.setVisibility(View.VISIBLE);
            kuralNumber3 = String.valueOf(searchEdit.getText());
            kurNo = Integer.parseInt(kuralNumber3);
            Log.i("number" , String.valueOf(kurNo));
            if(kurNo > 1330){
                Toast.makeText(KuralSearch.this , "Enter a Valid Kural Number" , Toast.LENGTH_LONG).show();
            }else {
                DownloadTask task = new DownloadTask();
                task.execute("https://getthirukkural.appspot.com/api/2.0/kural/" +kurNo+ "?appid=vtzhxktz2hhiu&format=json");
            }
        }else {
            Toast.makeText(KuralSearch.this , "No Internet Connection" , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kural_search);
        searchEdit = (EditText)findViewById(R.id.editText2);
        searchLine1 = (TextView)findViewById(R.id.Searchline1);
        searchLine2 = (TextView)findViewById(R.id.Searchline2);
        searchTrans = (TextView)findViewById(R.id.transSearch);
        transView2 = (TextView)findViewById(R.id.transView2);
        searchOffline = (Button)findViewById(R.id.saveSearch);
        myDb = new OfflineDataBase(this);
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
                    trans = jsonPart.getString("Translation");



                    searchTrans.setText(trans);
                    searchLine1.setText(Line1);
                    searchLine2.setText(Line2);

                }



            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG);

            }


        }
    }
}
