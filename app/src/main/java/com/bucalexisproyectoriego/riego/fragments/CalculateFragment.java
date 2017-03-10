package com.bucalexisproyectoriego.riego.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.ArrayList;
import java.util.Date;

import android.widget.AdapterView;
import android.view.View.OnFocusChangeListener;
import com.bucalexisproyectoriego.riego.internetconnections.ProcessJSON;
import com.bucalexisproyectoriego.riego.R;
import com.bucalexisproyectoriego.riego.internetconnections.NetworkUtil;
import com.bucalexisproyectoriego.riego.databaseobjects.Crop;
import com.bucalexisproyectoriego.riego.databaseobjects.Kc;
import com.bucalexisproyectoriego.riego.databaseobjects.MyDBHandler;
import com.bucalexisproyectoriego.riego.databaseobjects.Pr;
import com.bucalexisproyectoriego.riego.databaseobjects.Stage;
import android.app.ProgressDialog;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import android.widget.Toast;
import android.text.format.DateFormat;
public class CalculateFragment extends Fragment {

    public CalculateFragment() {
    }

    private Button buttonCalculate;
    MyDBHandler dbHandler;
    Spinner stageSpinner;
    public boolean existsEto = false;
    private String date;
    private float kc;
    private float pr;
    private float cc;
    private float ha;
    private float eto;
    private TextView datePicker;
    private TextView ccInput;
    private TextView haInput;

    private AwesomeValidation awesomeValidation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_calculate, container, false);
        Spinner cropSpinner= (Spinner) rootView.findViewById(R.id.spinnerCrop);
        stageSpinner= (Spinner) rootView.findViewById(R.id.spinnerStage);

        dbHandler = new MyDBHandler(getActivity(), null, null, 1);
        ArrayList<Crop> cropsList = dbHandler.getCrops();

        CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(getActivity(), cropsList);
        cropSpinner.setAdapter(customSpinnerAdapter);
        //awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //awesomeValidation.addValidation(getActivity(), R.id.textDate, "1+", R.string.dateerror);

        cropSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Crop crop =(Crop)parent.getAdapter().getItem(position);
                Log.e("spinner", crop.getName() + crop.getId());

                ArrayList<Stage> stagesList = dbHandler.getStages(crop.getId());
                CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(getActivity(), stagesList);
                stageSpinner.setAdapter(customSpinnerAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        stageSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Stage stage =(Stage)parent.getAdapter().getItem(position);
                Kc stageKc = dbHandler.getKc(stage.getId());
                Pr stagePr = dbHandler.getPr(stage.getId());
                kc = stageKc.getValue();
                pr = stagePr.getValue();

                Log.e("spinner", stage.getName() + " " + stage.getId() + " "+ kc + " " + pr);
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
                    NetworkUtil.context = getContext();
                    if (validateInput()){

                    }

                       //Log.e("main", "available");

                       //ProcessJSON jsonData = (ProcessJSON) new ProcessJSON(getActivity(), view).execute("http://104.131.109.172/");






               /* LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity());
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
                alertDialogAndroid.show();*/
            }
        });

        datePicker = (TextView) rootView.findViewById(R.id.textDate);
        ccInput = (TextView) rootView.findViewById(R.id.textCC);
        haInput = (TextView) rootView.findViewById(R.id.textHA);


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
        datePicker.setText(convertDate(new Date().getTime(),"dd/MM/yyyy"));


        return rootView;
    }

    public boolean validateInput() {
        boolean result = true;
        if (!ccInput.getText().toString().isEmpty()){

            if (Integer.parseInt(ccInput.getText().toString()) < 20 ){
                ccInput.setError("Debe ser al menos 20");
                result = false;
            }
            if (Integer.parseInt(ccInput.getText().toString()) > 60 ){
                ccInput.setError("Debe ser menor a 60");
                result = false;
            }

        }
        else{
            ccInput.setError("Ingrese valor");
        }

        if (!haInput.getText().toString().isEmpty()){

            if (Integer.parseInt(haInput.getText().toString()) < 20 ){
                haInput.setError("Debe ser al menos 20");
                result = false;
            }
            if (Integer.parseInt(haInput.getText().toString()) > 60 ){
                haInput.setError("Debe ser menor a 60");
                result = false;
            }

        }
        else{
            haInput.setError("Ingrese valor");
            result = false;
        }


        return result;
    }

    public void openDateDialog(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
    public String convertDate(long dateInMilliseconds,String dateFormat) {
        return DateFormat.format(dateFormat, dateInMilliseconds).toString();
    }
    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList asr;


        public CustomSpinnerAdapter(Context context,ArrayList asr) {
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
            txt.setText(asr.get(position).toString());
            txt.setTextColor(Color.parseColor("#000000"));
            return  txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(getActivity());
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(16);
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
            txt.setText(asr.get(i).toString());
            txt.setTextColor(Color.parseColor("#000000"));
            return  txt;
        }

    }

}
