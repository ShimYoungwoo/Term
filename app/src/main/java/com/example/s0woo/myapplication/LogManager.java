package com.example.s0woo.myapplication;

/**
 * Created by s0woo on 2017-04-14.
 */

import android.util.Log;


public class LogManager
{
    public static final boolean _DEBUG = true;

    private static final String _TAG = "TMAP_OPEN_API";


    public static void printLog(String text)
    {
        if ( _DEBUG )
        {
            Log.d(_TAG, text);
        }
    }


    public static void printError(String text)
    {
        if(_DEBUG)
            Log.e(_TAG, "**ERROR** : " + text);
    }

}