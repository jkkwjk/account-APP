package com.jkk.finances.Utils;

import android.content.Context;
import android.widget.Toast;
/**
 * Android弹出底部信息框
 *
 * @author jkkwjk
 * @version : 2019/04/27 下午10:46
 */
public class ToastShow {
    public final static void show(Context context,String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
