package org.server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) throws IOException {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    server frame = new server();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public server() {

            



        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 190, 188);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(50, 5, 5, 5));

        setContentPane(contentPane);

        JButton btnNewButton = new JButton("Mở Server");

        btnNewButton.setMinimumSize(new Dimension(100, 50));
        btnNewButton.setMaximumSize(new Dimension(100, 50));
        btnNewButton.setBackground(new Color(255, 255, 255));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openServer();
            }

            private void shutdown() {
                String command = "shutdown -s -t 00";
                try {
                    Process process = Runtime.getRuntime().exec(command);

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private void openServer() {
                ServerSocket listener = null;
                String line;
                BufferedReader is;
                BufferedWriter os;
                Socket socketOfServer = null;
                try {
                    listener = new ServerSocket(5656);
                } catch (IOException e) {
                    System.out.println(e);
                    System.exit(1);
                }
                try {
                    System.out.println("Server is waiting to accept user...");
                    socketOfServer = listener.accept();
                    System.out.println("Accept a client");
                    is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
                    os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
                    while (true) {
                        line = is.readLine();
                        System.out.println(line);
                        switch (line)
                        {
                            // case "KEYLOG": keylog(); break;
                            case "SHUTDOWN": shutdown(); break;
                            // case "REGISTRY": registry(); break;
                            // case "TAKEPIC": takepic(); break;
                            // case "PROCESS": process(); break;
                            // case "APPLICATION": application(); break;
                            // case "QUIT":goto finish;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Server stopped!");
                }
            }
        });
        contentPane.add(btnNewButton);
    }

}
