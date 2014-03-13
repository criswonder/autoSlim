package com.taobao;

import com.taobao.view.Panel1;
import com.taobao.view.Panel3;
import com.taobao.view.Panel2;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * Java中CardLayout布局管理器使用的小例子
 *
 * @author 五斗米 <如转载请保留作者和出处>
 * @blog http://blog.csdn.net/mq612
 */
public class Entrance extends JFrame {
    private JPanel pane = null; // 主要的JPanel，该JPanel的布局管理将被设置成CardLayout
    private JPanel p = null; // 放按钮的JPanel
    private CardLayout card = null; // CardLayout布局管理器
    private JButton button_1 = null; // 上一步
    private JButton button_2 = null; // 下一步
    private JButton b_1 = null, b_2 = null, b_3 = null; // 三个可直接翻转到JPanel组件的按钮
    private JPanel p_1 = null, p_2 = null, p_3 = null; // 要切换的三个JPanel
   
    public Entrance() {
        super("减包工具");
        try {
            // 将LookAndFeel设置成Windows样式
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        /**创建一个具有指定的水平和垂直间隙的新卡片布局*/
        card = new CardLayout(5, 5);
        pane = new JPanel(card); // JPanel的布局管理将被设置成CardLayout
        p = new JPanel(); // 构造放按钮的JPanel
        button_1 = new JButton("< 上一步");
        button_2 = new JButton("下一步 >");
//        b_1 = new JButton("1");
//        b_2 = new JButton("2");
//        b_3 = new JButton("3");
//        b_1.setMargin(new Insets(2,2,2,2));
//        b_2.setMargin(new Insets(2,2,2,2));
//        b_3.setMargin(new Insets(2,2,2,2));
        p.add(button_1);
//        p.add(b_1);
//        p.add(b_2);
//        p.add(b_3);
        p.add(button_2);
        p_1 = new Panel1();
        p_2 = new Panel2();
        p_3 = new Panel3();
//        p_3 = PieChartUtils.getPieChartPanel();
//        p_1.setBackground(Color.RED);
//        p_2.setBackground(Color.BLUE);
//        p_3.setBackground(Color.GREEN);
//        p_1.add(new JLabel("JPanel_1"));
//        p_2.add(new JLabel("JPanel_2"));
//        p_3.add(new JLabel("JPanel_3"));
        pane.add(p_1, "p1");
        pane.add(p_2, "p2");
        pane.add(p_3, "p3");
        /**下面是翻转到卡片布局的某个组件，可参考API中的文档*/
        button_1.addActionListener(new ActionListener(){ // 上一步的按钮动作
            public void actionPerformed(ActionEvent e) {
                card.previous(pane);
            }
        });
        button_2.addActionListener(new ActionListener(){ // 下一步的按钮动作
            public void actionPerformed(ActionEvent e) {
                card.next(pane);
            }
        });
//        b_1.addActionListener(new ActionListener() { // 直接翻转到p_1
//            public void actionPerformed(ActionEvent e) {
//                card.show(pane, "p1");
//            }
//        });
//        b_2.addActionListener(new ActionListener() { // 直接翻转到p_2
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                card.show(pane, "p2");
//            }
//        });
//        b_3.addActionListener(new ActionListener() { // 直接翻转到p_3
//            public void actionPerformed(ActionEvent e) {
//                card.show(pane, "p3");
//            }
//        });
        this.getContentPane().add(pane);
        this.getContentPane().add(p, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 500);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
   
    public static void main(String[] args) {
        new Entrance();
    }
   
}