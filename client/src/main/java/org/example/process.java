package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.*;
import java.util.List;
import java.util.Objects;

public class process extends JDialog {
    private JPanel contentPane;
    private JButton killButton;
    private JButton startButton;
    private JButton watchButton;
    private JButton clearViewButton;
    private JTable table1;
//    private Object[][] task_data_obj = {};
//    private ArrayList<ArrayList<String>> task_data = new ArrayList<>(5);

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
        String[] output = tmp.split("\",\"");
//        String[] tmp2 = null;
//        tmp2 = tmp.split("\",\"");
//        for (int i = 0; i < 3; i++)
//        {
//            output.add(tmp2[i]);
//        }

        return output;
    }
    private void watchButtonClick() {
        DefaultTableModel anothermodel = new DefaultTableModel(null ,new String[] {"Image Name", "PID", "Session Name", "Session#", "Mem Usage"}){
            @Override
            public boolean isCellEditable(int row, int column) {
                //Only the third column
                return false;
            }
        };
        table1.setModel(anothermodel);
//        table1.setEnabled(false);
        String temp = "XEM";
        try {
            Program.nw.write(temp);
            Program.nw.newLine();
            Program.nw.flush();
//            String s1 = "name process";
//            String s2 = "ID";
//            String s3 = "count";
            String line = "";
            try {
                while (!Objects.equals(line, "END_OF_SERVER_LIST"))
                {
                    line = Program.nr.readLine();
                    if (!line.equals("END_OF_SERVER_LIST")) {
                        watchButtonClick_addtoRowFunc(csv_stringToArr(line));
//                        task_data.add(csv_stringToArr(line));
                    }
//                    System.out.println(line);
//                ObjectInputStream ois = new ObjectInputStream(Program.socketOfClient.getInputStream());
//                Message m2 = (Message) ois.readObject();
                }
                line = "";
            } catch (IOException e)
            {
                System.out.println(e);
            }
//            temp = Program.nr.readLine();
//            int soprocess =

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
        } catch (IOException e)
        {
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
