package org.example;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class Start extends JDialog {
    private JPanel contentPane;
    private JButton startButton;
    private JFormattedTextField processNameTextField;
    private JLabel processNameLabel;

    public Start() {
        InitializeStartComponent();
    }

    private void InitializeStartComponent() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(startButton);
        this.setResizable(false);
        this.pack();
        this.setTitle("Start Form");

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startButtonClick();
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

    private void startButtonClick() {
        try {
            Program.nw.write("STARTID");
            Program.nw.newLine();
            Program.nw.flush();
            Program.nw.write(processNameTextField.getText());
            Program.nw.newLine();
            Program.nw.flush();
            String s = Program.nr.readLine();
            JOptionPane.showMessageDialog(contentPane, s);
        } catch (IOException e)
        {
            System.out.println(e);
        }
    }

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
