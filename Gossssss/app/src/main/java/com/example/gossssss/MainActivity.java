package com.example.gossssss;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    ArrayList<Country> Countries = new ArrayList();
    ArrayAdapter<Country> adapter;
    FragmentManager fragmentManager = getSupportFragmentManager();
    dbService Service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        Service = new dbService(this);

        SQLiteDatabase db = Service.getWritableDatabase();
        Cursor c = db.query("mytable", null, null, null, null, null, null);

        while (c.moveToNext()){
            Countries.add(new Country(c.getString(1),c.getString(2),c.getInt(3),false));
        }

        //Для списка....
        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter = new CountryAdapter(this,R.layout.list_item, Countries);
        lvMain.setAdapter(adapter);
        //Для кнопок...
        Button btnAddFragment = (Button) findViewById(R.id.btnAddFragment);
        Button btnUpdateFragment = (Button) findViewById(R.id.btnUpdateFragment);
        //

        //
        btnAddFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment addFragment = new AddFragment(Countries,Service);
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
                for(int i = 0;i<Countries.size();i++){
                    if(Countries.get(i).isFlag()){
                        position = i;
                        break;
                    }
                }
                Fragment updateFragment = new UpdateFragment(Countries,position);
                FragmentTransaction ft = fragmentManager.beginTransaction();
                assert updateFragment != null;
                ft.replace(R.id.frame, updateFragment);
                ft.commit();
            }
        });

    }

    private void loadDatabase() {
        adapter = new CountryAdapter(this,R.layout.list_item, Countries);
    }


    public void UpdateList(){
        adapter.notifyDataSetChanged();
    }
}