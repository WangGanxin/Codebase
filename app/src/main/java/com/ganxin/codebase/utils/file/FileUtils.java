package com.ganxin.codebase.utils.file;

import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Description : 文件操作工具类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class FileUtils {

    private FileUtils() {}

    /**
     * 获取指定目录及其子目录下所有文件列表
     *
     * @param file 目标文件
     * @return 所有文件列表
     */
    public static List<String> listFilePath(File file) {
        List<String> list = new ArrayList<String>();
        for (File childFile : listFile(file)) {
            list.add(childFile.getPath());
        }
        return list;
    }


    /**
     * 获取指定目录及其子目录下所有文件列表
     *
     * @param file 目标文件
     * @return 所有文件列表
     */
    public static List<File> listFile(File file) {
        List<File> list = new ArrayList<File>();
        List<File> dirList = new ArrayList<File>();
        if (file != null && file.exists()) {
            if (file.isFile()) {
                list.add(file);
            } else if (file.isDirectory()) {
                if (!isNoMediaDir(file)) {
                    File[] files = file.listFiles();
                    if (files != null) {
                        for (File childFile : files) {
                            if (childFile.isFile()) {
                                list.add(childFile);
                            } else if (childFile.isDirectory()) {
                                dirList.add(childFile);
                            }
                        }
                        while (!dirList.isEmpty()) {
                            File dir = dirList.remove(0);
                            if (isNoMediaDir(dir)) {
                                continue;
                            }
                            File[] listFiles = dir.listFiles();
                            if (listFiles != null) {
                                for (File childFile : listFiles) {
                                    if (childFile.isFile()) {
                                        list.add(childFile);
                                    } else if (childFile.isDirectory()) {
                                        dirList.add(childFile);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取指定目录及其子目录下所有文件列表
     *
     * @param path 目标文件路径
     * @return 所有文件列表
     */
    public static List<String> listFilePath(String path) {
        List<String> list = new ArrayList<String>();
        for (File childFile : listFile(path)) {
            list.add(childFile.getPath());
        }
        return list;
    }

    /**
     * 获取指定目录及其子目录下所有文件列表
     *
     * @param path 目标文件路径
     * @return 所有文件列表
     */
    public static List<File> listFile(String path) {
        if (!TextUtils.isEmpty(path)) {
            return listFile(new File(path));
        }
        return null;
    }

    public static boolean isNoMediaDir(File dir) {
        boolean result = false;
        if (dir == null || dir.isFile()) {
            result = true;
        } else {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    if (childFile.isFile() && ".nomedia".equals(childFile.getName())) {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 创建包含指定文件内容的文件
     *
     * @param filePath    文件路径
     * @param fileContent 文件内容
     * @return true 标示创建成功
     */
    public static boolean createFile(String filePath, String fileContent) {
        boolean flag = false;
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            boolean b = parent.mkdirs();
            if (!b) {
                return false;
            }
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(fileContent);
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 删除文件
     *
     * @param path 文件路径，如是文件夹路径，则将删除整个文件夹
     * @return true 表示删除成功
     */
    public static boolean deleteFile(String path) {
        boolean result = false;
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                if (file.isFile()) {
                    result = file.delete();
                } else {
                    result = removeDir(file);
                }
            }
        }
        return result;
    }

    /**
     * 删除文件夹及其下所有文件、文件夹
     *
     * @param dir 文件夹
     * @return true 标示删除成功
     */
    public static boolean removeDir(File dir) {
        boolean flag = false;
        if (dir.exists() && dir.isDirectory()) {
            File[] childFiles = dir.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                flag = dir.delete();
            } else {
                for (File child : childFiles) {
                    boolean del = false;
                    if (child.isDirectory()) {
                        del = removeDir(child);
                    } else if (child.isFile()) {
                        del = child.delete();
                    }
                    if (!del) {
                        break;
                    }
                }
                flag = dir.delete();
            }
        }
        return flag;
    }


    public static boolean removeCacheFiles(File dir) {
        boolean flag = false;
        if (dir.exists() && dir.isDirectory()) {
            File[] childFiles = dir.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                flag = dir.delete();
            } else {
                long maxModifyTime = 0;
                String modifyCacheFilename = "";
                //找出最后缓存文件
                for (File child : childFiles) {
                    if (child.isFile()) {
                        if (child.lastModified() > maxModifyTime) {
                            maxModifyTime = child.lastModified();
                            modifyCacheFilename = child.getName();
                        }
                    }
                }
                for (File child : childFiles) {
                    if (child.isDirectory()) {
                        removeDir(child);
                    } else if (child.isFile()) {
                        if (child.getName() != null && !child.getName().equals(modifyCacheFilename)) {
                            child.delete();
                        }
                    }
                }
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 判断指定路径的文件是否存在
     *
     * @param path 文件路径
     * @return true 表示文件存在
     */
    public static boolean fileExists(String path) {
        boolean exists = false;
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            exists = file.exists() && file.isFile();
        }
        return exists;
    }

    /**
     * 判断文件是否有效,有的文件大小为0，不能视为有效文件
     */
    public static boolean fileAvaliable(String path) {
        boolean flag = false;
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            flag = file.exists() && file.isFile() && file.length() > 0;
        }
        return flag;
    }

    /**
     * 获取目录或文件总大小
     *
     * @param file 目标文件
     * @return 目录或文件总大小，单位：字节
     */
    public static long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            if (file.isFile()) {
                size = file.length();
            } else if (file.isDirectory()) {
                for (File tmp : file.listFiles()) {
                    size += getFileSize(tmp);
                }
            }
        }
        return size;
    }

    /**
     * 解压 ZIP 文件
     *
     * @param inputStream ZIP 文件的输入流对象
     * @param savePath    解压后的文件保存路径
     */
    public static void unZip(InputStream inputStream, String savePath) {
        try {
            File file = new File(savePath);
            if (file.exists() && file.isDirectory()) {
                removeDir(file);
            }
            if (file.mkdirs()) {
                ZipInputStream zis = new ZipInputStream(inputStream);
                ZipEntry zipEntry;
                while ((zipEntry = zis.getNextEntry()) != null) {
                    File saveFile = new File(savePath, zipEntry.getName());
                    if (zipEntry.isDirectory()) {
                        if (!saveFile.mkdirs()) {
                            break;
                        }
                    } else {
                        byte[] data = new byte[1024];
                        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
                        int length;
                        while ((length = zis.read(data)) != -1) {
                            out.write(data, 0, length);
                        }
                        out.flush();
                        out.close();
                    }
                }
                zis.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 解压 ZIP 文件
     *
     * @param zipPath  ZIP 文件路径
     * @param savePath ZIP 文件解压后的文件保存路径
     */
    public static void unZip(String zipPath, String savePath) {
        File zipFile = new File(zipPath);
        if (zipFile.exists() && zipFile.isFile()) {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(zipFile);
                unZip(inputStream, savePath);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
