/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.taobao.view;

import com.taobao.controller.FileSizeUtils;
import com.taobao.controller.PieChartUtils;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 *
 * @author wb-maohongyun
 */
public class Panel3 extends javax.swing.JPanel {

    private File chooseDir;

    /**
     * Creates new form Panel3
     */
    public Panel3() {
        initComponents();
        jPanel2 = new JPanel();
//        jPanel2.setLayout(new GridLayout(10, 1));
//        jPanel2.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(Panel3.this.jPanel2);
        Panel3.this.add(scrollPane, BorderLayout.SOUTH);
//        this.removeAll();
//        this.add(chartPanel);
//        this.add(PieChartUtils.getPieChartPanel(), BorderLayout.SOUTH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();

        setLayout(new java.awt.BorderLayout());

        jLabel2.setText("Class路径：");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("浏览");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("开始扫描分析");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //        fc.showOpenDialog(this);

        fc.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {

                if (arg0.getClickCount() == 2) {
                    File file = fc.getSelectedFile();
                    if (file.isDirectory()) {
                        fc.setCurrentDirectory(file);
                        fc.rescanCurrentDirectory();
                    } else {
                        fc.approveSelection();
                    }
                }
            }

            //Other methods (can be empty)
            @Override
            public void mousePressed(MouseEvent e) {
                //                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    + fc.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    + fc.getSelectedFile());
            jTextField1.setText(fc.getSelectedFile().getAbsolutePath());

            chooseDir = new File(fc.getSelectedFile().getAbsolutePath());
        } else {
            System.out.println("No Selection ");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        jProgressBar1.setIndeterminate(true);
        jProgressBar1.setValue(19);
        if (null == chooseDir) {
            //warning
        } else {
            WorkThread thread = new WorkThread();
            thread.start();
        }

    }//GEN-LAST:event_jButton2ActionPerformed
//    private PieDataset createDataset() {
//        DefaultPieDataset result = new DefaultPieDataset();
//        result.setValue("class1", 29);
//        result.setValue("class2", 20);
//        result.setValue("class3", 51);
//        return result;
//
//    }
//
//    private JFreeChart createChart(PieDataset dataset, String title) {
//
//        JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
//                dataset, // data
//                true, // include legend
//                true,
//                false);
//
//        PiePlot3D plot = (PiePlot3D) chart.getPlot();
//        plot.setStartAngle(290);
//        plot.setDirection(Rotation.CLOCKWISE);
//        plot.setForegroundAlpha(0.5f);
//        return chart;
//
//    }

    class WorkThread extends Thread {

        @Override
        public void run() {
            Map map = FileSizeUtils.getChildrenFolderUsage(chooseDir);
            JPanel p = PieChartUtils.getBarChartPanel(map);

            Panel3.this.jPanel2.add(p);
            Panel3.this.jPanel2.revalidate();
            Panel3.this.revalidate();
            System.out.println("scan dir finish");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Panel3.class.getName()).log(Level.SEVERE, null, ex);
//            }
            System.out.println("after sleep");
            jProgressBar1.setIndeterminate(false);
            jProgressBar1.setValue(0);
        }
    }
    private javax.swing.JPanel jPanel2;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
