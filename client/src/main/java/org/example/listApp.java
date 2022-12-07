package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;

public class listApp extends JDialog {
    private JPanel contentPane;
    private JButton killButton;
    private JButton startButton;
    private JButton watchButton;
    private JButton clearViewButton;
    private JTable table1;


    public listApp() {
        InitializeProcessComponent();
    }

    private void InitializeProcessComponent() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(watchButton);
        setResizable(false);
        // Init table1
        DefaultTableModel model = new DefaultTableModel(null, new String[]{"Application Name", "ID", "Handle Count"});
        table1.setModel(model);

        this.pack();
        this.setTitle("Application Form");
        killButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                killButtonClick();
            }
        });
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startButtonClick();
            }
        });
        watchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                watchButtonClick();
            }
        });
        clearViewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearViewButtonClick();
            }
        });
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    // -------------------------- FUNCTION ------------------------------

    private void clearViewButtonClick() {
        DefaultTableModel anothermodel = new DefaultTableModel(null, new String[]{"Application Name", "ID", "Handle Count"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //Only the third column
                return false;
            }
        };
        table1.setModel(anothermodel);
    }

    private void watchButtonClick_addtoRowFunc(String[] input) {
        DefaultTableModel yourModel = (DefaultTableModel) table1.getModel();
        yourModel.addRow(input);
    }

    private String[] csv_stringToArr(String input) {
        String tmp = input.substring(1, input.length() - 1);
        String[] output = tmp.split("\",\"");
        return output;
    }

    private void watchButtonClick() {
        DefaultTableModel anothermodel = new DefaultTableModel(null, new String[]{"Application Name", "ID", "Handle Count"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
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
                while (!Objects.equals(line, "END_OF_SERVER_LIST")) {
                    line = Program.nr.readLine();
                    if (!line.equals("END_OF_SERVER_LIST")) {
                        watchButtonClick_addtoRowFunc(csv_stringToArr(line));
                    }
                }
                line = "";
            } catch (IOException e) {
                System.out.println(e);
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
        } catch (IOException e) {
            System.out.println(e);
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
        } catch (IOException e) {
            System.out.println(e);
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
        // DISPOSE
    }

}
