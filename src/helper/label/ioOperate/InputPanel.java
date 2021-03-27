package helper.label.ioOperate;
//import org.knowm.xchart.XYChart;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class InputPanel {
    private JPanel panel;
    private RealtimeChart realtimeChart;
    private XChartPanel xChartPanel;
    private int cur_loc;
    private int expect_loc;
    public InputPanel()
    {
//        realtimeChart.plot();
//        xyChart=new XYChart;
        realtimeChart=new RealtimeChart();
    }
    public RealtimeChart retRealtimeChartEntity()
    {
        return realtimeChart;
    }
    public JPanel retJPanel()
    {
        panel=realtimeChart.buildPanel();

        return panel;
    }

    public void updatePanel(int expY,int curY)
    {
        realtimeChart.updateData(expY,curY);
        panel.validate();
        panel.repaint();
    }
    public void setCur_loc(int cur_loc) {
        this.cur_loc = cur_loc;
    }

    public void setExpect_loc(int expect_loc) {
        this.expect_loc = expect_loc;
    }


}




