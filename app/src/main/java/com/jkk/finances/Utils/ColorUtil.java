package com.jkk.finances.Utils;

import android.graphics.Color;

public class ColorUtil {
    public static final int COLOR_BLUE = Color.parseColor("#33B5E5");
    public static final int COLOR_VIOLET = Color.parseColor("#AA66CC");
    public static final int COLOR_GREEN = Color.parseColor("#99CC00");
    public static final int COLOR_ORANGE = Color.parseColor("#FFBB33");
    public static final int COLOR_RED = Color.parseColor("#DC143C");
    public static final int COLOR1 = Color.parseColor("#6495ED");
    public static final int COLOR2 = Color.parseColor("#008B8B");
    public static final int COLOR3 = Color.parseColor("#008000");
    public static final int COLOR4 = Color.parseColor("#808000");
    public static final int COLOR5 = Color.parseColor("#FF8C00");
    public static final int COLOR6 = Color.parseColor("#FF4500");
    public static final int COLOR7 = Color.parseColor("#66CDAA");
    public static final int[] COLORS;
    private static int COLOR_INDEX;
    public ColorUtil() {
    }
    public static final int nextColor() {
        if (COLOR_INDEX >= COLORS.length) {
            COLOR_INDEX = 0;
        }
        return COLORS[COLOR_INDEX++];
    }
    static {
        COLORS = new int[]{COLOR_BLUE, COLOR_VIOLET, COLOR_GREEN, COLOR_ORANGE, COLOR_RED,COLOR1,COLOR2,COLOR3,COLOR4,COLOR5,COLOR6,COLOR7};
        COLOR_INDEX = 0;
    }

}
