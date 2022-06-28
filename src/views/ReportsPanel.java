package views;

import models.MyProcess;
import models.OperatingSystem;
import presenters.Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReportsPanel extends JPanel {

    private static final String[] COLUMNS = {"Nombre", "Tiempo", "Bloqueo", "Suspendido listo", "Suspendido bloqueado"};

    public ReportsPanel(ArrayList<MyProcess> readyProcess, ArrayList<MyProcess> dispatchedProcess,
                        ArrayList<MyProcess> executingProcess, ArrayList<MyProcess> toLockedProcess,
                        ArrayList<MyProcess> lockedProcess, ArrayList<MyProcess> lockedToReady,
                        ArrayList<MyProcess> lockedToSuspendedLocked, ArrayList<MyProcess> suspendedLocked,
                        ArrayList<MyProcess> suspendedLockedToLocked, ArrayList<MyProcess> suspendedLockedToSuspendedReady,
                        ArrayList<MyProcess> toSuspendedReady, ArrayList<MyProcess> suspendedReady,
                        ArrayList<MyProcess> suspendedReadyToReady, ArrayList<MyProcess> expiredProcess,
                        ArrayList<MyProcess> terminatedProcess, ActionListener listener) {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#FDFEFE"));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        initTitle(listener);
        JTabbedPane reports = new JTabbedPane();
        reports.setBackground(Color.decode("#FDFEFE"));
        TablePanel readyTable = new TablePanel(OperatingSystem.processInfo(readyProcess), COLUMNS);
        reports.add("Listos", readyTable);

        TablePanel dispatchedTable = new TablePanel(OperatingSystem.processInfo(dispatchedProcess), COLUMNS);
        reports.add("Despachados", dispatchedTable);

        TablePanel executingTable = new TablePanel(OperatingSystem.processInfo(executingProcess), COLUMNS);
        reports.add("En ejecucion", executingTable);

        TablePanel toLockedTable = new TablePanel(OperatingSystem.processInfo(toLockedProcess), COLUMNS);
        reports.add("A Bloqueados", toLockedTable);

        TablePanel lockedTable = new TablePanel(OperatingSystem.processInfo(lockedProcess), COLUMNS);
        reports.add("Bloqueados", lockedTable);

        TablePanel lockedToReadyTable = new TablePanel(OperatingSystem.processInfo(lockedToReady), COLUMNS);
        reports.add("De bloqueado a listo", lockedToReadyTable);

        TablePanel lockedToSuspendedLockedTable = new TablePanel(OperatingSystem.processInfo(lockedToSuspendedLocked), COLUMNS);
        reports.add("De bloqueo a suspendido listo", lockedToSuspendedLockedTable);

        TablePanel suspendedLockedTable = new TablePanel(OperatingSystem.processInfo(suspendedLocked), COLUMNS);
        reports.add("Suspendidos bloqueados", suspendedLockedTable);

        TablePanel suspendedLockedToLockedTable = new TablePanel(OperatingSystem.processInfo(suspendedLockedToLocked), COLUMNS);
        reports.add("Suspendido bloqueado a bloqueado", suspendedLockedToLockedTable);

        TablePanel suspendedLockedToSuspendedReadyTable = new TablePanel(OperatingSystem.processInfo(suspendedLockedToSuspendedReady), COLUMNS);
        reports.add("Suspendido bloqueado a suspendido listo", suspendedLockedToSuspendedReadyTable);

        TablePanel toSuspendedReadyTable = new TablePanel(OperatingSystem.processInfo(toSuspendedReady), COLUMNS);
        reports.add("Ejecucion a suspendido listo", toSuspendedReadyTable);

        TablePanel suspendedReadyTable = new TablePanel(OperatingSystem.processInfo(suspendedReady), COLUMNS);
        reports.add("Suspendido listo", suspendedReadyTable);

        TablePanel suspendedReadyToReadyTable = new TablePanel(OperatingSystem.processInfo(suspendedReadyToReady), COLUMNS);
        reports.add("Suspendido listo a listo", suspendedReadyToReadyTable);

        TablePanel expiredTable = new TablePanel(OperatingSystem.processInfo(expiredProcess), COLUMNS);
        reports.add("Tiempo expirado", expiredTable);

        TablePanel terminatedTable = new TablePanel(OperatingSystem.processInfo(terminatedProcess), COLUMNS);
        reports.add("terminados", terminatedTable);
        add(reports, BorderLayout.CENTER);

        JButton newSimulationBtn = createBtn("Nueva simulacion", Color.decode("#2980B9"), listener,
                Events.NEW_SIMULATION.toString());
        newSimulationBtn.setFont(new Font("Arial", Font.BOLD, 16));
        newSimulationBtn.setPreferredSize(new Dimension(100, 40));
        add(newSimulationBtn, BorderLayout.SOUTH);
    }

    private void initTitle(ActionListener listener){
        MyGridPanel titlePanel = new MyGridPanel();
        titlePanel.setBackground(Color.decode("#16A085"));
        JLabel titleLb = new JLabel("REPORTES");
        titleLb.setForeground(Color.WHITE);
        titleLb.setFont(new Font("Arial", Font.BOLD, 20));
        titleLb.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLb, BorderLayout.NORTH);
        titlePanel.addComponent(titleLb, 3, 1, 6, 0.1);
        //JButton exportBtn = createBtn("Exportar a PDF", Color.decode("#2980B9"), listener, Events.EXPORT.toString());
        //titlePanel.addComponent(exportBtn, 10, 1, 2, 0.1);
        add(titlePanel, BorderLayout.NORTH);
    }

    private JButton createBtn(String txt, Color color, ActionListener listener, String command){
        JButton btn = new JButton(txt);
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.addActionListener(listener);
        btn.setActionCommand(command);
        return btn;
    }
}