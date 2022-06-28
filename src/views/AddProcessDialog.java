package views;


import exceptions.EmptyProcessNameException;
import exceptions.EmptyProcessTimeException;
import models.MyProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddProcessDialog extends JDialog {

    private AddProcessPanel addProcessPanel;

    public AddProcessDialog(ActionListener listener, boolean isEditing) {
        setSize(400, 400);
        setModal(true);
        setLayout(new BorderLayout());
        setResizable(false);
        setUndecorated(true);
        addProcessPanel = new AddProcessPanel(listener, isEditing);
        add(addProcessPanel);
        setLocationRelativeTo(null);
    }

    public String getProcessName() throws EmptyProcessNameException {
        return addProcessPanel.getProcessName();
    }

    public int getProcessTime() throws EmptyProcessTimeException, NumberFormatException{
        return addProcessPanel.getProcessTime();
    }

    public boolean getIsBlocked(){
        return addProcessPanel.getIsBlocked();
    }

    public boolean getIsSuspendedReady(){
        return addProcessPanel.getIsSuspendedReady();
    }

    public boolean getIsSuspendedBlocked(){
        return addProcessPanel.getIsSuspendedBlocked();
    }

    public void setInitialInfo(MyProcess process){
        boolean[] states = {process.isLocked(), process.isSuspended_Locked(), process.isSuspended_Ready()};
        addProcessPanel.setInitialInfo(process.getName(), String.valueOf((int)process.getTime()), states);
    }
}
