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
        super(context, "finances.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table userinfo(uid primary key,phone,email,password )");
        db.execSQL("insert into userinfo(uid,password)values(\"admin\",\""+getStringMD5("admin000000")+"\") ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}
