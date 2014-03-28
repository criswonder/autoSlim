package com.taobao.controller;

/**
 * 
 */

import java.io.File;
import java.util.HashMap;

/**
 * @author wb-maohongyun
 *
 */
public class FileSizeUtils {
	private static final String PROJECT_HOME = "D:\\Andy\\gerrit\\taobao_android_tao_3.9.1\\target\\classes\\android";
	/**
	 * 
	 */
	public FileSizeUtils() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getDirSize(new File(PROJECT_HOME)));
	}

	private static long getDirSize(File dir) {
		if (dir == null) {
			return 0;
		}
		if (!dir.isDirectory()) {
			return 0;
		}
		long dirSize = 0;
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				dirSize += file.length();
			} else if (file.isDirectory()) {
//				System.out.println(file.toString()+"="+file.length());
//				dirSize += file.length();
				dirSize += getDirSize(file); // 如果遇到目录则通过递归调用继续统计
			}
		}
		return dirSize;
	}
        
        public static HashMap<String,String> getChildrenFolderUsage(File file){
            if(!file.isDirectory()){
                return null;
            }
            File[] children = file.listFiles();
            float sum = getDirSize(file)/1024;
            float sum20Percent = sum*0.08f;
            if(children!=null && children.length>0){
                HashMap<String ,String> map = new HashMap<>();
                for(int i=0;i<children.length;i++){
                    if(children[i].isDirectory()){
                        float dirSize = getDirSize(children[i])/1024.00f;
                        System.out.println("getChildrenFolderUsage folder name "+children[i].getName()+"="+dirSize+",sum20Percent="+sum20Percent+",sum="+sum);
                        if(dirSize>sum20Percent){
                             map.put(children[i].getName(), String.valueOf(dirSize));
                        }
                    }else{
                        if(map.get("other")!=null){
                            float otherFileSize =Float.parseFloat(map.get("other"));
                            map.put("other",String.valueOf((otherFileSize+children[i].length())/1024.00));
                        }else{
                            map.put("other",String.valueOf(children[i].length()/1024.00));
                        }
                    }
                }
                return map;
            }
            return null;
        }
}
