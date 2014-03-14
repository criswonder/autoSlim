package com.taobao.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JTextArea;

public class FileCtrl {

    private final String RES_TYPE_PNG = "png";
    private final String RES = "\\res\\";
    private final String[] FILEDIR = {"drawable-hdpi", "drawable-ldpi",
        "drawable-mdpi", "drawable-nodpi", "drawable-xhdpi", "drawable-xxhdpi", "drawable"};
    private String savePath = "";
    private String filePath = "";
    private List<List<File>> list_main = null;
    private File projectDir;
    private File saveDir;
    private JTextArea jTa;

    public FileCtrl(File projectDir, File saveDir, JTextArea jTa) {
        this.projectDir = projectDir;
        this.saveDir = saveDir;
        this.jTa = jTa;
    }

    public void letsGO() {
        init();
        start();
    }

    private void init() {
        filePath = projectDir.getAbsolutePath();
        savePath = saveDir.getAbsolutePath();
        if (list_main == null) {
            list_main = new ArrayList<List<File>>();
            for (String fileDir : FILEDIR) {
                List<File> tempList = getFileData(filePath + RES + fileDir);
                if (tempList != null) {
                    list_main.add(tempList);
                }
            }
        }
    }

    private void start() {
        List<File> tempMoveFile = new ArrayList<File>();
        for (int i = 0; i < list_main.size(); i++) {
            for (int j = i + 1; j < list_main.size(); j++) {
                for (int iIn = 0; iIn < list_main.get(i).size(); iIn++) {
                    for (int jIn = 0; jIn < list_main.get(j).size(); jIn++) {
                        File iInFile = list_main.get(i).get(iIn);
                        File jInFile = list_main.get(j).get(jIn);
                        if (iInFile.getName().equals(jInFile.getName())) {
                            if (iInFile.length() >= jInFile.length()) {
                                tempMoveFile.add(iInFile);
                            } else {
                                tempMoveFile.add(jInFile);
                            }
                            break;
                        }
                    }
                }
            }
        }
        removeDuplicateWithOrder(tempMoveFile);
        jTa.setText("");
        for (File file : tempMoveFile) {
            FileUtils.move(file, new File(savePath + "\\" + file.getName()), true, jTa);
        }
        jTa.append("操作完成");
        jTa.paintImmediately(jTa.getBounds());
    }

    private void removeDuplicateWithOrder(List<File> list) {
        Set<File> set = new HashSet<File>();
        List<File> newList = new ArrayList<File>();
        for (Iterator<File> iter = list.iterator(); iter.hasNext();) {
            File element = iter.next();
            if (set.add(element)) {
                newList.add(element);
            }
        }
        list.clear();
        list.addAll(newList);
    }

    private List<File> getFileData(String filePath) {
        File dir = new File(filePath);
        File[] files = dir.listFiles();
        List<File> list = new ArrayList<File>();
        if (files == null) {
            return null;
        }
        try {
            for (File file : files) {
                if (!file.isDirectory()
                        && getExtensionName(file.getName()).equalsIgnoreCase(
                                RES_TYPE_PNG)) {
                    list.add(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    private String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
}
