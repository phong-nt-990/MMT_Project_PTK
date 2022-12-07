package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;

public class process extends JDialog {
    private JPanel contentPane;
    private JButton killButton;
    private JButton startButton;
    private JButton watchButton;
    private JButton clearViewButton;
    private JTable table1;

    public process() {
        InitializeProcessComponent();
    }

    private void InitializeProcessComponent() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(watchButton);
        setResizable(false);
        // Init table1
        DefaultTableModel model = new DefaultTableModel(null ,new String[] {"Image Name", "PID", "Session Name", "Session#", "Mem Usage"});
        table1.setModel(model);

        this.pack();
        this.setTitle("Process Form");
        killButton.addActionListener(e -> killButtonClick());
        startButton.addActionListener(e -> startButtonClick());
        watchButton.addActionListener(e -> watchButtonClick());
        clearViewButton.addActionListener(e -> clearViewButtonClick());
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    // -------------------------- FUNCTION ------------------------------

    private void clearViewButtonClick() {
        DefaultTableModel anothermodel = new DefaultTableModel(null ,new String[] {"Image Name", "PID", "Session Name", "Session#", "Mem Usage"}){
            @Override
            public boolean isCellEditable(int row, int column) {
                //Only the third column
                return false;
            }
        };
        table1.setModel(anothermodel);
    }
    private void watchButtonClick_addtoRowFunc(String[] input){
        DefaultTableModel yourModel = (DefaultTableModel) table1.getModel();
        yourModel.addRow(input);
    }
    private String[] csv_stringToArr(String input){
        String tmp = input.substring(1, input.length()-1);
        return tmp.split("\",\"");
    }
    private void watchButtonClick() {
        // Create table header
        DefaultTableModel anothermodel = new DefaultTableModel(null ,new String[] {"Image Name", "PID", "Session Name", "Session#", "Mem Usage"}){
            @Override
            public boolean isCellEditable(int row, int column) {
                //Only the third column
                return false;
            }
        };
        table1.setModel(anothermodel);
        String temp = "XEM";
        try {
            Program.nw.write(temp);
            Program.nw.newLine();
            Program.nw.flush();
            String line = "";
            try {
                while (!Objects.equals(line, "END_OF_SERVER_LIST"))
                {
                    line = Program.nr.readLine();
                    if (!line.equals("END_OF_SERVER_LIST")) {
                        watchButtonClick_addtoRowFunc(csv_stringToArr(line));
                    }
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void killButtonClick() {
        String temp = "KILL";
        try {
            Program.nw.write(temp);
            Program.nw.newLine();
            Program.nw.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Kill kill_dialog = new Kill();
        kill_dialog.setVisible(true);
    }


    private void startButtonClick() {
        String temp = "START";
        try {
            Program.nw.write(temp);
            Program.nw.newLine();
            Program.nw.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Start start_dialog = new Start();
        start_dialog.setVisible(true);
    }

    // ------------------------- END OF FUNCTION --------------------------


    private void onCancel() {
        String s = "QUIT";
        try {
            Program.nw.write(s);
            Program.nw.newLine();
            Program.nw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dispose();
    }
}
