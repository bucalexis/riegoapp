package com.bucalexisproyectoriego.riego;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.view.View.OnFocusChangeListener;
import java.util.Arrays;


public class ConnectFragment extends Fragment {

    public ConnectFragment() {
    }

    private Button buttonCalculate;
    private String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_connect, container, false);
        Spinner spinnerCustom= (Spinner) rootView.findViewById(R.id.spinnerCrop);
        Spinner spinnerCustom2= (Spinner) rootView.findViewById(R.id.spinnerStage);

        MyDBHandler dbHandler = new MyDBHandler(getActivity(), null, null, 1);
        ArrayList<Crop> cropsList = dbHandler.getCrops();

        ArrayList<Stage> stagesList = dbHandler.getStages(cropsList.get(0).getId());
        Kc kc =  dbHandler.getKc(1);
        Pr pr = dbHandler.getPr(4);

        ArrayList<Record> recordsList = dbHandler.getRecords();

        Log.e("ee","size" + cropsList.size());
        Log.e("ee","size" + stagesList.size());
        Log.e("ee","size" + recordsList.get(0).getDate());

        Log.e("ee","kc" + kc.getValue());
        Log.e("ee","pr" + pr.getValue());





        // Spinner Drop down elements
        ArrayList<String> languages = new ArrayList<String>();
        languages.add("Andorid");
        languages.add("IOS");
        languages.add("PHP");
        languages.add("Java");
        languages.add(".Net");
        String [] crops = getResources().getStringArray(R.array.planets_array);
        String [] stages = getResources().getStringArray(R.array.stages_array);


        CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(getActivity(), new ArrayList (Arrays.asList(crops)));
        spinnerCustom.setAdapter(customSpinnerAdapter);
        customSpinnerAdapter=new CustomSpinnerAdapter(getActivity(), new ArrayList (Arrays.asList(stages)));
        spinnerCustom2.setAdapter(customSpinnerAdapter);

        spinnerCustom.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


       // getActivity().setContentView(R.layout.android_user_input_dialog);

        buttonCalculate = (Button) rootView.findViewById(R.id.buttonCalculate);
       buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity());
                View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setTitle("Su resultado es")
                        .setCancelable(false)
                        .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                                Log.e("ja",userInputDialogEditText.getText().toString());
                            }
                        })

                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });

        TextView datePicker = (TextView) rootView.findViewById(R.id.textDate);
        datePicker.setOnFocusChangeListener(new OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    openDateDialog();
                } else {
                }
            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDateDialog();

            }
        });

        datePicker.setInputType(InputType.TYPE_NULL);
        datePicker.setTextIsSelectable(true);
        datePicker.setKeyListener(null);


        return rootView;
    }

    public void openDateDialog(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<String> asr;

        public CustomSpinnerAdapter(Context context,ArrayList<String> asr) {
            this.asr=asr;
            activity = context;
        }



        public int getCount()
        {
            return asr.size();
        }

        public Object getItem(int i)
        {
            return asr.get(i);
        }

        public long getItemId(int i)
        {
            return (long)i;
        }



        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(getActivity());
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(18);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(asr.get(position));
            txt.setTextColor(Color.parseColor("#000000"));
            return  txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(getActivity());
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(16);
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
            txt.setText(asr.get(i));
            txt.setTextColor(Color.parseColor("#000000"));
            return  txt;
        }

    }

}
