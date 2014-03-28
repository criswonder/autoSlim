/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.taobao.controller;

import java.awt.Font;
import java.util.Map;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 *
 * @author wb-maohongyun
 */
public class PieChartUtils {
   private static final PieChartUtils utils;
    static{
       utils   = new PieChartUtils();
       utils.setTheme();
    }
    public static JPanel getPieChartPanel() {
        PieChartUtils utils = new PieChartUtils();
        PieDataset dataset = utils.createDataset();
        // based on the dataset we create the chart
        JFreeChart chart = utils.createPieChart3D(dataset, "分析结果");
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
//        setContentPane(chartPanel);

        return chartPanel;
    }
    public static JPanel getBarChartPanel(Map data) {
        //创建主题样式  
        StandardChartTheme mChartTheme = new StandardChartTheme("CN");  
        //设置图表标题  
        mChartTheme.setExtraLargeFont(new Font("黑体", Font.BOLD, 20));  
        //设置轴向字体  
        mChartTheme.setLargeFont(new Font("宋体", Font.PLAIN, 15));  
        //设置图例字体  
        mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));  
        //应用主题  
        ChartFactory.setChartTheme(mChartTheme);  
         if (data == null) {
            return null;
        }
         
        DefaultCategoryDataset mDataset = new DefaultCategoryDataset(); 
        if (data.keySet() != null && data.keySet().size() > 0) {
            for (Object obj : data.keySet()) {
                mDataset.addValue( Double.parseDouble(data.get(obj).toString()), "",obj.toString());
            }
        }
          JFreeChart mChart = ChartFactory.createBarChart3D(  
                "class文件占比分析图",   
                "文件夹",   
                "大小",  
                mDataset,   
                PlotOrientation.VERTICAL,   
                true,   
                true,  
                true); 
        //设置内部属性  
        CategoryPlot mPlot = (CategoryPlot)mChart.getPlot();  
        //设置纵轴和横轴  
        CategoryAxis mDomainAxis = mPlot.getDomainAxis();  
        //设置柱状图距离x轴最左端（即y轴）的距离百分比10%  
        mDomainAxis.setLowerMargin(0.1);  
        mDomainAxis.setUpperMargin(0.1);  
        //柱体显示数值  
        BarRenderer mRenderer = new BarRenderer();  
        mRenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());  
        mRenderer.setItemLabelFont(new Font("宋体", Font.PLAIN, 15));  
        mRenderer.setItemLabelsVisible(true);  
        mPlot.setRenderer(mRenderer);  
         
       
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(mChart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(700, 400));
        // add it to our application
//        setContentPane(chartPanel);

        return chartPanel;
    }
    public static JPanel getPieChartPanel(Map data) {
        if (data == null) {
            return null;
        }
        DefaultPieDataset result = new DefaultPieDataset();
        if (data.keySet() != null && data.keySet().size() > 0) {
            for (Object obj : data.keySet()) {
                result.setValue(obj.toString(), Double.parseDouble(data.get(obj).toString()));
            }
        }
       

//        result.setValue("Linux", 200);
//        result.setValue("Mac", 20);
//        result.setValue("Windows", 51);
        // based on the dataset we create the chart
        JFreeChart chart = utils.createPieChart3D(result, "分析结果");
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
//        setContentPane(chartPanel);

        return chartPanel;
    }

    /**
     * Creates a sample dataset
     */
    private PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Linux", 200);
        result.setValue("Mac", 20);
        result.setValue("Windows", 51);
        return result;

    }

    /**
     * Creates a chart
     */
    private JFreeChart createPieChart3D(PieDataset dataset, String title) {
        JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
                dataset, // data
                true, // include legend
                true,
                false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;

    }
    
//    private JFreeChart createBarChart3D(PieDataset dataset, String title) {
//        setTheme();
//
//         JFreeChart mChart = ChartFactory.createBarChart3D(  
//                "class文件占比分析图",   
//                "文件夹",   
//                "大小",  
//                mDataset,   
//                PlotOrientation.VERTICAL,   
//                true,   
//                true,  
//                true); 
//
//        PiePlot3D plot = (PiePlot3D) chart.getPlot();
//        plot.setStartAngle(290);
//        plot.setDirection(Rotation.CLOCKWISE);
//        plot.setForegroundAlpha(0.5f);
//        return chart;
//
//    }
    
    private void setTheme(){
    //创建主题样式
        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
//设置标题字体
        mChartTheme.setExtraLargeFont(new Font("宋体", Font.CENTER_BASELINE, 20));
//设置轴向字体
        mChartTheme.setLargeFont(new Font("宋体", Font.CENTER_BASELINE, 15));
//设置图例字体
        mChartTheme.setRegularFont(new Font("宋体", Font.CENTER_BASELINE, 15));
//应用主题样式
        ChartFactory.setChartTheme(mChartTheme);
    }
    
}
