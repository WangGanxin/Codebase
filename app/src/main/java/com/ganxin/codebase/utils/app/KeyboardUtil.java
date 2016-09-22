package com.ganxin.codebase.utils.app;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Description : 键盘显示工具类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/22 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class KeyboardUtil {

    /**
     * @param context
     * @param editText
     */
    public static void openKeyboard(Context context, View editText) {
        try {
            if (context == null) {
                return;
            }

            if (editText != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);

                //如果上面的无效，试试这个
                //imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
                //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param context
     * @param view
     */
    public static void hideKeyboard(Context context, View view) {
        try {
            if (context == null) {
                return;
            }

            if (view != null && view.getWindowToken() != null) {

                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        try {
            if (activity == null) {
                return;
            }

            View view = activity.getWindow().peekDecorView();

            if (view != null && view.getWindowToken() != null) {

                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
