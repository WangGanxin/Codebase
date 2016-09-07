package com.ganxin.codebase.utils.bitmap;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.os.Environment;
import android.text.TextUtils;

/**
 * Description : 位图工具类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class BitmapUtil {


    /**
     * 保存位图对象至文件
     *
     * @param context 环境
     * @param bitmap  位图对象
     * @param name    保存名称
     * @throws Exception I/O等可能发生的异常（如磁盘空间不足）
     */
    public static void saveBitmapToFile(final Context context,
                                        final Bitmap bitmap, final String name) {
        new Thread() {
            public void run() {
                try {
                    File file = new File(context.getCacheDir(), name);
                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    FileOutputStream fileOutputStream = new FileOutputStream(
                            file);
                    bitmap.compress(CompressFormat.PNG, 10,
                            fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();
    }

    /**
     * 保存位图对象至指定目录下的文件
     *
     * @param context 环境
     * @param bitmap  位图对象
     * @param name    保存名称
     * @param path    保存路径
     * @throws Exception I/O等可能发生的异常（如磁盘空间不足）
     */
    public static void saveBitmapToFile(Context context, Bitmap bitmap,
                                        String name, String path) throws Exception {
        File file = new File(Environment.getExternalStorageDirectory()
                + File.separator + path);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(Environment.getExternalStorageDirectory()
                + File.separator + path, name);
        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        bitmap.compress(CompressFormat.PNG, 30, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    /**
     * 从文件获取位图对象
     *
     * @param context 环境
     * @param name    文件名称
     * @return 位图对象
     */
    public static Bitmap getBitmapFromFile(Context context, String name) {
        File file = new File(context.getFilesDir(), name);
        if (!file.exists()) {
            return null;
        }
        return BitmapFactory.decodeFile(file.getPath());

    }

    /**
     * 获取圆角位图对象
     *
     * @param bitmap 需要变为圆角的位图对象
     * @param pixels 像素值，当等于位图宽的一半时会获得圆形位图
     * @return 圆角位图对象
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap getShadowBitmap(Bitmap bitmap) {
        Canvas canvas = new Canvas(bitmap);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        Rect rect1 = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.reset();

        canvas.drawBitmap(bitmap, rect, rect1, paint);

        return bitmap;
    }

    /**
     * 缩放位图至壁纸
     *
     * @param bitmap       位图对象
     * @param screenWidth  屏幕宽
     * @param screenHeight 屏幕高
     * @return 位图对象
     */
    public static Bitmap scaleBitmapToWallpaper(Bitmap bitmap, int screenWidth,
                                                int screenHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) screenWidth) / width;
        float scaleHeight = ((float) screenHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * 将Drawable转化为Bitmap
     *
     * @param drawable {@link Drawable}
     * @return {@link Bitmap}
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = HSBitmapUtil.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 将Bitmap转为Drawable
     *
     * @param bitmap
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        @SuppressWarnings("deprecation")
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        return (Drawable) bitmapDrawable;
    }

    /**
     * 获得圆角图片的方法
     *
     * @param bitmap  源图片
     * @param roundPx 圆角半径
     * @return 新图片
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        if (bitmap == null) {
            return null;
        }
        Bitmap output = HSBitmapUtil.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 获得圆形的方法
     *
     * @param bitmap  源图片
     * @param roundPx 圆角半径
     * @return 新图片
     */
    public static Bitmap getRoundedBitmap(Bitmap bitmap, float roundPx) {
        if (bitmap == null) {
            return null;
        }
        Bitmap output = HSBitmapUtil.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 获得带边框圆形的方法
     *
     * @param bitmap     源图片
     * @param roundPx    圆角半径
     * @param boderPx    背景边框宽度
     * @param boderColor 背景边框颜色
     * @return 新图片
     */
    public static Bitmap getRoundedBitmapWithBorder(Bitmap bitmap,
                                                    float roundPx, int boderPx, int boderColor) {
        Bitmap tmpBitmap = getRoundedBitmap(bitmap, roundPx);
        if (tmpBitmap == null) {
            return null;
        }

        Bitmap output = HSBitmapUtil.createBitmap(tmpBitmap.getWidth()
                        + boderPx * 2, tmpBitmap.getHeight() + boderPx * 2,
                Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint borderpaint = new Paint();
        final Rect borderrect = new Rect(0, 0, tmpBitmap.getWidth() + boderPx
                * 2, tmpBitmap.getHeight() + boderPx * 2);
        final RectF borderrectF = new RectF(borderrect);
        borderpaint.setAntiAlias(true);
        borderpaint.setColor(boderColor);
        canvas.drawOval(borderrectF, borderpaint);

        final Paint paint = new Paint();
        final Rect src = new Rect(0, 0, tmpBitmap.getWidth(),
                tmpBitmap.getHeight());
        final Rect dst = new Rect(boderPx, boderPx, tmpBitmap.getWidth()
                + boderPx, tmpBitmap.getHeight() + boderPx);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawBitmap(tmpBitmap, src, dst, paint);
        return output;
    }

    /**
     * 获得带倒影的图片方法
     *
     * @param bitmap 源图片
     * @return 带倒影图片
     */
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap reflectionImage = HSBitmapUtil.createBitmap(bitmap, 0,
                height / 2, width, height / 2, matrix, false);
        Bitmap bitmapWithReflection = HSBitmapUtil.createBitmap(width,
                (height + height / 2), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);
        return bitmapWithReflection;
    }

    /**
     * 把bitmap保存到SD卡上
     *
     * @param bitmap   源图片
     * @param savePath 保存路径
     * @param format   图片格式
     */
    public static boolean saveBitmap(Bitmap bitmap, String savePath,
                                     CompressFormat format) {
        if (bitmap == null || TextUtils.isEmpty(savePath)) {
            return false;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(savePath);
            bitmap.compress(format, 80, fos);
        } catch (FileNotFoundException e) {
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 读取
     *
     * @param filePath 文件路径
     * @return
     */
    public static Bitmap readBitmap(final String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        } else {
            return HSBitmapUtil.decodeFile(filePath);
        }
    }

    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 压缩图片大小
     *
     * @param bitmap
     * @return
     */
    public static Bitmap reduceImageSize(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 80, baos);
        byte[] bytes = baos.toByteArray();
        InputStream is;
        is = new ByteArrayInputStream(bytes);
        return HSBitmapUtil.decodeStream(is);
    }

    /**
     * 这个是对一个颜色算出灰阶
     *
     * @param r
     * @param g
     * @param b
     * @return 灰度
     */
    private static int RGB2Gray(int r, int g, int b) {
        int gray = 0;
        gray = (((b) * 117 + (g) * 601 + (r) * 306) >> 10);

        return gray;
    }

    /**
     * 判断是否使用亮色的文字
     *
     * @param
     * @return true, 使用亮色文字;flase,使用暗色文字.
     */
    public static boolean IsUseLightFont(int color) {

        boolean useLightFont = false;
        int gray = RGB2Gray(Color.red(color), Color.green(color),
                Color.blue(color));

        if (gray <= 150) {
            useLightFont = true;
        }

        return useLightFont;
    }

    /**
     * 裁剪图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap cropBitmap(Bitmap bitmap, int width, int height) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        Bitmap newBitmap = HSBitmapUtil.createBitmap(bitmap, 0, 0, width,
                height, null, true);
        return newBitmap;
    }

    /**
     * 图片平铺
     */
    public static Bitmap bitmapCreateRepeater(int width, Bitmap src) {
        int count = (width + src.getWidth() - 1) / src.getWidth();
        Bitmap bitmap = Bitmap.createBitmap(width, src.getHeight(),
                Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for (int idx = 0; idx < count; ++idx) {
            canvas.drawBitmap(src, idx * src.getWidth(), 0, null);
        }
        return bitmap;
    }

    /**
     * 图片压缩
     *
     * @param path
     * @param fileDir
     * @param quality
     * @return
     */
    public static String encodeImage(String path, String fileDir, int quality) {
        try {
            // 压缩图片
            Options options = new Options();
            options.inJustDecodeBounds = true;
            Bitmap bgimage = BitmapFactory.decodeFile(path, options);
            int inSampleSize = 0;
            if (options.outHeight > options.outWidth) {
                inSampleSize = Math.round(options.outHeight / (float) 800);
            } else {
                inSampleSize = Math.round(options.outWidth / (float) 800);
            }
            if (options.outWidth / inSampleSize > 1024
                    || options.outHeight / inSampleSize > 1024) {
                inSampleSize = inSampleSize + 1;
            }
            if (inSampleSize <= 0)
                inSampleSize = 1;
            options.inSampleSize = inSampleSize;
            options.inJustDecodeBounds = false;
            bgimage = BitmapFactory.decodeFile(path, options);
            if (bgimage == null)
                return "";
            // 旋转图片
            int round = getExifOrientation(path);
            if (round != 0) {
                bgimage = rotation(bgimage, round);
            }
            // 获取这个图片的宽和高
            int width = bgimage.getWidth();
            int height = bgimage.getHeight();
            // 计算缩放率，新尺寸除原始尺寸
            float scalMin = ((float) 800) / (height > width ? height : width);// scaleWidth<scaleHeight?scaleWidth:scaleHeight;
            Bitmap bitmap = Bitmap.createScaledBitmap(bgimage,
                    (int) (width * scalMin), (int) (height * scalMin), false);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 得到输出流
            bitmap.compress(CompressFormat.JPEG, quality, baos);
            // 转输入流
            InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            bitmap.recycle();
            bgimage.recycle();
            File sendFilePath = new File(fileDir);
            writeToFile(sendFilePath, isBm);
            return sendFilePath.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取图片的选择角度
     *
     * @param filepath
     * @return
     */
    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognize a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }

    /**
     * 选择图片
     *
     * @param bitmap
     * @param round  旋转的角度
     */
    public static Bitmap rotation(Bitmap bitmap, int round) {
        Matrix matrix = new Matrix();
        matrix.postRotate(round);
        Bitmap tempBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return tempBitmap;
    }

    /**
     * 写入图片
     *
     * @param tagFile
     * @param in
     * @return
     */
    public static boolean writeToFile(File tagFile, InputStream in) {
        FileOutputStream fout = null;
        boolean success = true;
        try {
            fout = new FileOutputStream(tagFile);
            int len = -1;
            byte[] buff = new byte[4096];
            for (; (len = in.read(buff)) != -1; ) {
                fout.write(buff, 0, len);
            }
        } catch (Exception e) {
            success = false;
            e.printStackTrace();
        } finally {
            if (fout != null)
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
        return success;
    }

    /**
     * 图片压缩
     *
     * @param path
     * @param fileDir
     * @param msgId
     * @return
     */
    public static String pixEncodeImage(String path, String fileDir, int quality) {
        try {
            // 压缩图片
            Options options = new Options();
            options.inJustDecodeBounds = true;
            Bitmap bgimage = BitmapFactory.decodeFile(path, options);
            int inSampleSize = (int) (options.outHeight / (float) 500);
            if (inSampleSize <= 0)
                inSampleSize = 1;
            options.inSampleSize = inSampleSize;
            options.inJustDecodeBounds = false;
            bgimage = BitmapFactory.decodeFile(path, options);
            if (bgimage == null)
                return "";
            // 旋转图片
            int round = getExifOrientation(path);
            if (round != 0) {
                bgimage = rotation(bgimage, round);
            }
            // 获取这个图片的宽和高
            int width = bgimage.getWidth();
            int height = bgimage.getHeight();
            // 计算缩放率，新尺寸除原始尺寸
            float scalMin = ((float) 500) / (height > width ? height : width);// scaleWidth<scaleHeight?scaleWidth:scaleHeight;
            Bitmap bitmap = Bitmap.createScaledBitmap(bgimage,
                    (int) (width * scalMin), (int) (height * scalMin), false);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 得到输出流
            bitmap.compress(CompressFormat.PNG, quality, baos);
            // 转输入流
            InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            bitmap.recycle();
            bgimage.recycle();
            File sendFilePath = new File(fileDir);
            writeToFile(sendFilePath, isBm);
            return sendFilePath.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 图片处理
     *
     * @param image
     * @return
     */
    public static Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Options newOpts = new Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    private static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos  
            image.compress(CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10  
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;
    }

    /**
     * 根据图片地址把图片转成字节
     *
     * @param path
     * @return
     */
    public static byte[] decodeBitmap(String path) {
        Options opts = new Options();
        opts.inJustDecodeBounds = true;// 设置成了true,不占用内存，只获取bitmap宽高  
        BitmapFactory.decodeFile(path, opts);
        opts.inSampleSize = HSBitmapUtil.computeSampleSize(opts, -1, 1024 * 800);
        opts.inJustDecodeBounds = false;// 这里一定要将其设置回false，因为之前我们将其设置成了true  
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inDither = false;
        opts.inPurgeable = true;
        opts.inTempStorage = new byte[16 * 1024];
        FileInputStream is = null;
        Bitmap bmp = null;
        ByteArrayOutputStream baos = null;
        try {
            is = new FileInputStream(path);
            bmp = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);
            double scale = getScaling(opts.outWidth * opts.outHeight,
                    1024 * 600);
            Bitmap bmp2 = Bitmap.createScaledBitmap(bmp,
                    (int) (opts.outWidth * scale),
                    (int) (opts.outHeight * scale), true);
            bmp.recycle();
            baos = new ByteArrayOutputStream();
            bmp2.compress(CompressFormat.JPEG, 100, baos);
            bmp2.recycle();
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.gc();
        }
        return baos.toByteArray();
    }

    private static double getScaling(int src, int des) {
        /**
         * 48 目标尺寸÷原尺寸 sqrt开方，得出宽高百分比 49 
         */
        double scale = Math.sqrt((double) des / (double) src);
        return scale;
    }

    /**
     * 将图片转成二进制
     *
     * @param bitmap
     * @return
     */
    public static byte[] getBitmapByte(final Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static Bitmap OptionsBitmap(String path) {
        Bitmap bitmap;
        // 图片解析的配置
        Options options = new Options();
        // 真正解析图片
        options.inJustDecodeBounds = false;
        // 设置采样率
        options.inSampleSize = 8;
        bitmap = BitmapFactory.decodeFile(path, options);
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.PNG, 100, baos);
        }
        return bitmap;
    }
}
