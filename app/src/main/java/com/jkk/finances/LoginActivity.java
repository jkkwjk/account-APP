package com.jkk.finances;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jkk.finances.Activity.MainActivity;
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
                        DBHelper dbHelper = new DBHelper(context);
                        // TODO: 2019/6/7 数据库验证
                        Intent intent = new Intent();
                        intent.setClass(context, MainActivity.class);
                        startActivityForResult(intent,1);
                        finish();
                    }
                }
                    break;
                case R.id.button_register:
                    // TODO: 2019/6/7 跳转注册
                    break;

            }
        }
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
