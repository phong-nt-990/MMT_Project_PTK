package org.example;

import javax.swing.*;
import javax.swing.table.TableColumn;
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
    private JButton buttonOK;
    private JButton buttonCancel;

    public process() {
        InitializeProcessComponent();
    }

    private void InitializeProcessComponent() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(watchButton);

        // Init table1
//        String[] tableColumn = {"Name Process", "ID Process", "Count Thread"};
//        TableColumn tableColumn;


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
    public void watchButtonClick() {
        String temp = "XEM";
        try {
            Program.nw.write(temp);
            Program.nw.newLine();
            Program.nw.flush();
            String s1 = "name process";
            String s2 = "ID";
            String s3 = "count";
//            temp = Program.nr.readLine();
//            int soprocess =

        } catch (IOException e) {

            throw new RuntimeException(e);
        }


    }

    public void killButtonClick() {

    }

    public void clearViewButtonClick() {

    }

    public void startButtonClick() {

    }

    // ------------------------- END OF FUNCTION --------------------------


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
