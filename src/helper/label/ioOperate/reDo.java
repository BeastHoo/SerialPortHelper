package helper.label.ioOperate;

import gnu.io.SerialPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class reDo implements ActionListener {
   private JButton Redo;
   private IOStreamOperation ioStreamOperation;
   private SerialPort serialPort;
   private JFrame jf;

    public IOStreamOperation getIoStreamOperation() {
        return ioStreamOperation;
    }

    public void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public void setIoStreamOperation(IOStreamOperation ioStreamOperation) {
        this.ioStreamOperation = ioStreamOperation;
    }

    public reDo(JFrame jf)
   {
       this.jf=jf;
   }
   public JButton returnReDoButton()
   {
       Redo=new JButton("复位");
       Redo.addActionListener(this);
       return Redo;
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
        ioStreamOperation.actRedo();
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException interruptedException) {
//            interruptedException.printStackTrace();
//        }
//        ioStreamOperation.writeData(ioStreamOperation.makeStartEngineDataFramPackage());

    }
}
