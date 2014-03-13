/**
 * 
 */
package com.taobao.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * @author wb-maohongyun
 * 
 */
public class LintScanner {

	public static final String PROJECT_HOME = "D:\\Andy\\gerrit\\taobao_android_tao_3.9.1\\";
	public static final String LINT_HOME = "D:\\Andy\\tools\\android\\sdk\\android-sdk-windows\\tools\\";
	private static final String RESULT_FILE_PATH = "D:\\Andy\\gerrit\\taobao_android_tao_3.9.1\\lint_scan_result.xml";

	/**
	 * 
	 */
	public LintScanner() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FutureTask<String> t = executeCmdCommand(
				"cmd /c lint "+PROJECT_HOME+" --check UnusedResources,IconDuplicates,IconDuplicatesConfig --xml "+
						RESULT_FILE_PATH
				);
		try {
			if(t.get().equals("success")){
				System.out.println("!!!!!!!!!!!!!!!!!!!! sucessful!");
				deleteUnusedResources(parseXMLUseJDOM());
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 */
	public static void deleteUnusedResources(List lst) {
		if(null!=lst){
			Issue issue=null;
			File toDel = null;
			for(Object obj:lst){
				if(obj instanceof Issue){
					issue = (Issue)obj;
					if("UnusedResources".equals(issue.getId())){
						if(issue.getLocation().endsWith(".png")){
							toDel = new File(PROJECT_HOME+issue.getLocation().replace("\\", "\\\\")) ;
							if(toDel.exists()){
								toDel.delete();
								System.out.println("del file "+issue.getLocation());
							}
								
						}
						if(issue.getLocation().endsWith(".xml") 
								&& !(issue.getColumn()!=null && issue.getColumn().length()>0))
						 {
							toDel = new File(PROJECT_HOME+issue.getLocation().replace("\\", "\\\\")) ;
							if(toDel.exists()){
								toDel.delete();
								System.out.println("del file "+issue.getLocation());
							}
							
						}
					}
				}
			}
		}
	}

	/**
	 * 解析lint的扫描结果文件
	 */
	@SuppressWarnings("rawtypes")
	public static List parseXMLUseJDOM() {
		SAXBuilder builder = new SAXBuilder();
		List<Issue> issues = new ArrayList<Issue>();
		File xmlFile = new File(RESULT_FILE_PATH);
		try {

			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List list = rootNode.getChildren("issue");
			Issue issue = null;
			for (int i = 0; i < list.size(); i++) {
				issue = new Issue();
				Element node = (Element) list.get(i);
				issue.setId(node.getAttributeValue("id"));
				
				//TODO 有的重复的drawable没有location信息，这种也需要删除
				if(node.getChild("location")!=null){
                                    issue.setLocation(node.getChild("location").getAttributeValue("file"));
                                    issue.setColumn(node.getChild("location").getAttributeValue("column"));
                                    issue.setLine(node.getChild("location").getAttributeValue("line"));
				}else{
                                    continue;
                                }
//				if(issue.getColumn()!=null && issue.getColumn().length()>0){
//					System.out.println(issue);
//				}
				issues.add(issue);
			}

		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
		return issues;
	}

	/**
	 * 在cmd中执行lint命令
	 * 
	 * @param cmdStr
	 *            拼接好的命令参数
	 * @return
	 */
	public static FutureTask<String> executeCmdCommand(final String cmdStr) {
		final ExecutorService exec = Executors.newSingleThreadExecutor();
		FutureTask<String> task = new FutureTask<String>(new Callable<String>() {

			@Override
			public String call() throws Exception {
				Process proc = Runtime.getRuntime().exec(cmdStr);

				BufferedReader stdInput = new BufferedReader(new InputStreamReader(
						proc.getInputStream(), Charset.forName("GBK")));

				BufferedReader stdError = new BufferedReader(new InputStreamReader(
						proc.getInputStream(), Charset.forName("GBK")));

				String s = null;
				System.out.println("Here is the standard output of the command:\n");
				while ((s = stdInput.readLine()) != null) {
					System.out.println(s);
				}
				System.out.println("Here is the standard error of the command (if any):\n");
				while ((s = stdError.readLine()) != null) {
					System.out.println(s);
				}
				proc.destroy();
				exec.shutdown();
				return "success";
			}
		});
		exec.execute(task);
		return task;
	}

	static class Issue{
		private String id;
		private String location;
		private String line;
		private String column;
		
		@Override
		public String toString() {
			return id+","+location;
		}
		public Issue() {
		}
		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * @return the location
		 */
		public String getLocation() {
			return location;
		}
		/**
		 * @param location the location to set
		 */
		public void setLocation(String location) {
			this.location = location;
		}
		/**
		 * @return the line
		 */
		public String getLine() {
			return line;
		}
		/**
		 * @param line the line to set
		 */
		public void setLine(String line) {
			this.line = line;
		}
		/**
		 * @return the column
		 */
		public String getColumn() {
			return column;
		}
		/**
		 * @param column the column to set
		 */
		public void setColumn(String column) {
			this.column = column;
		}
	}
}
