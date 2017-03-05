package com.bucalexisproyectoriego.riego.internetconnections;

import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.net.URL;
import java.io.IOException;
import android.util.Log;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.MalformedURLException;

/**
 * Created by cfsuman on 31/05/2015.
 */
public class HTTPDataHandler {

    static String stream = null;

    public HTTPDataHandler(){
    }

    public String GetHTTPData(String urlString){
        HttpURLConnection urlConnection;
        try{
            URL url = new URL(urlString);
            //Log.e("Server", "entro1");


            urlConnection = (HttpURLConnection) url.openConnection();
           // Log.e("Server", "entro2");

            // Check the connection status
           // Log.e("Server", urlConnection.getResponseCode() + "");

            if(urlConnection.getResponseCode() == 200)
            {
                Log.e("Server", "entro33");
                // if response code = 200 ok
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                // Read the BufferedInputStream
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    sb.append(line);
                }
                stream = sb.toString();
                // End reading...............

                // Disconnect the HttpURLConnection
                urlConnection.disconnect();

            }
            else
            {
              NetworkUtil.serverError = true;

               Log.e("http", "server error");
            }
        }catch (MalformedURLException e){
            Log.e("http", "malformed url");
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
            Log.e("http", "no internet error");
            NetworkUtil.noInternetError = true;
        }finally {


        }
        // Return the data from specified url
        return stream;
    }
}