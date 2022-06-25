package presenters;

import exceptions.EmptyProcessNameException;
import exceptions.EmptyProcessTimeException;
import exceptions.RepeatedNameException;
import models.MyProcess;
import models.OperatingSystem;
import views.AddProcessDialog;
import views.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Presenter implements ActionListener {

    private AddProcessDialog addProcessDialog;
    private OperatingSystem operatingSystem;
    private MainFrame mainFrame;

    public Presenter(){
        operatingSystem = new OperatingSystem();
        initMainFrame();
    }

    private void initMainFrame(){
        mainFrame = new MainFrame(this);
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (Events.valueOf(e.getActionCommand())){
            case ADD_PROCESS:
                manageAddAction();
                break;
            case ACCEPT:
                manageAcceptAction();
                break;
            case CANCEL:
                manageCancelAction();
                break;
            case EXIT:
                manageExitAction();
                break;
        }
    }

    private void manageAddAction(){
        addProcessDialog = new AddProcessDialog(this, false);
        addProcessDialog.setVisible(true);
    }

    private void manageAcceptAction() {
        try {
            operatingSystem.verifyProcessName(addProcessDialog.getProcessName());
            boolean[] states = {addProcessDialog.getIsBlocked(), addProcessDialog.getIsSuspendedBlocked(),
                                addProcessDialog.getIsSuspendedReady()};
            MyProcess newProcess = new MyProcess(addProcessDialog.getProcessName(), addProcessDialog.getProcessTime(), states);
            operatingSystem.addProcess(newProcess);
            addProcessDialog.dispose();
            mainFrame.updateProcesses(operatingSystem.getProcessQueue());
        } catch (NumberFormatException | RepeatedNameException | EmptyProcessTimeException | EmptyProcessNameException e) {
            JOptionPane.showMessageDialog(mainFrame, e.getMessage(), "ERROR!!!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void manageCancelAction() {
        addProcessDialog.dispose();
    }

    private void manageExitAction() {
        System.exit(0);
    }
}
