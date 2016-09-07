package com.ganxin.codebase.utils.download;


import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

/**
 * Description : 下载工具类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class DownloadUtil {

    /**
     * 获取下载管理器
     *
     * @param context 当前环境
     * @return 下载管理器
     */
    public static final DownloadManager getDownloadManager(Context context) {
        Object obj = context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (obj instanceof DownloadManager) {
            return (DownloadManager) obj;
        }
        return null;
    }

    /**
     * 下载目标地址数据至SD卡默认目录
     *
     * @param context 当前环境
     * @param url     地址
     * @return 下载编号
     */
    public static final long download(Context context, String url,
                                      String fileName) {
        DownloadManager downloadManager = getDownloadManager(context);
        if (downloadManager != null) {
            Uri uri = Uri.parse(url);
            File file = new File(Environment.getExternalStorageDirectory()
                    .getPath() + File.separator + fileName);
            /*
			 * 判断文件是否存在，如果存在删除源文件
			 */
            if (file.exists()) {
                file.delete();
            }
            Request request = new Request(uri);
            request.setTitle("正在下载……");
            request.setDestinationUri(Uri.parse(Uri.fromFile(
                    Environment.getExternalStorageDirectory()).toString()
                    + File.separator + fileName)); // 设置下载后文件存放的位置
            Toast.makeText(context, "开始下载更新包，请稍后……", Toast.LENGTH_LONG).show();
            return downloadManager.enqueue(request);
        }
        return 0;
    }

}
