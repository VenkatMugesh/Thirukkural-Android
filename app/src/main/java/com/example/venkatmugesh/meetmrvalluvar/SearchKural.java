package com.example.venkatmugesh.meetmrvalluvar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchKural extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_kural);
        Button kuralsearch = (Button)findViewById(R.id.searchNumber);
        Button kuralSelect = (Button)findViewById(R.id.selectKural);
        kuralsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchKural.this , KuralSearch.class);
                startActivity(intent);
            }
        });
        kuralSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchKural.this , SelectKural.class);
                startActivity(i);
            }
        });
    }
}
