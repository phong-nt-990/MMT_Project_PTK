package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        this.pack();
        this.setTitle("Client Form");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        takeScreenshotButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                butPic_Click();
            }
        });
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                butConnect_Click();
            }
        });
    }
    public client() {
        InitializeComponent();
//        setContentPane(contentPane);
//        setModal(true);
//        getRootPane().setDefaultButton(connectButton);
//        connectButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                butConnect_Click();
//            }
//        });
//        shutdownButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                butShutdown_Click();
//            }
//        });
//        exitButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                butExit_Click();
//            }
//        });
//        takeScreenshotButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                butPic_Click();
//            }
//        });
//        appRunningButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                appRuningButton_Clicked();
//            }
//        });
    }

    public void appRuningButton_Clicked() {
        if (Program.socketOfClient == null) {
            JOptionPane.showMessageDialog(null, "Chưa kết nối đến server");
            return;
        }
        String sCommand = "APPLICATION";
        try {
            Program.nw.write(sCommand);
            Program.nw.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void butPic_Click() {
        if (Program.socketOfClient == null)
        {
            JOptionPane.showMessageDialog(null, "Chưa kết nối đến server");
            return;
        }
        String sCommand = "TAKEPIC";
        try {
            Program.nw.write(sCommand);
            Program.nw.newLine();
            Program.nw.flush();
        } catch (IOException e)
        {
            System.out.println(e);
        }

        pic pic_dialog = new pic();
        pic_dialog.lam();
//        pic_dialog.setResizable(false);
//        pic_dialog.setTitle("Take Screenshot Form");
//        pic_dialog.pack();
        pic_dialog.setVisible(true);
    }
    public void setAppRunningButton() {

    }
    public void butExit_Click() {
        String sCommand = "QUIT";
        if (Program.socketOfClient != null) {
            try {
                Program.nw.write(sCommand);
                Program.nw.newLine();
                Program.nw.flush();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        System.exit(1);
    }


    public void butShutdown_Click() {
        if (Program.socketOfClient == null)
        {
            JOptionPane.showMessageDialog(null, "Chưa kết nối đến server");
            return;
        }
        String sCommand = "SHUTDOWN";
            try {
                Program.nw.write(sCommand);
                Program.nw.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        try {
            Program.socketOfClient.close();
        } catch (IOException e)
        {
            System.out.println(e);
        }
        Program.socketOfClient = null;

    }

    public void butConnect_Click() {
        boolean test = true;
        try {
            SocketAddress ipServer = new InetSocketAddress(ipTextField.getText(), 5656);
            Program.socketOfClient = new Socket();
            Program.socketOfClient.connect(ipServer);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối đến server");
            Program.socketOfClient = null;
            test = false;
            return;
        }
        if (test)
        {
            JOptionPane.showMessageDialog(null, "Kết nối đến server thành công");
            try {
                Program.nr = new BufferedReader(new InputStreamReader(Program.socketOfClient.getInputStream()));
                Program.nw = new BufferedWriter(new OutputStreamWriter(Program.socketOfClient.getOutputStream()));
            } catch (IOException e)
            {
                System.out.println(e);
            }
        }
    }
//    private void onOK() {
//
//    }


}
