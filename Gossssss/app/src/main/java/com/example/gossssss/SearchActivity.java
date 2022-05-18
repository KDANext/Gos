package com.example.gossssss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ArrayAdapter<Country> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ArrayList<Country> countries = (ArrayList<Country>) getIntent().getSerializableExtra("MyClass");
        ListView lvSearch = (ListView) findViewById(R.id.lvSearch);
        adapter = new CountryAdapter(this,R.layout.list_item, countries);
        lvSearch.setAdapter(adapter);
    }
}