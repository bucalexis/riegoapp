package com.bucalexisproyectoriego.riego.internetconnections;

/**
 * Created by bucalexis on 3/4/17.
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.IOException;
import android.widget.Toast;
import com.bucalexisproyectoriego.riego.MainActivity;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.net.URL;
import java.io.IOException;
import android.util.Log;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.MalformedURLException;


public class NetworkUtil {

   public static Context context;
    public static boolean serverError;
    public static boolean noInternetError;
    public static boolean success;

    public static void startConnection(){
        serverError = false;
        noInternetError = false;
        success = false;
    }

    public static boolean isInternetAvailable() {
        String host = "104.131.109.172";
        int port = 80;
        Socket socket = new Socket();

        try {
            socket.connect(new InetSocketAddress(host, port), 2000);
            socket.close();
            return true;
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException es) {}
            return false;
        }
    }

    public static boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isInternetAccessible()  {
        if(isNetwork()) {


            URL url = null;
            try {
                url = new URL("http://www.google.com");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(2000);
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                return true;
            } catch (Exception e) {
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
            }
            //Log.d("net", "Internet not available!");
        }
        NetworkUtil.noInternetError = true;
        return false;
    }

    private static boolean isNetwork() {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        Log.e("netwotk", isConnected + "");
        return isConnected;
    }




}
