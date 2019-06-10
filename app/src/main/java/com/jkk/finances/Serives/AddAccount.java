package com.jkk.finances.Serives;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.fastjson.JSON;
import com.jkk.finances.Model.AccountInfo;
import com.jkk.finances.Tools.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class AddAccount {
    public static boolean addAccount(Context context,String userName, AccountInfo accountInfo){
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uuid",accountInfo.getUuid());
        values.put("username",userName);
        values.put("time",accountInfo.getTime());
        values.put("type",accountInfo.getType());
        values.put("money",accountInfo.getMoney());
        values.put("use",accountInfo.getUseName());
        if (accountInfo.getMore()!=null){
            if (accountInfo.getMore()==0){
                values.put("str",accountInfo.getStr());
            }else {
                values.put("str",accountInfo.getStr());
                values.put("url",accountInfo.getUrl());
            }
        }

        long rowID=db.insert("accont",null,values);
        return rowID!=-1;
    }

    private void update(Context context,String userName, AccountInfo accountInfo){
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        db.delete("accont","uuid",new String[]{accountInfo.getUuid()});
        this.addAccount(context, userName, accountInfo);
    }
    public static List<AccountInfo> getInfoByUserName(Context context,String userName){
        List<AccountInfo> ret = new ArrayList<>();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from accont where username = ?", new String[]{userName});

        while (cursor.moveToNext()){
            String uuid = cursor.getString(cursor.getColumnIndex("uuid"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String use = cursor.getString(cursor.getColumnIndex("use"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            String str = cursor.getString(cursor.getColumnIndex("str"));
            Float money = Float.parseFloat(cursor.getString(cursor.getColumnIndex("money")));

            Integer more = null;

            if (!(url==null||url.equals("")||url.equals("{}"))){
                int i;
                for (i=0; i<str.length(); ++i){
                    if (str.charAt(i)!=5){
                        break;
                    }
                }
                if (i==str.length()){
                    more = 1;
                }else {
                    more = 2;
                }
            }else {
                url = null;
                if (!(str==null || str.equals(""))){
                    more = 0;
                }else {
                    str = null;
                }
            }

            ret.add(new AccountInfo(uuid,use,money,time,type,more,str,url));
        }
        return ret;
    }
}
