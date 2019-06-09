package com.jkk.finances;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

public class EditViewWithPic extends android.support.v7.widget.AppCompatEditText {
    public EditViewWithPic(Context context) {
        super(context);
    }

    public EditViewWithPic(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditViewWithPic(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void insertDrawable(int id) {
        final SpannableString ss = new SpannableString("easy");
        //得到drawable对象，即所要插入的图片
        Drawable d = getResources().getDrawable(id);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        //用这个drawable对象代替字符串easy
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        //包括0但是不包括"easy".length()即：4。[0,4)。值得注意的是当我们复制这个图片的时候，实际是复制了"easy"这个字符串。
        ss.setSpan(span, 0, "easy".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        append(ss);
    }
    public void insertDrawable(Bitmap b) {
        final SpannableString ss = new SpannableString("//|||}}");
        //得到drawable对象，即所要插入的图片
        ImageSpan span = new ImageSpan(b);
        //包括0但是不包括"easy".length()即：4。[0,4)。值得注意的是当我们复制这个图片的时候，实际是复制了"easy"这个字符串。
        ss.setSpan(span, 0, "//|||}}".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        append(ss);
    }

}