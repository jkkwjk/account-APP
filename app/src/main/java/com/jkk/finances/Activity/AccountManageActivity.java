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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.jkk.finances.EditViewWithPic;
import com.jkk.finances.R;
import com.jkk.finances.Utils.ToastShow;

import java.io.FileNotFoundException;

public class AccountManageActivity extends AppCompatActivity {
    private Button buttoninster;
    EditViewWithPic mEditViewWithPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);

        mEditViewWithPic = (EditViewWithPic) findViewById(R.id.edit_account_descripe);
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
        mEditViewWithPic.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.d("back",String.valueOf(keyCode));
//                if (event.getAction()==KeyEvent.ACTION_UP) {
//                    Log.d("back","hello");
//                    //dosomething
//                    //有需要时可以添加以下代码来隐藏软键盘
//                   // InputMethodManager imm = (InputMethodManager) getSystemService(SearchActivity.INPUT_METHOD_SERVICE);
//                   // imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
                return false;
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
