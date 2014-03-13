/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.taobao;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 线程的优先级
 * 10个计数器线程分别被设置了不同的优先级，我们通过计数器的累加来观察优先级的作用
 * @author 五斗米
 * @blog http://blog.csdn.net/mq612
 */
public class TestMain extends JFrame {
    private MyThread [] thread = null; // 要操作的线程
    private JPanel pane = null;
    private JButton startButton = null, stopButton = null; // 启动、结束按钮

    public TestMain(){
        super("线程的优先级");
        pane = new JPanel();
        thread = new MyThread[10];
        for(int i = 0; i < 10; i++){ // 线程的优先级最小是1，最大是10
            thread[i] = new MyThread(i + 1);
        }
        startButton = new JButton("执行");
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < 10; i++){
                    thread[i].start();
                }
            }
        });
        stopButton = new JButton("结束");
        stopButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < 10; i++){
                    thread[i].quit();
                }
            }
        });
        JPanel p = new JPanel();
        p.add(startButton);
        p.add(stopButton);
        this.getContentPane().add(pane);
        this.getContentPane().add(p, BorderLayout.NORTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    /**
     * 计数器线程
     */
    class MyThread extends Thread{
        private JTextField text = null; // 计数器
        private int i = 0; // 计数器
        private int priority = 0; // 优先级
        private JLabel label = null; // 优先级显示标签
        private boolean b = true; // 控制线程结束的boolean变量

        public MyThread(int priority){
            this.priority = priority;
            this.setPriority(priority);
            JPanel p = new JPanel();
            label = new JLabel("Priority=" + priority);
            text = new JTextField(12);
            p.add(label);
            p.add(text);
            pane.add(p); // 将自己的计数器加入主窗口面板中
        }
        /**
         * 结束线程
         */
        public void quit(){
            b = false;
        }
        public void run(){
            while(b){
                this.text.setText(Integer.toString(i++));
                try {
                    this.sleep(1); // 减小这里的毫秒数，可以让我们更容易观察到结果
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

//    public static void main(String [] args){
//        new TestMain();
//    }

}