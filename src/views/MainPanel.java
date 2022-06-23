package views;

import presenters.Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainPanel extends MyGridPanel {

    public static final String EXIT_BTN_TXT = "Salir";
    public static final String START_SIMULATION_BTN_TXT = "Iniciar Simulacion";
    private ProcessesPanel processesPanel;
    //private ReportsPanel reportsPanel;
    private MyGridPanel startSimulationPanel;
    private ActionListener listener;

    public MainPanel(ActionListener listener) {
        this.listener = listener;
        setBackground(Color.decode("#FDFEFE"));
        initExitBtn();
        processesPanel = new ProcessesPanel(listener);
        addComponent(processesPanel, 0, 1, 2, 1);
        initStartSimulationPanel();
    }

    private void initExitBtn(){
        MyGridPanel exitBtnPanel = new MyGridPanel();
        exitBtnPanel.setBackground(Color.decode("#34495E"));
        JButton exitBtn = createBtn(EXIT_BTN_TXT, Color.RED, listener, Events.EXIT.toString());
        exitBtnPanel.addComponentWithInsets(exitBtn, 11, 1, 1, 0.1, new Insets(5,0,5,0));
        addComponent(exitBtnPanel, 0,0, 12, 0.005);
    }

    public void initStartSimulationPanel(){
        //hideReportsPanel();
        startSimulationPanel = new MyGridPanel();
        JButton startSimulationBtn = createBtn(START_SIMULATION_BTN_TXT, Color.decode("#2980B9"),
                                                listener, Events.INIT_SIMULATION.toString());
        startSimulationPanel.setBackground(Color.decode("#FDFEFE"));
        startSimulationPanel.addComponent(new JLabel(" "), 0, 3, 12, 0.3);
        startSimulationPanel.addComponent(startSimulationBtn, 5, 4, 5, 0.05);
        startSimulationPanel.addComponent(new JLabel(" "), 0, 5, 12, 0.4);
        addComponent(startSimulationPanel, 2, 1, 9, 1);
        updateUI();
    }
    /*
    private void hideReportsPanel(){
        if(reportsPanel != null){
            this.remove(reportsPanel);
        }
    }

    public void initReportsPanel(ArrayList<MyProcess> readyProcess, ArrayList<MyProcess> dispatchedProcess,
                                 ArrayList<MyProcess> executingProcess, ArrayList<MyProcess> toLockedProcess,
                                 ArrayList<MyProcess> lockedProcess, ArrayList<MyProcess> wakeUpProcess,
                                 ArrayList<MyProcess> expiredProcess, ArrayList<MyProcess> terminatedProcess){
        this.remove(startSimulationPanel);
        this.remove(processesPanel);
        processesPanel.setBorder(BorderFactory.createMatteBorder(2, 2,2,0, Color.BLACK));
        addComponent(processesPanel, 0, 1, 3, 1);
        reportsPanel = new ReportsPanel(readyProcess, dispatchedProcess, executingProcess, toLockedProcess,
                                        lockedProcess, wakeUpProcess, expiredProcess, terminatedProcess, listener);
        addComponent(reportsPanel, 3,1,9,0.8);
        updateUI();
    }*/

    private JButton createBtn(String txt, Color color, ActionListener listener, String command){
        JButton btn = new JButton(txt);
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.addActionListener(listener);
        btn.setActionCommand(command);
        return btn;
    }

    /*public void updateProcesses(Queue<MyProcess> processQueue){
        processesPanel.updateProcesses(processQueue);
    }*/
}
