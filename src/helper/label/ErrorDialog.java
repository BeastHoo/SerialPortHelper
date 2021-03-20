/*
 * STARTSHIP 20/3/19
 * @author HEYUQIAN FROM CTGU
 */
package helper.label;

import gnu.io.CommPort;

import javax.swing.*;
import java.awt.*;

public class ErrorDialog extends JDialog {
    public ErrorDialog(JFrame jf, CommPort commPort)
    {
        super(jf,"Error",true);
        Container con=getContentPane();
//        setLayout(new FlowLayout());
        con.add(new JLabel("当前端口"+commPort.getName()+"不是串口"));
        setBounds(420,250,100,100);
    }
}
