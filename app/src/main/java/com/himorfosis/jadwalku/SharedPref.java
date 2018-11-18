package com.himorfosis.jadwalku;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    public SharedPref() {
        super();
    }

    public static void saveLogin (String DBNAME, String Tablekey, String value, Context context){

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(Tablekey, value);
        editor.commit();
    }


    public static String getLogin (String DBNAME, String Tablekey, Context context){

        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);
        text = settings.getString(Tablekey, null);
        return text;
    }


    public static void deleteLogin (String DBNAME, Context context){

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.commit();

    }

    //the constants
    public static void saveData (String DBNAME, String Tablekey, String value, Context context){

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(Tablekey, value);
        editor.commit();
    }

    public static String getData (String DBNAME, String Tablekey, Context context){

        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);
        text = settings.getString(Tablekey, null);
        return text;
    }

    public static void deleteData (String DBNAME, Context context){

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.commit();

    }

    public static void saveIntPref(String DBNAME, String Tablekey, int value , Context context){

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putInt(Tablekey, value);
        editor.commit();

    }

    public static int getIntPref (String DBNAME, String Tablekey, Context context){

        SharedPreferences settings;

        settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);
        int value = settings.getInt(Tablekey, 2);

        return value;

    }

}
