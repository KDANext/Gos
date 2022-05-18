package com.example.gossssss;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.Provider;
import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class UpdateFragment extends Fragment {

    ArrayList<Country> countries;
    Integer position;
    dbService Service;

    @SuppressLint("ValidFragment")
    public UpdateFragment(ArrayList<Country> countries, Integer position, dbService Service) {
        this.countries = countries;
        this.position = position;
        this.Service = Service;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        Button btnUpdateItem = (Button) view.findViewById(R.id.btnUpdateItem);

        EditText etCountry = (EditText) view.findViewById(R.id.UpdateName);
        EditText etCapital = (EditText) view.findViewById(R.id.UpdateCapital);
        EditText etNumber = (EditText) view.findViewById(R.id.UpdateNumber);

        etCountry.setText(countries.get(position).getName());
        etCapital.setText(countries.get(position).getCapital());
        etNumber.setText(countries.get(position).getNumber().toString());

        btnUpdateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = Service.getWritableDatabase();
                countries.get(position).setName(etCountry.getText().toString());
                countries.get(position).setCapital(etCapital.getText().toString());
                countries.get(position).setNumber(Integer.parseInt(etNumber.getText().toString()));
                countries.get(position).setFlag(false);

                cv.put("name", etCountry.getText().toString());
                cv.put("capital", etCapital.getText().toString());
                cv.put("number", Integer.parseInt(etNumber.getText().toString()));
                cv.put("flag", 1);
                db.update("mytable", cv, "id = ?", new String[]{countries.get(position).getIdString()});
                ((MainActivity) getActivity()).UpdateList();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}