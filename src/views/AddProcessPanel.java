package views;

import exceptions.EmptyProcessNameException;
import exceptions.EmptyProcessTimeException;
import presenters.Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddProcessPanel extends MyGridPanel{

    private JTextField processNameTxt;
    private JTextField processTimeTxt;
    private JCheckBox isBlockedCb;
    private JButton addBtn;
    private JCheckBox isSuspendedReadyCb;
    private JCheckBox isSuspendedBlockedCb;

    public AddProcessPanel(ActionListener listener, boolean isEditing) {
        setBackground(Color.decode("#FDFEFE"));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        initComponents(listener, isEditing);
    }

    private void initComponents(ActionListener listener, boolean isEditing) {
        initTitle(isEditing);
        initProcessNameTxt();
        initProcessTimeTxt();
        initIsBlockedCb();
        initIsSuspendedReadyCb();
        initIsSuspendedBlockedCb();
        if(isEditing){
            initButtons(listener, Events.ACCEPT_EDIT.toString(), Events.CANCEL_EDIT.toString(), isEditing);
        }else{
            initButtons(listener, Events.ACCEPT.toString(), Events.CANCEL.toString(), isEditing);
        }
    }

    private void initTitle(boolean isEditing){
        String title = isEditing ? "Editar Proceso" : "Nuevo Proceso";
        JLabel titleLb = createLb(title, new Font("Arial", Font.BOLD, 20));
        addComponent(new JLabel(" "), 0, 0, 12, 0.1);
        addComponent(titleLb, 4, 1, 4, 0.2);
        addComponent(new JLabel(" "), 0, 3, 12, 0.1);
    }

    private void initProcessNameTxt(){
        JLabel nameLb = createLb("   Nombre:", new Font("Arial", Font.BOLD, 14));
        addComponent(nameLb, 2, 5, 2, 0.1);
        processNameTxt = new JTextField();
        addComponent(processNameTxt, 4, 5, 6, 0.1);
        addComponent(new JLabel(" "), 0, 6, 11, 0.1);
    }

    private void initProcessTimeTxt() {
        JLabel timeLb = createLb("   Tiempo: ", new Font("Arial", Font.BOLD, 14));
        addComponent(timeLb, 2, 7, 2, 0.1);
        processTimeTxt = new JTextField();
        processTimeTxt.setText("");
        addComponent(processTimeTxt, 4, 7, 6, 0.1);
        addComponent(new JLabel(" "), 0, 8, 11, 0.1);
    }

    private void initIsBlockedCb(){
        JLabel isBlockedLb = createLb("   Bloqueo: ", new Font("Arial", Font.BOLD, 14));
        addComponent(isBlockedLb, 2, 9, 2, 0.1);
        isBlockedCb = new JCheckBox();
        isBlockedCb.setBackground(Color.WHITE);
        isBlockedCb.setHorizontalAlignment(SwingConstants.LEFT);
        addComponent(isBlockedCb, 7, 9, 1, 0.1);
        addComponent(new JLabel(" "), 0, 10, 11, 0.1);
    }

    private void initIsSuspendedReadyCb(){
        JTextArea isSuspendedTx = new JTextArea("   Suspendido\n   Listo : ");
        isSuspendedTx.setFont(new Font("Arial", Font.BOLD, 14));
        isSuspendedTx.setEditable(false);
        addComponent(isSuspendedTx, 2, 11, 2, 0.1);
        isSuspendedReadyCb = new JCheckBox();
        isSuspendedReadyCb.setBackground(Color.WHITE);
        isSuspendedReadyCb.setHorizontalAlignment(SwingConstants.LEFT);
        addComponent(isSuspendedReadyCb, 7, 11, 1, 0.1);
        addComponent(new JLabel(" "), 0, 12, 11, 0.1);
    }

    private void initIsSuspendedBlockedCb(){
        JTextArea isDestroyedTx = new JTextArea("   Suspendido\n   bloqueado : ");
        isDestroyedTx.setFont(new Font("Arial", Font.BOLD, 14));
        isDestroyedTx.setEditable(false);
        addComponent(isDestroyedTx, 2, 13, 2, 0.1);
        isSuspendedBlockedCb = new JCheckBox();
        isSuspendedBlockedCb.setBackground(Color.WHITE);
        isSuspendedBlockedCb.setHorizontalAlignment(SwingConstants.LEFT);
        addComponent(isSuspendedBlockedCb, 7, 13, 1, 0.1);
        addComponent(new JLabel(" "), 0, 14, 11, 0.1);
    }

    private void initButtons(ActionListener listener, String acceptEvent, String cancelEvent, boolean isEditing){
        String addBtnTxt = isEditing ? "Editar" : "Agregar";
        addBtn = createBtn(addBtnTxt, Color.decode("#00D48B"), listener, acceptEvent);
        addComponent(addBtn, 3, 15, 2, 0.12);
        JButton cancelBtn = createBtn("Cancelar", Color.decode("#FA512D"), listener, cancelEvent);
        addComponent(cancelBtn, 7, 15, 2, 0.12);
        addComponent(new JLabel(" "), 0, 16, 11, 0.05);
    }

    private JLabel createLb(String txt, Font font){
        JLabel lb = new JLabel(txt);
        lb.setFont(font);
        return lb;
    }

    private JButton createBtn(String txt, Color color, ActionListener listener, String command){
        JButton btn = new JButton(txt);
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.addActionListener(listener);
        btn.setActionCommand(command);
        return btn;
    }

    public String getProcessName() throws EmptyProcessNameException {
        if(!processNameTxt.getText().isEmpty()){
            return processNameTxt.getText();
        }else{
            throw new EmptyProcessNameException();
        }
    }

    public int getProcessTime() throws EmptyProcessTimeException, NumberFormatException {
        String text = processTimeTxt.getText();
        if(!text.isEmpty()){
            return Integer.parseInt(text);
        }else{
            throw new EmptyProcessTimeException();
        }
    }

    public void setInitialInfo(String name, String time, boolean ... states){
        processNameTxt.setText(name);
        processTimeTxt.setText(time);
        isBlockedCb.setSelected(states[0]);
        isSuspendedReadyCb.setSelected(states[1]);
        isSuspendedBlockedCb.setSelected(states[2]);
        addBtn.setName(name);
    }

    public boolean getIsBlocked(){
        return isBlockedCb.isSelected();
    }

    public boolean getIsSuspendedReady(){
        return isSuspendedReadyCb.isSelected();
    }

    public boolean getIsSuspendedBlocked(){
        return isSuspendedBlockedCb.isSelected();
    }
}
