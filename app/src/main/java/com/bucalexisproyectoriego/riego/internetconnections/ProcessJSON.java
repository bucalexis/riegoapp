package com.bucalexisproyectoriego.riego.internetconnections;

import android.app.ProgressDialog;
import android.os. AsyncTask;
import android.util.Log;
import org.json.JSONObject;
import org.json.JSONException;
import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.content.DialogInterface;

import android.support.v7.app.AlertDialog;

import android.view.LayoutInflater;

import android.widget.EditText;

import com.bucalexisproyectoriego.riego.R;


public class ProcessJSON extends AsyncTask<String, Void, String>{

    public double eto = 0;
    Activity activity;
    View view;
    public EditText userInputDialogEditText;
    public EditText loteInputDialogEditText;

    float cc, pr, ha, kc;

    public ProcessJSON(Activity activity, View view, float cc, float ha, float pr, float kc){
        this.activity = activity;
        this.view = view;
        this.cc = cc;
        this.ha = ha;
        this.pr = pr;
        this.kc = kc;
    }

    ProgressDialog progress;

    private boolean validateInput(){
        boolean result = true;
        if (userInputDialogEditText.getText().toString().isEmpty()){
            userInputDialogEditText.setError("Ingrese valor");
            result = false;
        }
        if (loteInputDialogEditText.getText().toString().isEmpty()){
            loteInputDialogEditText.setError("Ingrese valor");
            result = false;
        }

        return result;
    }

     protected void onPreExecute(){
         progress = new ProgressDialog(activity);
         progress.setMessage("Conectando...");
         progress.setCancelable(false);
         progress.show();
     }

    protected String doInBackground(String... strings){
        NetworkUtil.startConnection();
        String stream = null;
        String urlString = strings[0];
        if (NetworkUtil.isInternetAccessible()){
            HTTPDataHandler hh = new HTTPDataHandler();
            Log.e("json", "available");
            stream = hh.GetHTTPData(urlString);

        }
        else{
            Log.e("json", "not available");

        }
        // Return the data from specified url

        return stream;
    }

    protected void onPostExecute(String stream){
        if(stream !=null){
            try{

                // Get the full HTTP Data as JSONObject
                JSONObject reader= new JSONObject(stream);

                eto = (Double) reader.get("eto");
                NetworkUtil.success = true;
                Log.e("json stram: ", eto + "");

            }catch(JSONException e){
                e.printStackTrace();
            }

        } // if statement end

        progress.dismiss();
        if (NetworkUtil.success){
            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(activity);
            View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(activity);
            alertDialogBuilderUserInput.setView(mView);

            userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
            loteInputDialogEditText = (EditText) mView.findViewById(R.id.loteInputDialog);

            alertDialogBuilderUserInput
                    .setTitle("Su resultado es")
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
            alertDialogAndroid.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Boolean wantToCloseDialog = false;
                    Log.e("data form main", "cc:" + cc + "ha:" + ha + "pr:" + pr + "kc:" + kc);

                    Log.e("s", userInputDialogEditText.getText().toString());
                    //Do stuff, possibly set wantToCloseDialog to true then...
                    if(validateInput())
                        alertDialogAndroid.dismiss();
                    //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                }
            });




        }
        else{
            String error = "";
            if (NetworkUtil.serverError){
                error = "Servidor no disponible, intente más tarde";
            }
            if(NetworkUtil.noInternetError){
                error = "Verifique su conexión a internet";
            }
            Snackbar.make(view, error, Snackbar.LENGTH_LONG)
                    .show();
        }




    } // onPostExecute() end
} // ProcessJSON class end
