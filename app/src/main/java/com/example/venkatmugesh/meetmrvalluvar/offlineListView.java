package com.example.venkatmugesh.meetmrvalluvar;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class offlineListView extends AppCompatActivity {

    OfflineDataBase myDB;
    ListAdapter listAdapter;
    ListView listView;
    int[] count;
    DataFetcher myDF;
    PlayGameDb myPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_list_view);
        listView = (ListView) findViewById(R.id.offlineList);
        myPlay = new PlayGameDb(this);
        count = new int[2];
        myDF = new DataFetcher();
        myDB = new OfflineDataBase(this);
        count[0] =0;
        ArrayList<String> theList = new ArrayList<>();

        Cursor data = myDB.getData();

        if(data.getCount() == 0){

            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();

        }else{

            while(data.moveToNext()){

                theList.add(data.getString(1));

                listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);

                listView.setAdapter(listAdapter);

            }

        }
        count[0] = listView.getAdapter().getCount();
        Log.i("count" , String.valueOf(count[0]));
        myDF.setCount(count[0]);
       // Toast.makeText(offlineListView.this , "count" + count , Toast.LENGTH_LONG);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

                String name = adapterView.getItemAtPosition(i).toString();

                Log.d("hxhxh", "onItemClick: You Clicked on " + name);



                Cursor data = myDB.getItemID(name); //get the id associated with that name

                int itemID = -1;

                while(data.moveToNext()){

                    itemID = data.getInt(0);

                }

                if(itemID > -1){

                    Log.d("item", "onItemClick: The ID is: " + itemID);

                    Intent intent = new Intent(offlineListView.this, ViewOffline.class);

                    intent.putExtra("id",itemID);

                    intent.putExtra("name",name);

                    startActivity(intent);

                }

                else{

                   Log.i("erreo" , "no id");

                }

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView2, View view, final int i, long id) {
                final AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(offlineListView.this);
                final View mView = getLayoutInflater().inflate(R.layout.delete_dialog , null);

                deleteBuilder.setView(mView);
                final AlertDialog dialog = deleteBuilder.create();
                dialog.show();
                Button yes = (Button) mView.findViewById(R.id.btn_yes);
                Button no  = (Button) mView.findViewById(R.id.btn_no);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = adapterView2.getItemAtPosition(i).toString();

                        Cursor data = myDB.getItemID(name);

                        int itemID = -1;

                        while(data.moveToNext()){

                            itemID = data.getInt(0);

                        }
                        if (itemID > -1){
                            myDB.deleteKural(itemID);
                            boolean check =myPlay.addData(itemID);

                            if (check){
                                Log.i("Check" , "Sucessfully added");
                            }else {
                                Log.i("Sorry" , "Bro..!!!");
                            }
                        }else {
                            Log.i("error bro", String.valueOf(itemID));
                        }
                        dialog.dismiss();
                        Intent i = new Intent(offlineListView.this , offlineListView.class);
                        startActivity(i);
                        Toast.makeText(offlineListView.this , "Deleted Successfully..!" , Toast.LENGTH_LONG).show();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

                return false;
            }
        });


    }
}
