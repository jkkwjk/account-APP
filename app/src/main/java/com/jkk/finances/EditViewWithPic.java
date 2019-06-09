package com.jkk.finances;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.TextView;
import android.util.AttributeSet;


public class EditViewWithPic extends android.support.v7.widget.AppCompatEditText {
    public TInputConnection inputConnection;
    public EditViewWithPic(Context context) {
        super(context);
        init();
    }

    public EditViewWithPic(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditViewWithPic(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        inputConnection = new TInputConnection(null,true);
    }
    public void insertDrawable(Bitmap b) {
        char c = 5;
        String s = String.valueOf(c);
        final SpannableString ss = new SpannableString(s);
        //得到drawable对象，即所要插入的图片
        ImageSpan span = new ImageSpan(b);
        //包括0但是不包括"easy".length()即：4。[0,4)。值得注意的是当我们复制这个图片的时候，实际是复制了"easy"这个字符串。
        ss.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        append(ss);
    }
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        inputConnection.setTarget(super.onCreateInputConnection(outAttrs));
        return inputConnection;
    }

    public void setBackSpaceLisetener(TInputConnection.BackspaceListener backSpaceLisetener){
        inputConnection.setBackspaceListener(backSpaceLisetener);
    }
}