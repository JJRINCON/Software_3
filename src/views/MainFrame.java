package views;

import models.MyProcess;
import models.Queue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    private static final String TITLE = "Software 3";
    private ActionListener listener;
    private MainPanel mainPanel;

    public MainFrame(ActionListener listener){
        this.listener = listener;
        setUndecorated(true);
        getContentPane().setLayout(new GridLayout(1,1));
        setTitle(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        initProcessesPanel();
    }

    private void initProcessesPanel(){
        mainPanel = new MainPanel(listener);
        add(mainPanel);
    }

    public void initReportsPanel(ArrayList<MyProcess> readyProcess, ArrayList<MyProcess> dispatchedProcess,
                                 ArrayList<MyProcess> executingProcess, ArrayList<MyProcess> toLockedProcess,
                                 ArrayList<MyProcess> lockedProcess, ArrayList<MyProcess> lockedToReady,
                                 ArrayList<MyProcess> lockedToSuspendedLocked, ArrayList<MyProcess> suspendedLocked,
                                 ArrayList<MyProcess> suspendedLockedToLocked, ArrayList<MyProcess> suspendedLockedToSuspendedReady,
                                 ArrayList<MyProcess> toSuspendedReady, ArrayList<MyProcess> suspendedReady,
                                 ArrayList<MyProcess> suspendedReadyToReady, ArrayList<MyProcess> expiredProcess,
                                 ArrayList<MyProcess> terminatedProcess){
        mainPanel.initReportsPanel(readyProcess, dispatchedProcess, executingProcess, toLockedProcess,
                lockedProcess, lockedToReady, lockedToSuspendedLocked, suspendedLocked,
                suspendedLockedToLocked, suspendedLockedToSuspendedReady, toSuspendedReady,
                suspendedReady, suspendedReadyToReady, expiredProcess, terminatedProcess);
    }

    public void updateProcesses(Queue<MyProcess> processQueue){
        mainPanel.updateProcesses(processQueue);
    }

    public void newSimulation(){
        getContentPane().remove(mainPanel);
        mainPanel = new MainPanel(listener);
        add(mainPanel);
        getContentPane().revalidate();
    }
}
