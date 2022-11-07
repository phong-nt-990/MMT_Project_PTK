package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class client02 extends JDialog {
    private JPanel contentPane;
    private JButton shutdownButton;
    private JButton keystrokeButton;
    private JButton connectButton;
    private JFormattedTextField ipTextField;
    private JButton processRunningButton;
    private JButton appRunningButton;
    private JButton editRegistryButton;
    private JButton exitButton;
    private JButton takeScreenshotButton;

    public client02() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(shutdownButton);

        shutdownButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {

    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        client02 dialog = new client02();
        JFormattedTextField ipTextField1 = dialog.ipTextField;

        dialog.setResizable(false);
        dialog.pack();
        dialog.setResizable(false);
        dialog.setVisible(true);

        System.exit(0);
    }

}
