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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * @author wb-maohongyun
 *
 */
public class LintScanner {

    private JTextArea myTextArea = null;
    private File lintSaveDir = null;

    public LintScanner(JTextArea jTextArea2, File lintSaveDir) {
        this.myTextArea = jTextArea2;
        this.lintSaveDir = lintSaveDir;
    }

    public void deleteUnusedResources(List lst, File pro) {
        if (null != lst) {
            Issue issue = null;
            File toDel = null;
            for (Object obj : lst) {
                if (obj instanceof Issue) {
                    issue = (Issue) obj;
                    if ("UnusedResources".equals(issue.getId())) {
                        if (issue.getLocation().endsWith(".png")) {
                            toDel = new File(pro.getAbsolutePath() + "\\" + issue.getLocation().replace("\\", "\\\\"));
                            if (toDel.exists()) {
//                                toDel.delete();
                                FileUtils.move(toDel, new File(lintSaveDir.getAbsolutePath() + "\\" + issue.getLocation()), true, myTextArea);
                                myTextArea.append("del file " + issue.getLocation() + "\n");
                                myTextArea.paintImmediately(myTextArea.getBounds());
                            }
                        }
                        if (issue.getLocation().endsWith(".xml")
                                && !(issue.getColumn() != null && issue.getColumn().length() > 0)) {
                            toDel = new File(pro.getAbsolutePath() + "\\" + issue.getLocation().replace("\\", "\\\\"));
                            if (toDel.exists()) {
//                                toDel.delete();
                                FileUtils.move(toDel, new File(lintSaveDir.getAbsolutePath() + "\\" + issue.getLocation()), true, myTextArea);
                                myTextArea.append("del file " + issue.getLocation() + "\n");
                                myTextArea.paintImmediately(myTextArea.getBounds());
                            }

                        }
                    }
                }
            }
        }
    }

    /**
     * 解析lint的扫描结果文件
     * @param resultFilePath
     */
    @SuppressWarnings("rawtypes")
    public List parseXMLUseJDOM(String resultFilePath) {
        SAXBuilder builder = new SAXBuilder();
        List<Issue> issues = new ArrayList<Issue>();
        File xmlFile = new File(resultFilePath);
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
                if (node.getChild("location") != null) {
                    issue.setLocation(node.getChild("location").getAttributeValue("file"));
                    issue.setColumn(node.getChild("location").getAttributeValue("column"));
                    issue.setLine(node.getChild("location").getAttributeValue("line"));
                    issue.setName(node.getChild("location").getAttributeValue("name"));
                } else {
                    continue;
                }
                if(issue.getLocation().contains(".png")){
                    System.out.println("issue.getLocation():" + issue.getLocation());
                }
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
     * @param cmdStr 拼接好的命令参数
     * @return
     */
    public FutureTask<String> executeCmdCommand(final String cmdStr) {
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
                myTextArea.append("Here is the standard output of the command:\n");
                myTextArea.paintImmediately(myTextArea.getBounds());
                while ((s = stdInput.readLine()) != null) {
                    myTextArea.append(s);
                    myTextArea.paintImmediately(myTextArea.getBounds());
                }
                myTextArea.append("Here is the standard error of the command (if any):\n");
                myTextArea.paintImmediately(myTextArea.getBounds());
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

    static class Issue {

        private String id;
        private String location;
        private String line;
        private String column;
        private String name;

        @Override
        public String toString() {
            return getId() + "," + getLocation();
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

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

    }
}
