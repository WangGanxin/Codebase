package com.ganxin.codebase.utils.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * Description : 安装包帮助类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class PackageHelper {

    private PackageHelper() {}

    /**
     * 获取包管理器
     *
     * @param context 环境
     * @return 包管理器
     */
    public static PackageManager getPackageManager(Context context) {
        return context.getPackageManager();
    }

    /**
     * 获取版本号
     *
     * @param context 当前环境
     * @return 当前版本号
     */
    public static String getVersionName(Context context) {
        String version = "unKnown";
        try {
            version = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            version = "notFound";
        }
        return version;
    }

    /**
     * 获取版本代码
     *
     * @param context 当前环境
     * @return 当前版本代码
     */
    public static int getVersionCode(Context context) {
        int versionCode = -1;
        try {
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            versionCode = 0;
        }
        return versionCode;
    }

    /**
     * 获取指定键的Meta值
     *
     * @param context 环境
     * @param metaKey 键
     * @return 指定键的Meta值
     */
    public static String getMetaValue(Context context, String metaKey) {
        String apiKey = "unKnown";
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (ai != null && ai.metaData != null) {
                apiKey = ai.metaData.getString(metaKey);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            apiKey = "notFound";
        }
        return apiKey;
    }

    /**
     * 验证应用是否安装在手机中
     *
     * @param context
     * @param packagename 应用包名
     * @return
     */
    public static boolean isPackageInfo(Context context, String packagename) {
        PackageInfo packageInfo;

        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    packagename, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
}
