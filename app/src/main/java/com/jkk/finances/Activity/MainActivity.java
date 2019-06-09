package com.jkk.finances.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import com.jkk.finances.Fragment.AccountAllFragment;
import com.jkk.finances.Fragment.AccountCountFragment;
import com.jkk.finances.Model.AccountInfo;
import com.jkk.finances.R;
import com.jkk.finances.Utils.ToastShow;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Context context;

    private Long exitTime = 0L;

    private FragmentManager fm;
    private TextView mTextMessage;
    private BottomNavigationView navigation;

    private ArrayList<AccountInfo> accountInfos = AccountInfo.get();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_all_account:
                {
                    Fragment f = new AccountAllFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("account",accountInfos);
                    f.setArguments(bundle);
                    fm.beginTransaction().replace(R.id.view_main_fragment_container,f).commitAllowingStateLoss();
                }
                    return true;
                case R.id.navigation_add_account:
                    Intent intent = new Intent(MainActivity.this, AccountManageActivity.class);
                    startActivityForResult(intent,0);
                    return true;
                case R.id.navigation_count_account:
                {
                    Fragment f = new AccountCountFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("account",accountInfos);
                    f.setArguments(bundle);
                    fm.beginTransaction().replace(R.id.view_main_fragment_container,f).commitAllowingStateLoss();
                }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;

        viewInit();
        setListener();

        navigation.findViewById(R.id.navigation_all_account).performClick();
    }

    private void viewInit(){
        fm = getSupportFragmentManager();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
    }
    private void setListener(){
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 0:
            {
                if (resultCode==RESULT_OK){
                    // TODO: 2019/6/8 加入数据 
                }
            }
                break;
            case 1:
            {

            }
                break;
        }

        navigation.findViewById(R.id.navigation_all_account).performClick();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                ToastShow.show(context,"再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
