/*
 * STARTSHIP 20/3/19
 * @author HEYUQIAN FROM CTGU
 */
package helper.label;
//import gnu.io.CommPort;
import gnu.io.*;
import helper.label.ioOperate.IOStreamOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;

public class PIDHelper  {

//    private SerialPort serialPort;
    private JPanel p1;
    private JPanel p2;
    private JPanel p3;
    private JPanel p4;
    private JComboBox Port_Name;
    private JComboBox BauldRate;
    private JComboBox dataBits;
    private JComboBox checkBits;
    private JComboBox stopBits;
//    private ImageIcon Icon;
    private JButton flushButton;
    private JButton actionButton;
//    private openPortListener portListener;

    private SerialPort serialPort;
    private JFrame jf;
    public PIDHelper(JFrame jf)
    {
        init();
        this.jf=jf;
    }

    private void init()
    {
        stopBits=new JComboBox();
        checkBits = new JComboBox();
        dataBits = new JComboBox();
        Port_Name=new JComboBox();
        BauldRate = new JComboBox();
        p1=new JPanel();
        p2=new JPanel();
        p3=new JPanel();
        p4=new JPanel();
//        Icon =new ImageIcon("helper/label/refresh.ico");
//        Icon.setImage(Icon.getImage().getScaledInstance(5,5,Image.SCALE_DEFAULT));
        flushButton=new JButton("刷新");
//        flushButton.setSize(2,2);
        actionButton=new JButton("打开串口");
//        portListener=new openPortListener();
        serialPort=null;
    }


    public JSplitPane style()
    {

        String [] rate=new String[]{"50","75","100","150","300","600","1200","2400","4800","9600","19200","38400"};
        ArrayList<String> portName= getPortName();
        String DateBits=new String("5678");
        String [] checkParity=new String[]{"NoParity","EvenParity","OddParity","SpaceParity","MarkParity"};
        String [] stopParity=new String[]{"1","1.5","2"};
        for(String str:portName)
        {
            Port_Name.addItem(str);
        }
        for(String i:rate)
        {
            BauldRate.addItem(i);
        }
        for(int i=0;i<DateBits.length();i++)
        {
            dataBits.addItem(DateBits.charAt(i));
        }
        for(String str:checkParity)
        {
            checkBits.addItem(str);
        }
        for(String str:stopParity)
        {
            stopBits.addItem(str);
        }


        



        p1.setLayout(new GridLayout(7,2,5,10));
        p1.setBackground(Color.WHITE);
        p2.setBackground(Color.WHITE);
        p3.setBackground(Color.WHITE);
        p4.setBackground(Color.WHITE);
        JLabel title=new JLabel("串口设置:");
        title.setFont(new Font("黑体",Font.PLAIN,16));

        title.setHorizontalAlignment(SwingConstants.LEFT);
        JLabel jLabel=new JLabel("串口号");
        jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel jLabel1=new JLabel("波特率 ");
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel jLabel2=new JLabel("数据位 ");
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel jLabel3=new JLabel("校 验 ");
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel jLabel4=new JLabel("停止位 ");
        jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
        p1.add(title);
        p1.add(new JLabel());
        p1.add(jLabel);
        p1.add("串口号:",Port_Name);
        p1.add(jLabel1);
        p1.add("波特率:",BauldRate);
        p1.add(jLabel2);
        p1.add(dataBits);
        p1.add(jLabel3);
        p1.add(checkBits);
        p1.add(jLabel4);
        p1.add(stopBits);
        //刷新数据
        flushButton.addActionListener(new flushListener(this));
        p1.add(flushButton);


        //打开串口
//        portListener.getVal(Port_Name,BauldRate,dataBits,checkBits,stopBits,jf,serialPort);
//        actionButton.addActionListener(portListener);
        actionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton()==e.BUTTON1)
                {
                    if(serialPort!=null)
                    {
                        serialPort.close();
//            serialPort1=serialPort;
                        return;
                    }


                    CommPort commPort=null;
                    CommPortIdentifier portIdentifier=null;
                    String trim0 = Port_Name.getSelectedItem().toString().trim();
                    String trim1 = BauldRate.getSelectedItem().toString().trim();
                    String trim2 = dataBits.getSelectedItem().toString().trim();
                    String trim3 = checkBits.getSelectedItem().toString().trim();
                    String trim4 = stopBits.getSelectedItem().toString().trim();
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
                         * 设置串口参数：setSerialPortParams( int b, int d, int s, int p )
                         * b：波特率（baudrate）
                         * d：数据位（datebits），SerialPort 支持 5,6,7,8
                         * s：停止位（stopbits），SerialPort 支持 1,2,3
                         * p：校验位 (parity)，SerialPort 支持 0,1,2,3,4
                         * 如果参数设置错误，则抛出异常：gnu.io.UnsupportedCommOperationException: Invalid Parameter
                         * 此时必须关闭串口，否则下次 portIdentifier.open 时会打不开串口，因为已经被占用
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
//                    System.out.println(serialPort.getBaudRate()+" "+serialPort.getDataBits());
                    IOStreamOperation ioStreamOperation=new IOStreamOperation(serialPort);
                    ioStreamOperation.writeData(("abc").getBytes());
                }
            }
        });
        p1.add(actionButton);
//        serialPort = portListener.returnSerialPort();




        //PID选项卡划分为左右两部分
        JSplitPane jSplitPane=new JSplitPane();
        jSplitPane.setDividerLocation(180);

        //将左边的划分区域再次划分——一分为二
        JSplitPane jSplitPane1=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setDividerLocation(240);
        jSplitPane1.setDividerSize(8);
        jSplitPane.setLeftComponent(jSplitPane1);
        jSplitPane1.setLeftComponent(p1);


        //二分为三
        JSplitPane jSplitPane2=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setDividerLocation(180);
        jSplitPane2.setDividerSize(8);
        jSplitPane1.setRightComponent(jSplitPane2);
        jSplitPane2.setLeftComponent(p2);
        jSplitPane2.setRightComponent(p3);
        jSplitPane.setRightComponent(p4);
        return jSplitPane;
    }
    public ArrayList<String> getPortName(){
        ArrayList<String> portName=new ArrayList<>();
        //获取当前可用的端口，方法由CommPortIdentifier提供
        Enumeration<CommPortIdentifier> portIdentifierEnumeration=CommPortIdentifier.getPortIdentifiers();
        while(portIdentifierEnumeration.hasMoreElements())
        {
            portName.add(portIdentifierEnumeration.nextElement().getName());
        }
        return portName;

    }
    public JComboBox getPort_NameComponent()
    {
        return Port_Name;
    }
    public SerialPort getSerialPort()
    {
        return serialPort;
    }
}
