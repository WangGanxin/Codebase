package com.ganxin.codebase.utils.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.ganxin.codebase.app.CodebaseApplication;
import com.ganxin.codebase.app.Constants;

/**
 * Description : SharPreference工具类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/12/16 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class SharedPreferencesAccessor {
    /**
     * 写入布尔型数据
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void writeBoolean(Context context, String key, Boolean value) {
        SharedPreferences preference = context.getSharedPreferences(
                Constants.SHARPREFER_FILENAME, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 写入字符串数据
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void writeString(Context context, String key, String value) {
        SharedPreferences preference = context.getSharedPreferences(
                Constants.SHARPREFER_FILENAME, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 写入整型数据
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void writeInt(Context context, String key, int value) {
        SharedPreferences preference = context.getSharedPreferences(
                Constants.SHARPREFER_FILENAME, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 写入Long数据
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void writeLong(Context context, String key, int value) {
        SharedPreferences preference = context.getSharedPreferences(
                Constants.SHARPREFER_FILENAME, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 写入Float数据
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void writeFloat(Context context, String key, int value) {
        SharedPreferences preference = context.getSharedPreferences(
                Constants.SHARPREFER_FILENAME, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * 读取布尔型数据
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认的值，如果不存在则以此值返回
     * @return boolean 布尔数据
     */
    public static boolean readBoolean(Context context, String key,
                                      boolean defValue) {
        SharedPreferences preference = context.getSharedPreferences(
                Constants.SHARPREFER_FILENAME, Context.MODE_PRIVATE);
        return preference.getBoolean(key, defValue);
    }

    /**
     * 读取字符串数据
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 值
     * @return
     */
    public static String readString(Context context, String key, String defValue) {
        SharedPreferences preference = context.getSharedPreferences(
                Constants.SHARPREFER_FILENAME, Context.MODE_PRIVATE);
        return preference.getString(key, defValue);
    }


    /**
     * 读取整型数据
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return int 整型数据
     */
    public static int readInt(Context context, String key, int defValue) {
        SharedPreferences preference = context.getSharedPreferences(
                Constants.SHARPREFER_FILENAME, Context.MODE_PRIVATE);
        return preference.getInt(key, defValue);
    }

    /**
     * 读取Long数据
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return
     */
    public static long readLong(Context context, String key, int defValue) {
        SharedPreferences preference = context.getSharedPreferences(
                Constants.SHARPREFER_FILENAME, Context.MODE_PRIVATE);
        return preference.getLong(key, defValue);
    }

    /**
     * 读取Float数据
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return
     */
    public static float readFloat(Context context, String key, int defValue) {
        SharedPreferences preference = context.getSharedPreferences(
                Constants.SHARPREFER_FILENAME, Context.MODE_PRIVATE);
        return preference.getFloat(key, defValue);
    }

    //-----------------------------added 2016.12.16---------------------
    //改造该工具类，使其具有更好的安全性与扩展性

    /**
     * Description : 内部写入线程  <br/>
     * author : WangGanxin <br/>
     * date : 2016/12/16 <br/>
     * email : mail@wangganxin.me <br/>
     */
    private static class SetConfigRunnable implements Runnable {
        private Context context;
        private String configName;
        private String configKey;
        private Object configValue;
        private OnSharedataCommitListener commit;

        public SetConfigRunnable(Context context, String configName, String configKey, Object configValue, OnSharedataCommitListener commit) {
            this.configKey = configKey;
            this.configName = configName;
            this.configValue = configValue;
            this.context = context;
            this.commit = commit;
        }

        @Override
        public void run() {
            if (context == null)
                context = CodebaseApplication.getAppContext();
            if (TextUtils.isEmpty(configName) || TextUtils.isEmpty(configKey)) {
                return;
            }

            Editor sharedata = context.getSharedPreferences(configName, 0).edit();
            if (configValue instanceof Integer) {
                sharedata.putInt(configKey, (Integer) configValue);
            } else if (configValue instanceof String) {
                sharedata.putString(configKey, (String) configValue);
            } else if (configValue instanceof Boolean) {
                sharedata.putBoolean(configKey, (Boolean) configValue);
            } else if (configValue instanceof Float) {
                sharedata.putFloat(configKey, (Float) configValue);
            } else if (configValue instanceof Long) {
                sharedata.putLong(configKey, (Long) configValue);
            }

            try {
                sharedata.commit();
                if (this.commit != null) {
                    this.commit.onSharedataCommit(configKey, configValue);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void setConfig(Context context, String configName, String configKey, Object configValue) {
        setConfig(context, configName, configKey, configValue, null);
    }

    public static void setConfig(Context context, String configName, String configKey, Object configValue, OnSharedataCommitListener commit) {
        new Thread(new SetConfigRunnable(context, configName, configKey, configValue, commit)).start();
    }

    public static void removeConfig(Context context, String cfgName, String cfgKey) {
        if (context == null) {
            context = CodebaseApplication.getAppContext();
        }
        try {
            SharedPreferences shareData = context.getSharedPreferences(cfgName, 0);
            shareData.edit().remove(cfgKey);
            shareData.edit().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getIntConfig(Context context, String cfgName, String cfgKey, int defVal) {
        if (context == null)
            context = CodebaseApplication.getAppContext();
        SharedPreferences shareData = context.getSharedPreferences(cfgName, Context.MODE_PRIVATE);
        return shareData.getInt(cfgKey, defVal);
    }

    public static boolean getBooleanConfig(Context context, String cfgName, String cfgKey, boolean defVal) {
        if (context == null)
            context = CodebaseApplication.getAppContext();
        SharedPreferences shareData = context.getSharedPreferences(cfgName, Context.MODE_PRIVATE);
        return shareData.getBoolean(cfgKey, defVal);
    }

    public static long getLongConfig(Context context, String cfgName, String cfgKey, Long defVal) {
        if (context == null)
            context = CodebaseApplication.getAppContext();
        SharedPreferences shareData = context.getSharedPreferences(cfgName, Context.MODE_PRIVATE);
        return shareData.getLong(cfgKey, defVal);
    }

    public static float getFloatConfig(Context context, String cfgName, String cfgKey, Long defVal) {
        if (context == null)
            context = CodebaseApplication.getAppContext();
        SharedPreferences shareData = context.getSharedPreferences(cfgName, Context.MODE_PRIVATE);
        return shareData.getFloat(cfgKey, defVal);
    }

    public static String getStringConfig(Context context, String cfgName, String cfgKey, String defVal) {
        if (context == null)
            context = CodebaseApplication.getAppContext();
        SharedPreferences shareData = context.getSharedPreferences(cfgName, Context.MODE_PRIVATE);
        return shareData.getString(cfgKey, defVal);
    }
}
