/*
 * STARTSHIP 20/3/19
 * @author HEYUQIAN FROM CTGU
 * Attention: This is NEVER USED!
 */
package helper.label;

import gnu.io.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class openPortListener implements ActionListener {
    private JComboBox val0,val1,val2,val3,val4;
    private SerialPort serialPort;
//    private SerialPort retSerialPort;
    private  JFrame jf;
    public void getVal(JComboBox val0,JComboBox val1,JComboBox val2,JComboBox val3,
                       JComboBox val4,JFrame jf,SerialPort serialPort)
    {
        this.val0=val0;
        this.val1=val1;
        this.val2=val2;
        this.val3=val3;
        this.val4=val4;
        this.serialPort=serialPort;
        this.jf=jf;
    }

    public SerialPort returnSerialPort(){
        return serialPort;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        SerialPort serialPort1;
        if(serialPort!=null)
        {
            serialPort.close();
//            serialPort1=serialPort;
            return;
        }


        CommPort commPort=null;
        CommPortIdentifier portIdentifier=null;
        String trim0 = val0.getSelectedItem().toString().trim();
        String trim1 = val1.getSelectedItem().toString().trim();
        String trim2 = val2.getSelectedItem().toString().trim();
        String trim3 = val3.getSelectedItem().toString().trim();
        String trim4 = val4.getSelectedItem().toString().trim();
        int checkBit=0;

        try {
             portIdentifier=CommPortIdentifier.getPortIdentifier(trim0);

        } catch (NoSuchPortException noSuchPortException) {
            noSuchPortException.printStackTrace();
        }
        try {
            commPort=portIdentifier.open(trim0,5000);
        } catch (PortInUseException portInUseException) {
            portInUseException.printStackTrace();
        }
        switch (trim3)
        {
            case "NoParity":
                checkBit=SerialPort.PARITY_NONE;
                break;
            case "EvenParity":
                checkBit=SerialPort.PARITY_EVEN;
                break;
            case "OddParity":
                checkBit=SerialPort.PARITY_ODD;
                break;
            case "SpaceParity":
                checkBit=SerialPort.PARITY_SPACE;
                break;
            case "MarkParity":
                checkBit=SerialPort.PARITY_MARK;
                break;
            default:
                break;
        }
        if (commPort instanceof SerialPort) {
            serialPort = (SerialPort) commPort;
            /**
             * ?????????????????????setSerialPortParams( int b, int d, int s, int p )
             * b???????????????baudrate???
             * d???????????????datebits??????SerialPort ?????? 5,6,7,8
             * s???????????????stopbits??????SerialPort ?????? 1,2,3
             * p???????????? (parity)???SerialPort ?????? 0,1,2,3,4
             * ?????????????????????????????????????????????gnu.io.UnsupportedCommOperationException: Invalid Parameter
             * ??????????????????????????????????????? portIdentifier.open ?????????????????????????????????????????????
             */
            try {
                serialPort.setSerialPortParams(Integer.parseInt(trim1),Integer.parseInt(trim2),Integer.parseInt(trim4),checkBit);
                new portOpenSuccess(jf,commPort).setVisible(true);

            } catch (UnsupportedCommOperationException unsupportedCommOperationException) {
                unsupportedCommOperationException.printStackTrace();
                if(serialPort!=null)
                {
                    serialPort.close();
                }
            }

        }
        else {
            new ErrorDialog(jf,commPort).setVisible(true);

        }
    }
}
