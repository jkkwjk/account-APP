package com.jkk.finances.Tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DBHelper extends SQLiteOpenHelper {
    public static String getStringMD5(String sourceStr) {
        String s = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger bigInt = new BigInteger(1, md.digest(sourceStr.getBytes()));
            s = String.format("%032x", bigInt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }

    public DBHelper(Context context){
        super(context, "finances.db", null, 5);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(password ,username)");
        db.execSQL("create table accont(uuid, username ,time,type,use,url,str)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion){
            if (oldVersion == 1){
                db.execSQL("alter table user add column username");
            }else if (oldVersion == 2){

            }else if (oldVersion == 3){
                db.execSQL("create table accont(username ,time,type,use,url,str)");
            }else if (oldVersion == 4){
                db.execSQL("alter table accont add column uuid");
            }
            onUpgrade(db,oldVersion+1,newVersion);
        }
    }
}
