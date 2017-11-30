package com.example.johnjairo.demostrapp.modelo;
/**
 * Created by rocka on 8/06/2017.
 */

import com.example.johnjairo.demostrapp.R;
import com.example.johnjairo.demostrapp.vista.Item_paso;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class AdapterPaso extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Item_paso> items;

    public AdapterPaso (Activity activity, ArrayList<Item_paso> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Item_paso> paso) {
        for (int i = 0; i < paso.size(); i++) {
            items.add(paso.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_demos, null);
        }

        Item_paso dir = items.get(position);

        TextView paso = (TextView) v.findViewById(R.id.top_paso);
        paso.setText(dir.getPaso());

        TextView expresion = (TextView) v.findViewById(R.id.top_expresion);
        expresion.setText(dir.getExpresion());

        TextView justificacion = (TextView) v.findViewById(R.id.top_justificacion);
        justificacion.setText(dir.getJustificacion());

        return v;
    }
}