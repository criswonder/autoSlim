package com.taobao.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import javax.swing.JTextArea;

public class FileUtils {

    private static final int BUFFERED_SIZE = 1024 * 4;

    /**
     * 创建文件夹
     *
     * @param path 文件夹路径
     */
    public static void createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 删除文件夹
     *
     * @param path 文件夹路径
     */
    public static void deleteDir(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 高效拷贝文件
     */
    public static void move(File fi, File fo, boolean isCut, JTextArea jTa) {
        FileInputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(fi);
            os = new FileOutputStream(fo);
        } catch (FileNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            System.out.println(">>>>>>>>>>>>>>>>>>>>>InputStream exception:" + fi.getPath());
        }
        if (is == null || os == null) {
            return;
        }
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(os);

        byte[] b = new byte[BUFFERED_SIZE];

        int n = 0;
        try {
            while ((n = bis.read(b)) != -1) {
                try {
                    bos.write(b, 0, n);
                } catch (IOException e) {
                    System.out.println("file writing exception:文件写入异常");
                    close(bis, bos);
                    return;
                }
            }
        } catch (IOException e1) {
            System.out.println("file reading exception:文件读取异常");
            close(bis, bos);
            return;
        }
        try {
            bos.flush();
        } catch (IOException e) {
            System.out.println("输入流缓冲异常");
            close(bis, bos);
            return;
        }
        close(bis, bos);
        if (isCut) {
            if (fi.delete()) {
                jTa.append("成功：" + fi.getPath() + "\n");
                jTa.paintImmediately(jTa.getBounds());
            } else {
                jTa.append("失败：" + fi.getPath() + "\n");
                jTa.paintImmediately(jTa.getBounds());
            }
            System.out.println("----------------------------------------------------------------");
        }
    }

    /**
     * 关闭流
     */
    public static void close(InputStream is, OutputStream os) {
        try {
            is.close();
        } catch (IOException e) {
            System.out.println("输入流关闭异常");
        }
        try {
            os.close();
        } catch (IOException e) {
            System.out.println("输出流关闭异常");
        }
    }

    /**
     * 关闭流
     */
    public static void close(BufferedInputStream bis, BufferedInputStream bos) {
        try {
            bis.close();
        } catch (IOException e) {
            System.out.println("输入流关闭异常");
        }
        try {
            bos.close();
        } catch (IOException e) {
            System.out.println("输出流关闭异常");
        }
    }

    /**
     *
     * @param path 文件路径
     * @param type 需要得到的文件类型 如果为null或者""则得到所有文件
     * @param needPathDirectory 是否需要得到路径下子文件夹下的文件
     * @param list 装载你得到的结果
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List getSameTypeFile(String path, String type,
            boolean needPathDirectory, List list) {

        if (path == null || "".equals(path.trim()) || list == null) {
            return null;
        }

        File file = new File(path);

        String[] allFiles = file.list();

        if (allFiles != null) {
            File subFile = null;
            for (int i = 0; i < allFiles.length; i++) {
                subFile = new File(file.getAbsolutePath() + File.separator
                        + allFiles[i]);
                if (type != null && !"".equals(type.trim())) {
                    if (!subFile.isDirectory()
                            && type.equalsIgnoreCase(allFiles[i]
                                    .substring(allFiles[i].lastIndexOf(".")))) {
                        list.add(file.getAbsolutePath() + File.separator
                                + allFiles[i]);
                    }
                } else {
                    if (!subFile.isDirectory()) {
                        list.add(file.getAbsolutePath() + File.separator
                                + allFiles[i]);
                    }
                }

                if (needPathDirectory && subFile.isDirectory()) {
                    getSameTypeFile(subFile.getAbsolutePath(), type,
                            needPathDirectory, list);
                }
            }
        }

        return list;
    }

    /**
     *
     * @param path 文件路径
     * @param encoding 字符编码
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String readInputStreamToList(String path, String encoding) {

        InputStream is = null;
        Reader rd = null;
        LineNumberReader lr = null;
        StringBuffer sb = new StringBuffer();
        try {
            is = new FileInputStream(path);
            rd = new InputStreamReader(is, encoding);
            lr = new LineNumberReader(rd);

        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = "";
        try {
            while ((str = lr.readLine()) != null) {
                sb.append(str + "\r\n");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            lr.close();
        } catch (IOException e) {
            System.out.println("文件读取异常");
        }

        return sb.toString();
    }

    /**
     *
     * @param str要写入文档的字符串
     * @param path 路径
     * @param encoding 编码
     */
    public static void writeListToJspFile(String str, String path,
            String encoding) {
        OutputStream os = null;
        Writer writer = null;
        try {
            os = new FileOutputStream(path);
            writer = new OutputStreamWriter(os, encoding);

            writer.write(str);

        } catch (Exception e) {
            System.out.println("文件写入异常");
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
