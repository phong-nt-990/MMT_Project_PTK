package org.example;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class Kill extends JDialog {
    private JPanel contentPane;
    private JFormattedTextField formattedTextField1;
    private JButton killButton;

    public Kill() {
        InitializeKillComponent();
    }

    private void InitializeKillComponent() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(killButton);
        this.setResizable(false);
        this.pack();
        this.setTitle("Kill Form");

        killButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                killButtonClick();
            }
        });

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


    public void killButtonClick() {
        try {
            Program.nw.write("KILLID");
            Program.nw.newLine();
            Program.nw.flush();
            Program.nw.write(formattedTextField1.getText());
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
