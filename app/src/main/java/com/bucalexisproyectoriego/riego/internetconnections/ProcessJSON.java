package com.bucalexisproyectoriego.riego.internetconnections;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import com.bucalexisproyectoriego.riego.databaseobjects.MyDBHandler;

import org.json.JSONObject;
import org.json.JSONException;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.content.DialogInterface;

import android.support.v7.app.AlertDialog;

import android.view.LayoutInflater;

import android.widget.EditText;
import android.widget.TextView;

import com.bucalexisproyectoriego.riego.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;

public class ProcessJSON extends AsyncTask<String, Void, String> {

    public double eto = 0;
    Activity activity;
    View view;
    public EditText userInputDialogEditText;
    public EditText loteInputDialogEditText;
    public TextView resultDialogEditText;


    float cc, pr, ha, kc;
    int crop_id, stage_id, year, month, day;

    public ProcessJSON(Activity activity, View view, float cc, float ha, float pr, float kc, int crop_id, int stage_id, int year, int month, int day) {
        this.activity = activity;
        this.view = view;
        this.cc = cc;
        this.ha = ha;
        this.pr = pr;
        this.kc = kc;
        this.crop_id = crop_id;
        this.stage_id = stage_id;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    ProgressDialog progress;

    private boolean validateInput() {
        boolean result = true;
        if (userInputDialogEditText.getText().toString().isEmpty()) {
            userInputDialogEditText.setError("Ingrese valor");
            result = false;
        }
        if (loteInputDialogEditText.getText().toString().isEmpty()) {
            loteInputDialogEditText.setError("Ingrese valor");
            result = false;
        }

        return result;
    }

    protected void onPreExecute() {
        progress = new ProgressDialog(activity);
        progress.setMessage("Conectando...");
        progress.setCancelable(false);
        progress.show();
    }

    protected String doInBackground(String... strings) {
        NetworkUtil.startConnection();
        String stream = null;
        String urlString = strings[0];
        if (NetworkUtil.isInternetAccessible()) {
            HTTPDataHandler hh = new HTTPDataHandler();
            Log.e("json", "available");
            stream = hh.GetHTTPData(urlString);
        } else {
            Log.e("json", "not available");

        }
        // Return the data from specified url

        return stream;
    }

    protected void onPostExecute(String stream) {
        if (stream != null) {
            try {

                // Get the full HTTP Data as JSONObject
                JSONObject reader = new JSONObject(stream);

                eto = (Double) reader.get("eto");
                NetworkUtil.success = true;
                Log.e("json stram: ", eto + "");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } // if statement end

        progress.dismiss();
        if (NetworkUtil.success) {
            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(activity);
            View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(activity);
            alertDialogBuilderUserInput.setView(mView);

            userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
            loteInputDialogEditText = (EditText) mView.findViewById(R.id.loteInputDialog);
            resultDialogEditText = (TextView) mView.findViewById(R.id.dialogResult);


            alertDialogBuilderUserInput
                    .setTitle("Debe regar el día:")
                    .setCancelable(false)
                    .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {
                        }
                    })
                    .setNegativeButton("Cancelar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogBox, int id) {
                                    dialogBox.cancel();
                                }
                            });

            final AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
            alertDialogAndroid.show();
            int result = calculate();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date steepingdate = null;
            try {
                steepingdate = formatter.parse(day + "/" + month + "/" + year);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String resultForScreen = dateFormat("dd/MM/yyyy", steepingdate, result);
            final String resultForDB = dateFormat("yyyy-MM-dd HH:mm:ss", steepingdate, result);
            final String dateForDB = dateFormat("yyyy-MM-dd HH:mm:ss", steepingdate, 0);


            resultDialogEditText.setText(resultForScreen);
            alertDialogAndroid.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (validateInput()) {
                        String name = userInputDialogEditText.getText().toString();
                        String lote = loteInputDialogEditText.getText().toString();

                      //  public void addRecord(String name, int crop_id, int stage_id, String result, float cc, float ha, float latitude, float longitude, String lote, String date) {
                            MyDBHandler dbHandler = new MyDBHandler(activity, null, null, 1);
                            dbHandler.addRecord(name, crop_id, stage_id, resultForDB, cc, ha, 0, 0, lote, dateForDB);
                            alertDialogAndroid.dismiss();


                    }
                }
            });


        } else {
            String error = "";
            if (NetworkUtil.serverError) {
                error = "Servidor no disponible, intente más tarde";
            }
            if (NetworkUtil.noInternetError) {
                error = "Verifique su conexión a internet";
            }
            Snackbar.make(view, error, Snackbar.LENGTH_LONG)
                    .show();
        }


    } // onPostExecute() end

    private int calculate() {
        int result = 0;
        cc /= 100;
        ha /= 100;
        Log.e("data form main", "cc:" + cc + "ha:" + ha + "pr:" + pr + "kc:" + kc);

        float hfa = (float) (cc / 2.0 * 0.5);
        float mr = cc - hfa;
        float hres = (ha - mr) * pr;
         eto = 2.25;
        float etr = (float) (eto * kc);
        float s = (hres / etr);
        result = (int) (hres / etr);
        Log.e("data form main", "hres:" + hres + "etr:" + etr + "mr:" + mr);


        Log.e("data form main", "dias:" + result);


        Log.e("s", userInputDialogEditText.getText().toString());


        return result;
    }

    //yyyy-MM-dd HH:mm:ss

    private String dateFormat(String format, Date steepingdate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(steepingdate);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        DateFormat dateFormat = new SimpleDateFormat(format);

        Log.e("s", dateFormat.format(calendar.getTime()));

        return dateFormat.format(calendar.getTime());

    }
} // ProcessJSON class end
