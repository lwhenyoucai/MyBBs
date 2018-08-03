package com.example.lwhenyoucai.mysamplebbs.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lwhenyoucai on 2017/5/14.
 */
public class SharedPreferencesHelp {

    Context context;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public SharedPreferencesHelp(Context c, String str){
        context = c;
        sp = context.getSharedPreferences(str,0);
        editor = sp.edit();
    }

    public void putValue(String key,String value){
        editor = sp.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public String getValue(String key){
        return sp.getString(key,"0");

    }


    public void putValueInt(String key,Integer value){
        editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public void putValueLong(String key,Long value){
        editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }
    public long getValueLong(String key){
        return sp.getLong(key, 0);
    }

    public int getValueInt(String key){
        return sp.getInt(key, 0);

    }
    public void putValueBoolean(String key,boolean value){
        editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getValueBoolean(String key){
        return sp.getBoolean(key, true);

    }


}
