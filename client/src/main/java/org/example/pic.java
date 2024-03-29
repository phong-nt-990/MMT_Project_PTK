package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class pic extends JDialog {
    private JPanel contentPane;
    private JButton butTake;
    private JButton butSaveScr;
    private JLabel imagePane;
    BufferedImage scrBuffer;
    public String s;
    //------------------------------

    private void InitializePicComponent() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(butTake);
        this.pack();
        this.setTitle("Take Screenshot Form");
        butTake.addActionListener(e -> lam());
        butSaveScr.addActionListener(e -> butSaveScrClick());
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

    public pic() {
        InitializePicComponent();
    }

    public void lam() {
        String s = "TAKE";
        try {
            Program.nw.write(s);
            Program.nw.newLine();
            Program.nw.flush();
            ObjectInputStream ois = new ObjectInputStream(Program.socketOfClient.getInputStream());
            int size = Integer.parseInt(ois.readObject().toString());
            ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
            int sizeread = 0, bytesin;
            byte[] buffer = new byte[1024];
            while (sizeread < size)
            {
                bytesin=Program.socketOfClient.getInputStream().read(buffer);
                sizeread+=bytesin;
                baos.write(buffer,0,bytesin);
            }
            baos.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            scrBuffer = ImageIO.read(bais);
            Image scr = scrBuffer.getScaledInstance(imagePane.getWidth(), imagePane.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scr);
            int Width = icon.getIconWidth();
            imagePane.setIcon(new ImageIcon(icon.getImage().getScaledInstance(Width, 500, Image.SCALE_SMOOTH)));

        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void butSaveScrClick () {
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            try {
                ImageIO.write(scrBuffer,"png",fileToSave);
                JOptionPane.showMessageDialog(contentPane, "Save to file successfully.");
            } catch (IOException e)
            {
                System.out.println("Cannot save to file.");
                JOptionPane.showMessageDialog(contentPane, "Cannot save to file. Error: " + e);
            }
        }
    }

    public void onCancel() {
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