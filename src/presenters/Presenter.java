package presenters;

import views.MainFrame;

public class Presenter {

    private MainFrame mainFrame;

    public Presenter(){
        initMainFrame();
    }

    private void initMainFrame(){
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
