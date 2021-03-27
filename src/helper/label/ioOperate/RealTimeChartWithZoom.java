package helper.label.ioOperate;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class RealTimeChartWithZoom {
    private static int x;
    private JFreeChart jFreeChart;
    private final XYSeries CurrentLocation;
    private final XYSeries ExpectedLocation;
    private XYSeriesCollection xyDataset;
    public RealTimeChartWithZoom()
    {
        CurrentLocation=new XYSeries("CurrentLocation");
        ExpectedLocation=new XYSeries("ExpectedLocation");
        xyDataset=new XYSeriesCollection();
        xyDataset.addSeries(CurrentLocation);
        xyDataset.addSeries(ExpectedLocation);
        x=0;
        createChart();
        update(16000,15977);
        update(20000,19987);
        update(20000,19987);
        update(20000,19987);
        update(20000,19987);

    }
    private void createChart()
    {
        jFreeChart=ChartFactory.createXYLineChart(null,"","", xyDataset,
                PlotOrientation.VERTICAL,true,true,false);

        XYPlot xyPlot=jFreeChart.getXYPlot();
        NumberAxis valueAxis= (NumberAxis) xyPlot.getRangeAxis();
        valueAxis.setAutoRange(true);
        valueAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//        valueAxis.setFixedAutoRange(5);
        valueAxis.setMinorTickMarksVisible(false);
        
        NumberAxis numberAxis= (NumberAxis) xyPlot.getDomainAxis();
        numberAxis.setAutoTickUnitSelection(true);
        numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        numberAxis.setAutoRange(true);
        xyPlot.setOutlinePaint(null);
        xyPlot.setBackgroundAlpha(0.0f);
        xyPlot.setDomainPannable(true);
        xyPlot.setRangePannable(true);

        XYLineAndShapeRenderer lineAndShapeRenderer= (XYLineAndShapeRenderer) xyPlot.getRenderer();
        lineAndShapeRenderer.setBaseLinesVisible(true);
        lineAndShapeRenderer.setDrawOutlines(true);
        lineAndShapeRenderer.setUseFillPaint(true);
        lineAndShapeRenderer.setBaseFillPaint(Color.red);
        lineAndShapeRenderer.setAutoPopulateSeriesOutlinePaint(true);
        lineAndShapeRenderer.setAutoPopulateSeriesOutlineStroke(true);

        jFreeChart.setBorderPaint(new Color(0,204,205));
        jFreeChart.setBorderVisible(true);
    }

    public ChartPanel getPanel()
    {
        ChartPanel chartPanel=new ChartPanel(jFreeChart,true);
        chartPanel.setMouseWheelEnabled(false);
        chartPanel.setMouseZoomable(true);
        chartPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (-1 == e.getWheelRotation()) {
                    chartPanel.zoomInBoth(10, 10);
                } else if (1 == e.getWheelRotation()) {
                    chartPanel.zoomOutBoth(10, 10);
                }
            }
        });
        return chartPanel;
    }

    public void update(int expY,int curY)
    {
        ExpectedLocation.add(x,expY);
        CurrentLocation.add(x,curY);
        x++;
    }


}
