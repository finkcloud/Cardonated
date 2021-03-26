package com.philencripted.cardonated.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.philencripted.cardonated.ui.MainActivity;


public class Util {
    // context
    private static MainActivity _context = null;

    // protect access to this
    public static MainActivity getContext() {  return _context;  }
    public  static void set_context(MainActivity context){
        _context = context;
    }


    // check if network is available
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) Util.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
