package com.example.gossssss;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.TextView;

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
                intent.putExtra("MyClass", search);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void loadDatabase() {
        adapter = new CountryAdapter(this, R.layout.list_item, Countries);
    }


    public void UpdateList() {
        adapter.notifyDataSetChanged();
    }
}