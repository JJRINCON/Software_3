package presenters;

import exceptions.EmptyProcessNameException;
import exceptions.EmptyProcessTimeException;
import exceptions.RepeatedNameException;
import models.MyProcess;
import models.OperatingSystem;
import views.AddProcessDialog;
import views.AddProcessPanel;
import views.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Presenter implements ActionListener {

    private AddProcessDialog addProcessDialog;
    private AddProcessDialog editProcessDialog;
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
            case EDIT_PROCESS:
                manageEditProcessAction(e);
                break;
            case ACCEPT_EDIT:
                manageAcceptEditAction(e);
                break;
            case CANCEL_EDIT:
                manageCancelEditAction();
                break;
            case DELETE_PROCESS:
                manageDeleteProcessAction(e);
                break;
            case INIT_SIMULATION:
                manageInitSimulationAction();
                break;
            case NEW_SIMULATION:
                manageNewSimulationAction();
                break;
            case EXIT:
                manageExitAction();
                break;
        }
    }

    private void manageNewSimulationAction() {
        operatingSystem = new OperatingSystem();
        mainFrame.newSimulation();
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
            MyProcess newProcess = new MyProcess(addProcessDialog.getProcessName(), addProcessDialog.getProcessTime(),
                                                states);
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

    private void manageEditProcessAction(ActionEvent e) {
        String processName = ((JButton) e.getSource()).getName();
        MyProcess process = operatingSystem.search(processName);
        editProcessDialog = new AddProcessDialog(this, true);
        editProcessDialog.setInitialInfo(process);
        editProcessDialog.setVisible(true);
    }

    private void manageAcceptEditAction(ActionEvent event) {
        String actualName = ((JButton) event.getSource()).getName();
        try {
            if(!actualName.equals(editProcessDialog.getProcessName())){
                operatingSystem.verifyProcessName(editProcessDialog.getProcessName());
            }
            boolean[] states = {editProcessDialog.getIsBlocked(), editProcessDialog.getIsSuspendedBlocked(),
                    editProcessDialog.getIsSuspendedReady()};
            operatingSystem.editProcess(actualName, editProcessDialog.getProcessName(),
                    editProcessDialog.getProcessTime(), states);
            editProcessDialog.dispose();
            mainFrame.updateProcesses(operatingSystem.getProcessQueue());
        } catch (EmptyProcessNameException | RepeatedNameException | EmptyProcessTimeException e) {
            throw new RuntimeException(e);
        }
    }

    private void manageCancelEditAction() {
        editProcessDialog.dispose();
    }

    private void manageDeleteProcessAction(ActionEvent e) {
        String processName = ((JButton) e.getSource()).getName();
        operatingSystem.deleteProccess(processName);
        mainFrame.updateProcesses(operatingSystem.getProcessQueue());
    }

    private void manageInitSimulationAction() {
        if(!operatingSystem.getProcessQueue().isEmpty()){
            operatingSystem.startSimulation();
            mainFrame.initReportsPanel(operatingSystem.getReadyAndDespachado(), operatingSystem.getReadyAndDespachado(),
                                        operatingSystem.getExecuting(), operatingSystem.getToLocked(),
                                        operatingSystem.getLocked(), operatingSystem.getLockedToReady(),
                                        operatingSystem.getLockedToSuspendedLocked(), operatingSystem.getSuspendedLocked(),
                                        operatingSystem.getSuspendedLockedToLocked(), operatingSystem.getSuspendedLockedToSuspendedReady(),
                                        operatingSystem.getToSuspendedReady(), operatingSystem.getSuspendedReady(),
                                        operatingSystem.getSuspendedReadyToReady(), operatingSystem.getExpired(),
                                        operatingSystem.getProcessTerminated());
        }else{
            JOptionPane.showMessageDialog(mainFrame, "Debe haber almenos 1 proceso para poder iniciar la simulacion",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void manageExitAction() {
        System.exit(0);
    }
}
