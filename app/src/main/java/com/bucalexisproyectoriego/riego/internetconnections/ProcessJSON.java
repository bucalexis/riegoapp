package com.bucalexisproyectoriego.riego.internetconnections;

import android.app.ProgressDialog;
import android.os. AsyncTask;
import android.util.Log;


import org.json.JSONObject;
import org.json.JSONException;

import android.app.Activity;

import android.support.design.widget.Snackbar;
import android.view.View;

public class ProcessJSON extends AsyncTask<String, Void, String>{

    public double eto = 0;
    Activity activity;
    View view;

    public ProcessJSON(Activity activity, View view){
        this.activity = activity;
        this.view = view;
    }

    ProgressDialog progress;

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
