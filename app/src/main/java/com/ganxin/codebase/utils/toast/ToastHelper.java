package com.ganxin.codebase.utils.toast;


import android.content.Context;
import android.widget.Toast;

/**
 * Description : 吐司工具类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class ToastHelper {

    private static final Object SYNC_LOCK = new Object();

    private static Toast mToast;

    public static Toast getToastInstance(Context context) {
        if (mToast == null) {
            synchronized (SYNC_LOCK) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                }
            }
        }
        return mToast;
    }

    /**
     * 展示吐司
     *
     * @param context 环境
     * @param text    内容
     */
    public static void showToast(Context context, String text) {
        if (context != null && text != null) {
            getToastInstance(context);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(text);
            mToast.show();
        }
    }

    /**
     * @param context 环境
     * @param text    内容
     * @param isLong  是否长时间展示
     */
    public static void showToast(Context context, String text,
                                 boolean isLong) {
        if (context != null && text != null) {
            if (isLong) {
                getToastInstance(context);
                mToast.setDuration(Toast.LENGTH_LONG);
                mToast.setText(text);
                mToast.show();
            } else {
                showToast(context, text);
            }
        }

    }

    /**
     * 展示新吐司
     *
     * @param context 环境
     * @param text    内容
     */
    public static void showNewToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 展示新吐司
     *
     * @param context 环境
     * @param text    内容
     * @param isLong  是否长时间展示
     */
    public static void showNewToast(Context context, String text,
                                    boolean isLong) {
        if (isLong) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        } else {
            showNewToast(context, text);
        }
    }
}
