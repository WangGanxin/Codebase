
package com.ganxin.codebase.utils.file;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Description : 单文件工具类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class FileUtil {

    /**
     * 创建文件的模式，已经存在的文件要覆盖
     */
    public final static int MODE_COVER = 1;

    /**
     * 创建文件的模式，文件已经存在则不做其它事
     */
    public final static int MODE_UNCOVER = 0;

    private FileUtil() {}

    /**
     * 获取文件的输入流
     *
     * @param path
     * @return
     */
    public static FileInputStream getFileInputStream(String path) {
        FileInputStream fis = null;
        try {
            File file = new File(path);
            if (file.exists()) {
                fis = new FileInputStream(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fis;
    }

    /**
     * 获取文件的输出流
     *
     * @param path
     * @return
     */
    public static OutputStream getFileOutputStream(String path) {
        FileOutputStream fos = null;
        try {
            File file = new File(path);
            if (file.exists()) {
                fos = new FileOutputStream(file);
            }
        } catch (Exception e) {
            return null;
        }
        return fos;
    }

    /**
     * 给定包含绝对路径的文件名，输出文件名。
     *
     * @param filename
     * @return
     */
    public static String getFileName(String filename) {
        String castLastSlash = filename;
        while (castLastSlash.endsWith(File.separator)) {
            castLastSlash = castLastSlash.substring(0,
                    castLastSlash.length() - 1);
        }
        if (!TextUtils.isEmpty(castLastSlash)) {
            int slash = castLastSlash.lastIndexOf(File.separator);
            if (slash > -1 && slash < castLastSlash.length() - 1) {
                return castLastSlash.substring(slash + 1);
            } else {
                return castLastSlash;
            }
        }
        return "";
    }


    /**
     * 给定文件名（包含或不包含绝对路径），输出去掉扩展名的文件名。
     *
     * @param filename
     * @return
     */
    public static String getFileNameNoExtention(String filename) {
        if (!TextUtils.isEmpty(filename)) {
            String name = getFileName(filename);
            int dot = name.lastIndexOf('.');
            if ((dot > -1) && (dot < (name.length() - 1))) {
                return name.substring(0, dot);
            }
        }
        return "";
    }

    /**
     * 获取文件的数据
     *
     * @param path
     * @return
     */
    public static byte[] getFileData(String path) {
        byte[] data = null;// 返回的数据
        try {
            File file = new File(path);
            if (file.exists()) {
                data = new byte[(int) file.length()];
                FileInputStream fis = new FileInputStream(file);
                fis.read(data);
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 文件大小
     *
     * @param path
     * @return
     */
    public static int getFileSize(String path) {
        return (int) new File(path).length();
    }

    /**
     * @param f
     * @return
     */
    public static long getFileSizes(File f) {
        long size = 0;
        try {
            File flist[] = f.listFiles();
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    size = size + getFileSizes(flist[i]);
                } else {
                    size = size + flist[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * @param file
     * @return
     */
    public static String getFileSizeToString(File file) {
        Long size = getFileSizes(file);

        if (size < 1024) {
            return size + "B";
        } else if (size < 1024L * 1024L) {
            return String.format("%.1f", size.doubleValue() / 1024) + "K";
        } else if (size < 1024L * 1024L * 1024L) {
            return String.format("%.1f", size.doubleValue() / 1024 / 1024)
                    + "M";
        } else {
            return String.format("%.1f",
                    size.doubleValue() / 1024L / 1024L / 1024L) + "G";
        }
    }

    /**
     * 获取文件扩展名
     *
     * @param filename
     * @return
     */
    public static String getFileExtensionName(String filename) {
        if (!TextUtils.isEmpty(filename)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }


    /**
     * 获取文件扩展名
     *
     * @param file
     * @return
     */
    public static String getFileExtensionName(File file) {
        return getFileExtensionName(file.getName());
    }

    /**
     * 解析文件所在的文件夹
     *
     * @param filePath 文件路径
     * @return 文件所在的文件夹路径
     */
    public static String getFileFolderPath(final String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        int last = filePath.lastIndexOf("/");
        if (last == -1) {
            return null;
        }
        return filePath.substring(0, last + 1);
    }

    public static String getFileParentAbsPath(String abs_filename) {
        if (!TextUtils.isEmpty(abs_filename)) {
            int slash = abs_filename.lastIndexOf(File.separator);
            if ((slash > -1) && (slash < (abs_filename.length() - 1))) {
                return abs_filename.substring(0, slash);
            }
        }
        return "";
    }

    /**
     * 删除文件或文件夹(包括目录下的文件)
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        try {
            File f = new File(filePath);
            if (f.exists() && f.isDirectory()) {
                File[] delFiles = f.listFiles();
                if (delFiles != null) {
                    for (int i = 0; i < delFiles.length; i++) {
                        deleteFile(delFiles[i].getAbsolutePath());
                    }
                }
            }
            f.delete();
        } catch (Exception e) {

        }
    }

    /**
     * 删除文件
     *
     * @param filePath
     * @param deleteParent 是否删除父目录
     */
    public static void deleteFile(String filePath, boolean deleteParent) {
        if (filePath == null) {
            return;
        }
        try {
            File f = new File(filePath);
            if (f.exists() && f.isDirectory()) {
                File[] delFiles = f.listFiles();
                if (delFiles != null) {
                    for (int i = 0; i < delFiles.length; i++) {
                        deleteFile(delFiles[i].getAbsolutePath(), deleteParent);
                    }
                }
            }
            if (deleteParent) {
                f.delete();
            } else if (f.isFile()) {
                f.delete();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 创建一个空的文件(创建文件的模式，已经存在的是否要覆盖)
     *
     * @param path
     * @param mode
     */
    public static boolean createFile(String path, int mode) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        try {
            File file = new File(path);
            if (file.exists()) {
                if (mode == FileUtil.MODE_COVER) {
                    file.delete();
                    file.createNewFile();
                }
            } else {
                // 如果路径不存在，先创建路径
                File mFile = file.getParentFile();
                if (!mFile.exists()) {
                    mFile.mkdirs();
                }
                file.createNewFile();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 创建一个空的文件夹(创建文件夹的模式，已经存在的是否要覆盖)
     *
     * @param path
     * @param mode
     */
    public static void createFolder(String path, int mode) {
        try {
            File file = new File(path);
            if (file.exists()) {
                if (mode == FileUtil.MODE_COVER) {
                    FileUtil.deleteFile(path);
                    file.mkdirs();
                }
            } else {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断输入的两个文件名是否类似。 类似标准： 1 - 扩展名是否一样； 2 - 文件名前缀是否一样。 例如： ABC.jpg 与
     * ABC(1).jpg判断将返回true。
     *
     * @param name1 输入文件名，必须是带扩展名的文件名，不能带路径。
     * @param name2 输入文件名，必须是带扩展名的文件名，不能带路径。
     * @return
     */
    public static boolean compareFileName(final String name1, final String name2) {
        String ext1 = getFileExtensionName(name1);
        String ext2 = getFileExtensionName(name2);
        String[] name_1 = name1.split("(\\(\\d*\\))*\\.");
        String[] name_2 = name2.split("(\\(\\d*\\))*\\.");
        return ext1.equals(ext2) && name_1[0] != null && name_2[0] != null
                && name_1[0].equals(name_2[0]);
    }

    /**
     * 获取文件大小
     *
     * @param path
     * @return
     */
    public static long getSize(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        long size = 0;
        try {
            File file = new File(path);
            if (file.exists()) {
                size = file.length();
            }
        } catch (Exception e) {
            return 0;
        }
        return size;
    }

    /**
     * 判断文件或文件夹是否存在
     *
     * @param path
     * @return true 文件存在
     */
    public static boolean isExist(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        boolean exist = false;
        try {
            File file = new File(path);
            exist = file.exists();
        } catch (Exception e) {
            return false;
        }
        return exist;
    }

    /**
     * 移动文件
     *
     * @param oldFilePath 旧路径
     * @param newFilePath 新路径
     * @return
     */
    public static boolean moveFile(String oldFilePath, String newFilePath) {
        if (TextUtils.isEmpty(oldFilePath) || TextUtils.isEmpty(newFilePath)) {
            return false;
        }
        File oldFile = new File(oldFilePath);
        if (oldFile.isDirectory() || !oldFile.exists()) {
            return false;
        }
        try {
            File newFile = new File(newFilePath);
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(oldFile));
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] buf = new byte[1024];
            int read;
            while ((read = bis.read(buf)) != -1) {
                fos.write(buf, 0, read);
            }
            fos.flush();
            fos.close();
            bis.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
