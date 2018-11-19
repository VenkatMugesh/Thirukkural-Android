package com.example.venkatmugesh.meetmrvalluvar;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewOffline extends AppCompatActivity {

    TextView kuralNo;
    TextView Offline1;
    TextView Offline2;
    TextView transOff;
    DataFetcher dFetch;
    OfflineDataBase myDb;
    String kuralLine1 = "vdgvxfvdf";
    String kuralNumber = "dcd";
    String kuralLine2 = "dds";
    String trans = "dccd";
    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle mToggle;
    NavigationView mNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offline);

        kuralNumber = new String();
        //கருமத்தால் நாணுதல
        kuralNo = (TextView)findViewById(R.id.kuralNoOff);
        Offline1 =(TextView)findViewById(R.id.line1Off);
        Offline2 = (TextView)findViewById(R.id.line2Off);
        transOff = (TextView)findViewById(R.id.transoff);
        dFetch = new DataFetcher();
        myDb = new OfflineDataBase(this);
        myDrawer = (DrawerLayout) findViewById(R.id.offlineDrawer);
        mToggle = new ActionBarDrawerToggle(this,myDrawer , R.string.open,R.string.close);
        mNavView = (NavigationView)findViewById(R.id.offNav);
        myDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        int id;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                id = 0;
            } else {
                id= extras.getInt("id");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("id");
        }
        Cursor data = myDb.getData(id);
        while(data.moveToNext()){
            kuralNumber = data.getString(1);
            kuralLine1=data.getString(2);
            kuralLine2 = data.getString(3);
            trans = data.getString(4);
        }
        kuralNo.setText("KURAL NO: " + kuralNumber);
        Offline1.setText(kuralLine1);
        Offline2.setText(kuralLine2);
        transOff.setText(trans);

        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mainMenu :
                        Intent intent = new Intent(ViewOffline.this , MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.searchKural :
                        Intent intent2 = new Intent(ViewOffline.this , SearchKural.class);
                        startActivity(intent2);
                        break;

                    case R.id.playGame :
                        Intent intent3 = new Intent(ViewOffline.this , PlayGame.class);
                        startActivity(intent3);
                        break;

                    case R.id.viewOffline :
                        Intent intent4 = new Intent(ViewOffline.this , offlineListView.class);
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

}

