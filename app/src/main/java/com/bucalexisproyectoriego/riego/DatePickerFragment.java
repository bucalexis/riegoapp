package com.bucalexisproyectoriego.riego;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        TextView dateField= (TextView) getActivity().findViewById(R.id.textDate);

        String dayNumber = view.getDayOfMonth() + "";
        if (view.getDayOfMonth() < 10){
            dayNumber = "0" + view.getDayOfMonth();
        }

        String monthNumber = (view.getMonth() + 1) + "";
        if (view.getMonth() + 1 < 10){
            monthNumber = "0" + (view.getMonth() + 1);
        }

        dateField.setText(dayNumber + "/" + monthNumber+ "/"+view.getYear());

    }
}