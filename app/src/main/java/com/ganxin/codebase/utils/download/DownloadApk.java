package com.ganxin.codebase.utils.download;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;

import java.io.File;

/**
 * 用法 : Intent downloadSevice = new Intent( getActivity(),
 * DownloadApk.class); downloadSevice.putExtra("Url",
 * result.getDownloadAndroid()); getActivity().startService(
 * downloadSevice);  <br/>
 * <p/>
 * Description : 下载APK  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class DownloadApk extends Service {

    /**
     * 下载的文件名
     */
    private static final String APK_NAME = "XXX.apk";
    ;
    /**
     * 下载更新编号
     */
    private long downloadReference;
    /**
     * 下载时间接受广播
     */
    private DownloadUpdataBroadcastReceiver mDownloadUpdataBroadcastReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerBroadcastReceiver(); // 注册广播
        downloadReference = DownloadUtil
                .download(getApplicationContext(),
                        intent.getStringExtra("Url"), APK_NAME); // 获取下载编号
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 注册更新消息接收广播
     */
    private void registerBroadcastReceiver() {
        IntentFilter filter = new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        mDownloadUpdataBroadcastReceiver = new DownloadUpdataBroadcastReceiver();
        getApplicationContext().registerReceiver(
                mDownloadUpdataBroadcastReceiver, filter);
    }

    /**
     * 下载更新广播类
     */
    private class DownloadUpdataBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long reference = intent.getLongExtra(
                    DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
            /*
			 * 判断是否是我的下载
			 */
            if (downloadReference == reference) {
                stopSelf(); // 停止服务
                installAPK(APK_NAME); // 安装APK
            }
        }

        /**
         * 安装APK
         *
         * @param fileName 文件名
         */
        private void installAPK(String fileName) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse(Uri.fromFile(
                    Environment.getExternalStorageDirectory()).toString()
                            + File.separator + fileName),
                    "application/vnd.android.package-archive");
            getApplicationContext().startActivity(intent);
        }
    }
}
