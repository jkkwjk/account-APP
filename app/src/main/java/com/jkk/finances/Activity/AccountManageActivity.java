package com.jkk.finances.Activity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jkk.finances.EditViewWithPic;
import com.jkk.finances.Model.AccountInfo;
import com.jkk.finances.Model.User;
import com.jkk.finances.R;
import com.jkk.finances.TInputConnection;
import com.jkk.finances.Utils.ToastShow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
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
    private RadioGroup radioGroup1;//type
    private RadioGroup radioGroup2;//getout
    private ArrayList<String> mArrayList = new ArrayList<>();

    private String timeStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);

        this.context=this;

        mTextView=(TextView)findViewById(R.id.textView_account_text);
        edittime=(EditText)findViewById(R.id.button_account_time);
        edittime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击弹出时间选择
            }
        });
        Intent intent=getIntent();
        mAccountInfo = (AccountInfo) intent.getSerializableExtra("account");

        mEditViewWithPic = (EditViewWithPic) findViewById(R.id.edit_account_image);
        mEditViewWithPic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (after < count){
                    //删除
                    char ch = 5;
                    String str = String.valueOf(s);
                    str = str.substring(start,start+count);
                    int num = 0;
                    for (int i=0;i<start; ++i){
                        if (s.toString().charAt(i)==ch){
                            num++;
                        }
                    }
                    for (int i=0; i<str.length(); ++i){
                        if (str.charAt(i)==ch){
                            Log.d("jkk33", String.format("删除%s图片", num++));//num++为位置
                            mArrayList.remove(num++);
                        }
                    }
                }

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
        radioGroup1 =(RadioGroup) findViewById(R.id.RG_account_type);
        radioGroup2 =(RadioGroup) findViewById(R.id.RG_account_getout);
        startchange();
        //editname=(EditText)findViewById(R.id.button_account_time);

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
                finish();
            }
        });
        buttonsubmit=(Button)findViewById(R.id.button_account_submit);
        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usename=editname.getText().toString();
                String number=editmoney.getText().toString();
                Editable editable=mEditViewWithPic.getText();
                if(checknumber(number)) {
                    float money=Float.parseFloat(number);

                    String type =((RadioButton)findViewById(radioGroup1.getCheckedRadioButtonId())).getText().toString();
                    String getout =((RadioButton)findViewById(radioGroup2.getCheckedRadioButtonId())).getText().toString();
                    if(getout.equals("支出")){
                        money=-money;
                    }
                    //String time=timeStream;
                    String time = String.valueOf((new Date()).getTime()/1000);
                    if (mAccountInfo == null){
                        mAccountInfo=new AccountInfo();
                    }
                    mAccountInfo.setMoney(money);
                    mAccountInfo.setUseName(usename);
                    mAccountInfo.setType(type);
                    mAccountInfo.setTime(time);
                    if(!(editable==null||editable.toString().equals(""))){
                        mAccountInfo.setStr(editable.toString());
                        if(mArrayList.size()==0){
                            mAccountInfo.setMore(0);
                        }else{
                            mAccountInfo.setUrl(JSON.toJSONString(mArrayList));
                            if(editable.length()>mArrayList.size()){
                                mAccountInfo.setMore(2);
                            }else {
                                mAccountInfo.setMore(1);
                            }
                        }
                    }
                    // 添加数据
                    Intent intent = new Intent();
                    intent.putExtra("account",mAccountInfo);
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    ToastShow.show(context,"请正确填写金额");
                }
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
                mArrayList.add(uri.getScheme()+"://"+uri.getEncodedAuthority()+uri.getEncodedPath());
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
    public void startchange(){
        if(mAccountInfo!=null){
            mTextView.setText("在这里，修改你的账单");
            editname.setText(mAccountInfo.getUseName());
            editmoney.setText(mAccountInfo.getMoney().toString());
            String type=mAccountInfo.getType();
            if(type.equals("支付宝")){
                radioGroup1.check(R.id.RB_account_1);
            }else{
                if(type.equals("微信")){
                    radioGroup1.check(R.id.RB_account_2);
                }else{
                    if(type.equals("银行卡")){
                        radioGroup1.check(R.id.RB_account_3);
                    }else{
                        radioGroup1.check(R.id.RB_account_4);
                    }
                }
            }
            if(mAccountInfo.getMoney()<0){
                radioGroup2.check(R.id.RB_account_5);
            }else{
                radioGroup2.check(R.id.RB_account_6);
            }
            JSONArray jsonArray = (JSONArray) JSON.parse(mAccountInfo.getUrl());
            for (Object o : jsonArray) {
                mArrayList.add(o.toString());
            }
            if(mAccountInfo.getMore()!=null){
                String str=mAccountInfo.getStr();
                int number=0;
                for(int i=0;i<str.length();i++){
                    if(str.charAt(i)==5){
                        ContentResolver cr = this.getContentResolver();
                        try {
                            Uri url = Uri.parse(mArrayList.get(number));
                            number=number+1;
                            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(url));
                            resizeBitmap(bitmap);
                            mEditViewWithPic.append("\n");
                            mEditViewWithPic.insertDrawable(bitmap);
                        }catch (FileNotFoundException e){
                            Log.e("Exception", e.getMessage(),e);
                        }
                    }else{
                        mEditViewWithPic.append(String.valueOf(str.charAt(i)));
                    }
                }
            }
            //得到时间并显示
        }else{
            mTextView.setText("在这里，添加你的账单");
        }
    }
    public Uri getUriFromFilePath(Context ctx, File filePath) {
        Uri requirdUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            requirdUri = FileProvider.getUriForFile(ctx,
                    ctx.getApplicationContext().getPackageName() + PROVIDER_FILE_EXTENSION,
                    filePath);
        } else {
            requirdUri = Uri.fromFile(filePath);
        }

        return requirdUri;
    }

}
