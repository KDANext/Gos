package com.example.gossssss;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

// Класс для списка с нужными нам полями и xml для него в папке layout list_item

public class CountryAdapter extends ArrayAdapter<Country> {
    private LayoutInflater inflater;
    private int layout;
    private List<Country> Countries;

    public CountryAdapter(Context context, int resource, List<Country> Countries) {
        super(context, resource, Countries);
        this.Countries = Countries;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view=inflater.inflate(this.layout, parent, false);
        TextView nameView = (TextView) view.findViewById(R.id.Name);
        TextView capitalView = (TextView) view.findViewById(R.id.Capital);
        TextView number = (TextView) view.findViewById(R.id.Number);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.Flag);

        Country state = Countries.get(position);

        nameView.setText(state.getName());
        capitalView.setText(state.getCapital());
        number.setText(String.valueOf(state.getNumber()));
        checkBox.setChecked(state.isFlag());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    state.setFlag(true);
                }
                else {
                    state.setFlag(false);
                }
            }
        });

        return view;
    }
}
