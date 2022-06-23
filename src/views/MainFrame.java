package views;

import javax.swing.*;

public class MainFrame extends JFrame {

    private static final String TITLE = "Software 3";

    public MainFrame(){
        setTitle(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
    }
}
