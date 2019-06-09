package com.jkk.finances.Activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.jkk.finances.R;

public class AccountManageActivity extends AppCompatActivity {
    private Button buttoninster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
        buttoninster=(Button)findViewById(R.id.button_account_image);

    }

}
