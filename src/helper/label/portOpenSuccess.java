/*
  STARTSHIP 20/3/19
  @author HEYUQIAN FROM CTGU
 */
package helper.label;

import gnu.io.CommPort;

import javax.swing.*;
import java.awt.*;

public class portOpenSuccess extends JDialog {
    public portOpenSuccess(JFrame jf,CommPort commPort)
    {
        super(jf,"Succeed",true);
        Container con=getContentPane();
        con.add(new JLabel("当前端口 "+commPort.getName()+" 打开成功"));
        setBounds(500,240,160,100);
    }
}
