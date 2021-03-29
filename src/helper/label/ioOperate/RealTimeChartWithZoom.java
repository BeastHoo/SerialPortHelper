package helper.label.ioOperate;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.TextAnchor;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.lang.reflect.Field;

public class RealTimeChartWithZoom {
    private static int x;
    private JFreeChart jFreeChart;
    private final XYSeries CurrentLocation;
    private final XYSeries ExpectedLocation;
    private XYSeriesCollection xyDataset;
    private Field pan;
    public RealTimeChartWithZoom()
    {
        CurrentLocation=new XYSeries("CurrentLocation");
        ExpectedLocation=new XYSeries("ExpectedLocation");
        xyDataset=new XYSeriesCollection();
        xyDataset.addSeries(CurrentLocation);
        xyDataset.addSeries(ExpectedLocation);
        try {
            Class aClass = Class.forName("org.jfree.chart.ChartPanel");
            try {
                pan=aClass.getDeclaredField("panMask");
                pan.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        x=1;
        createChart();
    }
    private void createChart()
    {
        jFreeChart=ChartFactory.createXYLineChart(null,"","", xyDataset,
                PlotOrientation.VERTICAL,true,true,false);


        XYPlot xyPlot=jFreeChart.getXYPlot();
        NumberAxis valueAxis= (NumberAxis) xyPlot.getRangeAxis();
        valueAxis.setAutoRange(true);
        valueAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        valueAxis.setMinorTickMarksVisible(false);

        jFreeChart.setTextAntiAlias(false);
        NumberAxis numberAxis= (NumberAxis) xyPlot.getDomainAxis();
        numberAxis.setAutoTickUnitSelection(true);
        numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        numberAxis.setAutoRange(true);


        xyPlot.setBackgroundAlpha(0.7f);
        xyPlot.setDomainPannable(true);
        xyPlot.setRangePannable(true);
        xyPlot.setBackgroundPaint(Color.white);
        xyPlot.setOutlineVisible(true);
        xyPlot.setOutlinePaint(null);



        XYLineAndShapeRenderer lineAndShapeRenderer= (XYLineAndShapeRenderer) xyPlot.getRenderer();
        lineAndShapeRenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
        lineAndShapeRenderer.setBaseShapesVisible(true);
        lineAndShapeRenderer.setBaseItemLabelsVisible(true);
        lineAndShapeRenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
        lineAndShapeRenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.BASELINE_CENTER));
        lineAndShapeRenderer.setBaseStroke(new BasicStroke(1.5f));

        jFreeChart.setBorderPaint(new Color(0,204,205));
        jFreeChart.setBorderVisible(true);
    }

    public ChartPanel getPanel()
    {
        ChartPanel chartPanel=new ChartPanel(jFreeChart,true);
        chartPanel.setMouseWheelEnabled(false);
        chartPanel.setMouseZoomable(false);
        try {
            pan.set(chartPanel,Integer.valueOf(0));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
