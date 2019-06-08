package com.jkk.finances;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jkk.finances.Activity.MainActivity;
import com.jkk.finances.Activity.RegisterActivity;
import com.jkk.finances.Model.User;
import com.jkk.finances.Tools.DBHelper;
import com.jkk.finances.Utils.ToastShow;

public class LoginActivity extends AppCompatActivity {
    private Context context;
    private SQLiteDatabase mDatabase;
    private Button button_login;
    private Button button_register;

    private EditText editText_user;
    private EditText editText_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.context = this;
        viewInit();
        setListener();

        mDatabase = new DBHelper(context).getReadableDatabase();
    }

    private void viewInit(){
        button_login = findViewById(R.id.button_login);
        button_register = findViewById(R.id.button_register);

        editText_user = findViewById(R.id.editText_login_name);
        editText_pwd = findViewById(R.id.editText_login_pwd);
    }

    private void setListener(){

        OnClick onClick = new OnClick();
        button_login.setOnClickListener(onClick);
        button_register.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button_login:
                {
                    String userName = editText_user.getText().toString();
                    String pwd = editText_pwd.getText().toString();
                    if (canSubmit(userName,pwd)){
                        Cursor cursor = mDatabase.rawQuery("select username from user where username=? and password=?",
                                new String[]{userName,DBHelper.getStringMD5(pwd)});
                        cursor.moveToFirst();
                        if (!cursor.isAfterLast()){
                            cursor.close();
                            successLogin(new User(userName));
                        }else {
                            successLogin(new User("1")); //test
                            cursor.close();
                            ToastShow.show(context,"账号或密码错误");
                        }
                    }
                }
                    break;
                case R.id.button_register:
                    Intent intent = new Intent();
                    intent.setClass(context, RegisterActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }

    private void successLogin(User user){
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
        finish();
    }

    private boolean canSubmit(String user, String pwd){
        if (user.equals("") || pwd.equals("")){
            ToastShow.show(context,"账号或密码不能为空");
            return false;
        }else {
            return true;
        }
    }
}
