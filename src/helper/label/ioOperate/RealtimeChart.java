package helper.label.ioOperate;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.LinkedList;


public class RealtimeChart {
    private XYChart xyChart;
    private LinkedList<Integer> expValY;


    private LinkedList<Integer> curValY;
    private int size=1000;

    public RealtimeChart()
    {

        expValY=new LinkedList<>();

        curValY=new LinkedList<>();

    }

    public XChartPanel<XYChart> buildPanel() {

        XChartPanel xChartPanel=new XChartPanel<XYChart>(getXYchart());
        xChartPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(e.getWheelRotation()==-1)
                {
                    int height = xChartPanel.getChart().getHeight();
                    int width = xChartPanel.getChart().getWidth();
                    xChartPanel.setSize(width+200,height+200);
                    xChartPanel.validate();
                }else if(e.getWheelRotation()==1)
                {
                    int height = xChartPanel.getChart().getHeight();
                    int width = xChartPanel.getChart().getWidth();
//                    xChartPanel.getChart().getStyler().setPlotContentSize(0.7);
                    if(height>300&&width>300)
                    {
                        xChartPanel.setSize(width-200,height-200);
                        xChartPanel.validate();
                    }

                }
            }
        });
        return xChartPanel;
    }

    public void updateData(int expY, int curY)
    {
        if(expValY.isEmpty()==true)
        {
            expValY.add(expY);
        }else if(expValY.size()>=size){
            expValY.remove(0);
            expValY.add(expY);
        }
        if(curValY.isEmpty()==true)
        {
            curValY.add(curY);
        }else if(curValY.size()>=size){
            curValY.remove(0);
            curValY.add(curY);
        }

        xyChart.updateXYSeries("ExpectedLocation",null,expValY,null);
        xyChart.updateXYSeries("CurrentLocation", null, curValY,null);

    }

    private XYChart getXYchart()
    {
        expValY.add(20000);
        expValY.add(20000);
        expValY.add(20000);
        expValY.add(16000);
        curValY.add(19994);
        curValY.add(19994);
        curValY.add(19997);
        curValY.add(15790);


        xyChart=new XYChartBuilder().width(500).height(100).theme(Styler.ChartTheme.Matlab).title("").build();

        xyChart.addSeries("ExpectedLocation",null,expValY);
        xyChart.addSeries("CurrentLocation",null,curValY);
        xyChart.getStyler().setLegendBackgroundColor(Color.white);
        xyChart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
        xyChart.getStyler().setLegendLayout(Styler.LegendLayout.Horizontal);
        xyChart.getStyler().setXAxisTicksVisible(false);
        xyChart.getStyler().setToolTipsEnabled(true);
        xyChart.getStyler().setHasAnnotations(true);
//        xyChart.getStyler().setAxisTickLabelsColor(Color.white);
        return xyChart;
    }


}
