package helper.label;

import gnu.io.SerialPort;
import helper.label.ioOperate.IOStreamOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class startEngine implements ActionListener {
    private JButton start;
//    private JPanel panel;
    private IOStreamOperation ioStreamOperation;
    private SerialPort serialPort;
    private JFrame jf;
    public startEngine(JFrame jf)
    {
        this.jf=jf;
        init();
    }
    public void setSerialPort(SerialPort serialPort)
    {
        this.serialPort=serialPort;
    }
    private void init()
    {
        ioStreamOperation=new IOStreamOperation();
        start=new JButton("启动电机");
        start.setFont(new Font("黑体", Font.PLAIN,16));
        start.addActionListener(this);
//        panel=new JPanel();
    }
    public void setIoStreamOperation(IOStreamOperation ioStreamOperation)
    {
        this.ioStreamOperation=ioStreamOperation;
    }
    public JButton startEngineButton()
    {

        return start;
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
        if(ioStreamOperation.getSerialPort()==null||ioStreamOperation.getSerialPort()!=serialPort)
        {
            ioStreamOperation.setSerialPort(serialPort);
        }
        if(true==ioStreamOperation.writeData(ioStreamOperation.makeStartEngineDataFramPackage()))
        {
            JDialog jDialog=new JDialog(jf,"Succeed",true);
            jDialog.setBounds(500,240,160,100);
            jDialog.setLayout(new FlowLayout());
            jDialog.add(new JLabel("电机启动成功"));
            jDialog.setVisible(true);
        }else{
            JDialog jDialog=new JDialog(jf,"Fail",true);
            jDialog.setBounds(500,240,160,100);
            jDialog.setLayout(new FlowLayout());
            jDialog.add(new JLabel("电机启动失败"));
            jDialog.setVisible(true);
        }
    }
}

//修改  添加复位键，关闭键还有停止键
//将此处的panel为component,直接加入一个新的panel里面
