/*
 * STARTSHIP 20/3/19
 * @author HEYUQIAN FROM CTGU
 */
package helper.label;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class flushListener implements ActionListener {
    private ArrayList<String> portName;
    private JComboBox Port_Name;
    private PIDHelper pidHelper;
    public  flushListener(PIDHelper pidHelper)
    {
        this.pidHelper = pidHelper;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        portName=pidHelper.getPortName();
        Port_Name=pidHelper.getPort_NameComponent();
        Port_Name.removeAllItems();
        Port_Name.repaint();
        for(String str:portName)
            Port_Name.addItem(str);

    }
}
