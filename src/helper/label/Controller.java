/*
 * @author 何雨谦
 * @DATE 21/3/18
 */
package helper.label;

import gnu.io.SerialPort;

import javax.swing.*;
//import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Controller extends JFrame {
//    private JTabbedPane pane;
    private JSplitPane jSplitPane;
    public Controller()
    {
        init();
        setLocation(150,60);
        setSize(1000,800);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
    }
    private void init()
    {
        setTitle("上位机");
        PIDHelper pidHelper=new PIDHelper(this);

        jSplitPane=pidHelper.style();
        add(jSplitPane);
//        UIManager.put("TabbedPane.contentOpaque", false);

//        pane = new JTabbedPane();

//        pane.addTab("串口助手",null);
//        pane.addTab("步进电机控制",null);
//        pane.addTab("PID调试助手",new PIDHelper().style());
//        pane.addTab("GSM调试助手",null);
//        pane.addTab("网络助手",null);
//        add(pane);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                setVisible(false);

                if(pidHelper.getSerialPort()!=null)
                {
                    pidHelper.getRedo().getIoStreamOperation().actRedo();
                    pidHelper.getSerialPort().close();
                }

                System.exit(0);
            }
        });
    }

//    @Override
//    public synchronized void addWindowListener(WindowListener l) {
//        super.addWindowListener(l);
//
//    }
}
