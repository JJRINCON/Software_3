package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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

    public void newSimulation(){
        getContentPane().remove(mainPanel);
        mainPanel = new MainPanel(listener);
        add(mainPanel);
        getContentPane().revalidate();
    }
}
