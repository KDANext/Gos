package com.example.gossssss;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Country> Countries = new ArrayList();
    ArrayAdapter<Country> adapter;
    FragmentManager fragmentManager = getSupportFragmentManager();
    dbService Service;
    EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        Service = new dbService(this);

        SQLiteDatabase db = Service.getWritableDatabase();
        Cursor c = db.query("mytable", null, null, null, null, null, null);

        while (c.moveToNext()) {
            Countries.add(new Country(c.getInt(0),c.getString(1), c.getString(2), c.getInt(3), false));
        }
        //db.delete("mytable",null,null);
        //Для списка....
        ListView lvMain = (ListView) findViewById(R.id.lvSearch);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter = new CountryAdapter(this, R.layout.list_item, Countries);
        lvMain.setAdapter(adapter);
        //Для кнопок...
        Button btnAddFragment = (Button) findViewById(R.id.btnAddFragment);
        Button btnUpdateFragment = (Button) findViewById(R.id.btnUpdateFragment);
        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
        Button btnSaveJson = (Button) findViewById(R.id.btnSaveJSON);
        Button btnLoadJson = (Button) findViewById(R.id.btnLoadJSON);
        //
        searchField = (EditText) findViewById(R.id.TextSearch);
        //
        //
        btnAddFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment addFragment = new AddFragment(Countries, Service);
                FragmentTransaction ft = fragmentManager.beginTransaction();
                assert addFragment != null;
                ft.replace(R.id.frame, addFragment);
                ft.commit();
            }
        });
        btnUpdateFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = -1;
                for (int i = 0; i < Countries.size(); i++) {
                    if (Countries.get(i).isFlag()) {
                        position = i;
                        break;
                    }
                }
                if (position != -1) {
                    Fragment updateFragment = new UpdateFragment(Countries, position, Service);
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    assert updateFragment != null;
                    ft.replace(R.id.frame, updateFragment);
                    ft.commit();
                }

            }
        });
        btnSaveJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        btnLoadJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });

    }

    //действия на кнопках
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // Фильтрация и отчет
            case R.id.btnSearch:
                Intent intent = new Intent(this, SearchActivity.class);
                //поиск по совпадению(слово)
//                ArrayList<Country> search = new ArrayList<Country>();
//                for( int i = 0;i<Countries.size();i++){
//                    if(Countries.get(i).getName().equals(searchField.getText().toString())){
//                        search.add(Countries.get(i));
//                    }
//                }
                //поиск по чекбоксам
                ArrayList<Country> search = new ArrayList<Country>();
                for (int i = 0; i < Countries.size(); i++) {
                    if (Countries.get(i).isFlag()) {
                        search.add(Countries.get(i));
                    }
                }
                //
                intent.putExtra("MyClass", search);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Countries);
        editor.putString("task list", json);
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Country>>() {}.getType();
        ArrayList<Country> Temp = gson.fromJson(json, type);
        Countries.clear();
        Countries.addAll(Temp);
        if (Countries == null) {
            Countries = new ArrayList<>();
        }
        UpdateList();
    }




    public void UpdateList() {
        adapter.notifyDataSetChanged();
    }
}