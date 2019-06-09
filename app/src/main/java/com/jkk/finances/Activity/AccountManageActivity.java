package com.jkk.finances.Activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jkk.finances.EditViewWithPic;
import com.jkk.finances.Model.AccountInfo;
import com.jkk.finances.Model.User;
import com.jkk.finances.R;
import com.jkk.finances.TInputConnection;
import com.jkk.finances.Utils.ToastShow;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountManageActivity extends AppCompatActivity {
    private TextView mTextView;
    private Context context;
    private Button buttoninster;
    private EditViewWithPic mEditViewWithPic;
    private EditText editname;
    private EditText editmoney;
    private EditText edittime;
    private Button buttonback;
    private Button buttonsubmit;
    private AccountInfo mAccountInfo;
    private ArrayList<String> mArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);

        this.context=this;
        mTextView=(TextView)findViewById(R.id.textView_account_text);
        Intent intent=getIntent();
        mAccountInfo = (AccountInfo) intent.getSerializableExtra("account");
        if(mAccountInfo==null){
            mTextView.setText("在这里，添加你的账单");
        }else{
            mTextView.setText("在这里，修改你的账单");
        }
        mEditViewWithPic = (EditViewWithPic) findViewById(R.id.edit_account_image);
        mEditViewWithPic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editname=(EditText)findViewById(R.id.edit_account_name);
        editmoney=(EditText)findViewById(R.id.edit_account_money);
        buttoninster=(Button)findViewById(R.id.button_account_image);
        editname=(EditText)findViewById(R.id.button_account_time);
//        edittime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        buttoninster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
        buttonback=(Button)findViewById(R.id.button_account_back);
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountManageActivity.this, MainActivity.class);
                startActivity(intent);
                AccountManageActivity.this.finish();
            }
        });
        buttonsubmit=(Button)findViewById(R.id.button_account_submit);
        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usename=editname.getText().toString();
                String number=editmoney.getText().toString();
                if(checknumber(number)) {
                    float money=Float.parseFloat(number);
                    RadioGroup radioGroup1 =(RadioGroup) findViewById(R.id.RG_account_type);
                    String type =((RadioButton)findViewById(radioGroup1.getCheckedRadioButtonId())).getText().toString();
                    RadioGroup radioGroup2 =(RadioGroup) findViewById(R.id.RG_account_getout);
                    String getout =((RadioButton)findViewById(radioGroup2.getCheckedRadioButtonId())).getText().toString();
                    String time=edittime.getText().toString();
                    //String
                    if (mAccountInfo == null){
                        mAccountInfo=new AccountInfo();
                    }
                }else{
                    ToastShow.show(context,"请正确填写金额");
                }
//                ToastShow.show(context,"添加一个账单成功");
//                Intent intent = new Intent(AccountManageActivity.this, MainActivity.class);
//                intent.putExtra("user",);
//                startActivity(intent);
//                AccountManageActivity.this.finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String img_url = uri.getPath();//这是本机的图片路径
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                resizeBitmap(bitmap);
                mEditViewWithPic.append("\n");
                mEditViewWithPic.insertDrawable(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private  Bitmap resizeBitmap(Bitmap bitmap){
        double editWid = mEditViewWithPic.getWidth();
        double bitWid = bitmap.getWidth();
        if (editWid>bitWid){
            return bitmap;
        }else {
            double rate = editWid*0.75/bitWid;
            bitmap.setHeight((int)(bitmap.getHeight()*rate));
            bitmap.setWidth((int)(bitmap.getWidth()*rate));
        }
        return  bitmap;
    }
    public static boolean checknumber(String str)
    {
        boolean ret = false;
        try{
                Float money=Float.parseFloat(str);
                if(!(money<1e-6&&money> -1e-6)){
                    ret = true;
                }

        }catch(NumberFormatException e){

        }finally {
            return ret;
        }
    }
}
