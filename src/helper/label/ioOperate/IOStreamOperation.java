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
    public IOStreamOperation()
    {
        inputStream=null;
        outputStream=null;
//        init();
    }

    public void setSerialPort(SerialPort serialPort)
    {
        this.serialPort=serialPort;
        init();
    }

    public SerialPort getSerialPort()
    {
        return serialPort;
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
    public boolean writeData(byte[] byteBuffer)
    {
        try {
            if(serialPort==null)
            {
                //错误弹窗
                return false;
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
            return false;
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
        return true;
    }

    public byte[] makeCurrentPidDateFramePackage(String p,String i,String d)
    {
        byte [] dataFramePackage=new byte[16];
        dataFramePackage[0]= (byte) 0xAA;
        dataFramePackage[1]=0x07;
        int j=0;
        float Pf=Float.parseFloat(p);
        float If=Float.parseFloat(i);
        float Df=Float.parseFloat(d);
        byte [] PfB=getByteArray(Pf);
        for(j=0;j<PfB.length;j++)
        {
            dataFramePackage[2+j]=PfB[j];
        }
        byte [] IfB=getByteArray(If);
        for(j=0;j<IfB.length;j++)
        {
            dataFramePackage[6+j]=IfB[j];
        }

        byte [] DfB=getByteArray(Df);
        for(j=0;j<DfB.length;j++)
        {
            dataFramePackage[10+j]=DfB[j];
        }
//        byte [] PfB=hexToByteArray(p);
//        byte [] IfB=hexToByteArray(i);
//        byte [] DfB=hexToByteArray(d);
//        for(j=0;j<PfB.length;j++)
//        {
//            dataFramePackage[2+j]=PfB[j];
//        }
//
//        for(j=0;j<IfB.length;j++)
//        {
//            dataFramePackage[6+j]=PfB[j];
//        }
//
//        for(j=0;j<PfB.length;j++)
//        {
//            dataFramePackage[10+j]=PfB[j];
//        }

        dataFramePackage[14]=Check_CS(dataFramePackage);
        dataFramePackage[15]=(byte)0x2f;

        return dataFramePackage;
    }

    public byte[] makeCurrentExpectedValDataFramePackage(String expectValStr)
    {
        byte[] dataPackage=new byte[16];
        dataPackage[0] = (byte) 0xAA;
        dataPackage[1] = 0x08;
        Float f=Float.parseFloat(expectValStr);
        byte [] temp=getByteArray(f);
        for(int i=0;i<temp.length;i++)
        {
            dataPackage[2+i]=temp[i];
        }
        for(int i=6;i<14;i++)
        {
            dataPackage[i]=0x55;
        }
        dataPackage[14]=Check_CS(dataPackage);
        dataPackage[15]=0x2F;
        return dataPackage;
    }

    public byte[] makeStartEngineDataFramPackage()
    {
        byte [] dataFramePackage=new byte[16];
        int[] originData=new int[]{0xAA,0X0A,0x0A,0x55,0x55,0x55,0x55,0x55,0x55,0x55,0x55,0x55,0x55,0x55,0xBB,0x2F};
        for(int i=0;i<16;i++)
        {
            dataFramePackage[i] = (byte) originData[i];
        }
        return dataFramePackage;
    }


    public static byte[] getByteArray(float f) {
        int intbits = Float.floatToIntBits(f);//将float里面的二进制串解释为int整数
        return getByteArray(intbits);
    }



    public static byte[] getByteArray(int i) {
        byte[] b = new byte[4];
        b[0] = (byte) ((i & 0xff000000) >> 24);
        b[1] = (byte) ((i & 0x00ff0000) >> 16);
        b[2] = (byte) ((i & 0x0000ff00) >> 8);
        b[3] = (byte)  (i & 0x000000ff);
        return b;
    }

    public static byte Check_CS(byte[] Abyte)
    {
        byte result ;
            int num = 0;
            for (int i = 1; i < Abyte.length; i++)
            {
                num = (num + Abyte[i]) % 256;
            }
            result = (byte)num;
//            result = 0;
            result= (byte) (result & 0x00ff);
        return result;
    }

//    public static void recHexCodeInverse(byte [] hexByte)
//    {
//
//    }

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
//                    recHexCodeInverse(readBuffer);
                }
                System.out.println(readBuffer);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

