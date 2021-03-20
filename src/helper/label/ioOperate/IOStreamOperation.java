/**
 * 此类用于串口间的数据通信操作，主要是input和output
 * STARTSHIP: 2021/3/20
 * @author HEYUQIAN
 */

package helper.label.ioOperate;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;
//import java.io.InputStreamReader;

public class IOStreamOperation implements SerialPortEventListener{

    /*
     *  SerialPortEvent.BI:/*Break interrupt,通讯中断
     *  SerialPortEvent.OE:/*Overrun error，溢位错误
     *  SerialPortEvent.FE:/*Framing error，传帧错误
     *  SerialPortEvent.PE:/*Parity error，校验错误
     *  SerialPortEvent.CD:/*Carrier detect，载波检测
     *  SerialPortEvent.CTS:/*Clear to send，清除发送
     *  SerialPortEvent.DSR:/*Data set ready，数据设备就绪
     *  SerialPortEvent.RI:/*Ring indicator，响铃指示
     *  SerialPortEvent.OUTPUT_BUFFER_EMPTY:/*Output buffer is empty，输出缓冲区清空
     *  SerialPortEvent.DATA_AVAILABLE:
     */
    private SerialPort serialPort;
    //串口输入流引用
    private InputStream inputStream;
    //串口输出流引用
    private OutputStream outputStream;
    public IOStreamOperation(SerialPort serialPort)
    {
        this.serialPort=serialPort;
        inputStream=null;
        outputStream=null;
        init();
    }


    private void init()
    {
        try {
            serialPort.addEventListener(this);
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }


    }

//    向串口写数据
    public void writeData(byte[] byteBuffer)
    {
        try {
            if(serialPort==null)
            {
                //错误弹窗
                return;
            }
//            inputStream=serialPort.getInputStream();
            outputStream=serialPort.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.write(byteBuffer);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null)
            {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if(serialPortEvent.getEventType()==SerialPortEvent.DATA_AVAILABLE)
        {
            byte[] readBuffer=new byte[200];

            try {
                int numBytes=0;
                while(inputStream.available()>0)
                {
                    numBytes=inputStream.read(readBuffer);
                }
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

