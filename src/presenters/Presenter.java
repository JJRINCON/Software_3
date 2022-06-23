package presenters;

import views.AddProcessDialog;
import views.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Presenter implements ActionListener {

    private AddProcessDialog addProcessDialog;

    private MainFrame mainFrame;

    public Presenter(){
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
    }

    private void manageCancelAction() {
        addProcessDialog.dispose();
    }

    private void manageExitAction() {
        System.exit(0);
    }
}
