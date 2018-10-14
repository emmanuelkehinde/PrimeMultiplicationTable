package com.emmanuelkehinde.primemultiplicationtable.util;

import android.content.Context;
import android.widget.Toast;

public class DisplayUtil {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
