package com.jkk.finances.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jkk.finances.LoginActivity;
import com.jkk.finances.R;
import com.jkk.finances.Tools.DBHelper;
import com.jkk.finances.Utils.ToastShow;

public class RegisterActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private Context context;

    private EditText editText_username;
    private EditText editText_password;
    private Button button_register;
    private Button button_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.context = this;
        mDatabase = new DBHelper(context).getReadableDatabase();
        editText_username=(EditText)findViewById(R.id.editText_register_name);
        editText_password=(EditText)findViewById(R.id.editText_register_password);
        button_back=(Button)findViewById(R.id.button_register_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });
        button_register=(Button)findViewById(R.id.button_register_success);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editText_username.getText().toString();
                String password=editText_password.getText().toString();
                if(name.equals("")||password.equals(""))
                {
                    ToastShow.show(context,"用户名和密码不能为空");
                }
                else
                    {
                        boolean exist=find(mDatabase,name);
                        if(exist)
                        {
                            ToastShow.show(context,"用户名已存在");
                        }
                        else
                            {
                                if (add(mDatabase,name,password)){
                                    ToastShow.show(context,"注册成功，欢迎您的加入");
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    RegisterActivity.this.finish();
                                }
                                else
                                    {
                                        ToastShow.show(context,"注册失败，请重新填写信息");
                                    }
                            }
                    }
            }
        });

    }

    public static boolean find(SQLiteDatabase mDatabase,String name) {

        Cursor cursor = mDatabase.rawQuery("select username from user where username = ?", new String[]{name});
        if (cursor.moveToNext()) {
            mDatabase.close();
            return true;
        }
        mDatabase.close();
        return false;
    }

    public static boolean add(SQLiteDatabase mDatabase,String name,String password){
        ContentValues values = new ContentValues();
        values.put("username",name);
        values.put("password",password);
        long rowID=mDatabase.insert("user",null,values);
        if(rowID!=-1)
        {
            mDatabase.close();
            return true;
        }
        mDatabase.close();
        return false;
    }
}
