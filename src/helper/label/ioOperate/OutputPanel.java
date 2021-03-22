package helper.label.ioOperate;

import gnu.io.SerialPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OutputPanel implements ActionListener {
    private IOStreamOperation ioStreamOperation;
    private JPanel panel;
    private JLabel PidLabel;
    private JLabel p;
    private JTextField P;
    private JLabel i;
    private JTextField I;
    private JLabel d;
    private JTextField D;
    private SerialPort serialPort;
    private JButton jButton;
    private JFrame jf;
    private JButton sentExpectVal;
    private JTextField expectVal;
    private JLabel expectValTag;
    public OutputPanel(JFrame jf)
    {
        this.jf=jf;
        init();
    }

    private void init()
    {
        panel =new JPanel();
        sentExpectVal=new JButton("发送目标值");
        expectVal=new JTextField();
        expectValTag=new JLabel("目标值");
        expectValTag.setFont(new Font("黑体",Font.PLAIN,20));
        expectValTag.setHorizontalAlignment(SwingConstants.CENTER);
        panel.setBackground(Color.WHITE);
        PidLabel=new JLabel("PID目标值:");
        PidLabel.setFont(new Font("黑体",Font.PLAIN,20));
        p=new JLabel("P: ");
        p.setFont(new Font("黑体",Font.PLAIN,20));
        p.setHorizontalAlignment(SwingConstants.CENTER);
        i=new JLabel("I: ");
        i.setFont(new Font("黑体",Font.PLAIN,20));
        i.setHorizontalAlignment(SwingConstants.CENTER);
        d=new JLabel("D: ");
        d.setFont(new Font("黑体",Font.PLAIN,20));
        d.setHorizontalAlignment(SwingConstants.CENTER);
        P=new JTextField();
        I=new JTextField();
        D=new JTextField();
        P.setFont(new Font("黑体",Font.PLAIN,20));
        I.setFont(new Font("黑体",Font.PLAIN,20));
        D.setFont(new Font("黑体",Font.PLAIN,20));
        jButton=new JButton("发送PID");
        jButton.addActionListener(this);
        ioStreamOperation=new IOStreamOperation();
        sentExpectVal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(serialPort==null)
                {
                    JDialog jDialog=new JDialog(jf,"Error",true);
                    jDialog.setBounds(500,240,160,100);
                    jDialog.setLayout(new FlowLayout());
                    jDialog.add(new JLabel("未打开串口，请重试"));
                    jDialog.setVisible(true);
                }
                if(ioStreamOperation.getSerialPort()==null)
                {
                    ioStreamOperation.setSerialPort(serialPort);
                }
                String expectValStr=expectVal.getText();
                if(true==ioStreamOperation.writeData(ioStreamOperation.makeCurrentExpectedValDataFramePackage(expectValStr)))
                {
                    JDialog jDialog=new JDialog(jf,"Succeed",true);
                    jDialog.setBounds(500,240,160,100);
                    jDialog.setLayout(new FlowLayout());
                    jDialog.add(new JLabel("目标值设置成功"));
                    jDialog.setVisible(true);
                }else {
                    JDialog jDialog=new JDialog(jf,"Fail",true);
                    jDialog.setBounds(500,240,160,100);
                    jDialog.setLayout(new FlowLayout());
                    jDialog.add(new JLabel("目标值设置失败"));
                    jDialog.setVisible(true);
                }
            }
        });
    }

    public JPanel getIoPanel()
    {
        panel.setLayout(new GridLayout(6,2));
        panel.add(PidLabel);
        panel.add(new JLabel());
        panel.add(p);
        panel.add(P);
        panel.add(i);
        panel.add(I);
        panel.add(d);
        panel.add(D);


        panel.add(expectValTag);
        panel.add(expectVal);
//        panel.add(new JLabel());
//        panel.add(new JLabel());
        panel.add(jButton);
        panel.add(sentExpectVal);

        return panel;
    }

    public void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(serialPort==null)
        {
            JDialog jDialog=new JDialog(jf,"Error",true);
            jDialog.setBounds(500,240,160,100);
            jDialog.setLayout(new FlowLayout());
            jDialog.add(new JLabel("未打开串口，请重试"));
            jDialog.setVisible(true);
        }
        String pVal=P.getText();
        String iVal=I.getText();
        String dVal=D.getText();
        if(ioStreamOperation.getSerialPort()==null)
        {
            ioStreamOperation.setSerialPort(serialPort);
        }
        if(true==ioStreamOperation.writeData(ioStreamOperation.makeCurrentPidDateFramePackage(pVal,iVal,dVal)))
        {
            JDialog jDialog=new JDialog(jf,"Succeed",true);
            jDialog.setBounds(500,240,160,100);
            jDialog.setLayout(new FlowLayout());
            jDialog.add(new JLabel("PID设置成功"));
            jDialog.setVisible(true);
        }else {
            JDialog jDialog=new JDialog(jf,"Fail",true);
            jDialog.setBounds(500,240,160,100);
            jDialog.setLayout(new FlowLayout());
            jDialog.add(new JLabel("PID设置失败"));
            jDialog.setVisible(true);
        }
    }
}
