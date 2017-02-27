package com.bucalexisproyectoriego.riego;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by NanX3 on 29/10/2016.
 */

public class RecordsAdapter extends ArrayAdapter<Record> {
    public RecordsAdapter(Context context, List<Record> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.records_list,
                    parent,
                    false);
        }

        // Referencias UI.
        ImageView edit = (ImageView) convertView.findViewById(R.id.editRecord);
        ImageView delete = (ImageView) convertView.findViewById(R.id.deleteRecord);

        TextView name = (TextView) convertView.findViewById(R.id.nameText);
        TextView date = (TextView) convertView.findViewById(R.id.dateText);


        // Lead actual.
        final Record record = getItem(position);

        // Setup.
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.e("ja","edit" + record.getName());
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.e("ja","delete" + record.getName());
            }
        });
        name.setText(record.getName());
        date.setText(record.getDate());
        return convertView;
    }
}