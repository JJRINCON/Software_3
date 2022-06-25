package views;

import models.MyProcess;
import presenters.Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProcessPanel extends MyGridPanel {

    private static final String LB_NAME_TXT = "Nombre: ";
    private static final String LB_TIME_TXT = "Tiempo: ";
    private static final String TIME_UNITS_TXT = " UT";
    private static final String LB_IS_BLOCKED_TXT = "Bloqueo: ";
    private static final String EDIT_BTN_TXT = "Editar";
    private static final String DELETE_BTN_TXT = "Eliminar";
    public static final Color WHITE_COLOR = Color.decode("#FDFEFE");
    private static final String LB_IS_SUSPENDED_READY_TXT = "Suspendido listo";
    private static final String LB_IS_SUSPENDED_BLOCKED_TXT = "Suspendido bloqueado";

    public ProcessPanel(MyProcess process, ActionListener listener){
        setBackground(Color.decode("#FDFEFE"));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        initComponents(process, listener);
    }

    private void initComponents(MyProcess process, ActionListener listener){
        addComponent(new JLabel(" "), 0, 0, 12, 0.2);
        JLabel lbName = new JLabel(LB_NAME_TXT + process.getName());
        addComponent(lbName, 1, 1, 1, 1);

        JLabel lbTime = new JLabel(LB_TIME_TXT + process.getTime() + TIME_UNITS_TXT);
        addComponent(lbTime, 3,1,1,1);

        initStates(process.isLocked(), process.isSuspended_Ready(), process.isSuspended_Locked());
        initButtons(listener, process.getName());

        addComponent(new JLabel(" "), 0, 2, 12, 0.2);
    }

    private void initStates(boolean isLocked, boolean isSuspendedReady, boolean isSuspendedBlocked){
        JPanel isLockedPanel = createPanel(LB_IS_BLOCKED_TXT, isLocked);
        addComponent(isLockedPanel, 5, 1,1,1);

        JPanel isSuspendedReadyPanel = createPanel(LB_IS_SUSPENDED_READY_TXT, isSuspendedReady);
        addComponent(isSuspendedReadyPanel, 5, 1,1,1);

        JPanel isSuspendedBlockedPanel = createPanel(LB_IS_SUSPENDED_BLOCKED_TXT, isSuspendedBlocked);
        addComponent(isSuspendedBlockedPanel, 5, 1,1,1);
    }

    private void initButtons(ActionListener listener, String name){
        JButton editBtn = createBtn(EDIT_BTN_TXT, Color.BLUE, listener, Events.EDIT_PROCESS.toString(), name);
        addComponent(editBtn, 8,1,1,0.5);
        JButton deleteButton = createBtn(DELETE_BTN_TXT, Color.RED, listener, Events.DELETE_PROCESS.toString(), name);
        addComponent(deleteButton, 10, 1,1,0.5);
    }

    private JPanel createPanel(String txt, boolean status){
        JPanel panel = new JPanel(new GridLayout(1,2));
        panel.setBackground(WHITE_COLOR);
        JLabel lb = new JLabel(txt);
        panel.add(lb);
        panel.add(new SymbolPanel(status, false));
        return panel;
    }

    private JButton createBtn(String txt, Color color, ActionListener listener, String command, String name){
        JButton btn = new JButton(txt);
        btn.setName(name);
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.addActionListener(listener);
        btn.setActionCommand(command);
        return btn;
    }
}
