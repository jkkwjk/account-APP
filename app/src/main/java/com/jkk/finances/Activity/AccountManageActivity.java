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
import android.widget.Toast;

import com.jkk.finances.EditViewWithPic;
import com.jkk.finances.R;
import com.jkk.finances.TInputConnection;
import com.jkk.finances.Utils.ToastShow;

import java.io.FileNotFoundException;

public class AccountManageActivity extends AppCompatActivity {
    private Button buttoninster;
    private  EditViewWithPic mEditViewWithPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);

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
//                Editable editable = mEditViewWithPic.getText();
//
//                if(editable.length() <6){
//                    return ;
//                }
//
//                int end = Math.max(0,mEditViewWithPic.getSelectionStart() - 1);
//                int start=end-6;
//                if(start>=0){
//                    if(editable.subSequence(start,end).toString().equals("////cn")){
//                        //editable.delete(start,end);
//                    }else {
//                        return;
//                    }
//                 }else{
//                    return;
//                }

            }
        });
        buttoninster=(Button)findViewById(R.id.button_account_image);
        buttoninster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);
//                e.insertDrawable(R.drawable.checkbox_select);
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
            //    resizeBitmap(bitmap);
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
}
