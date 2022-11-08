package org.example;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Program {
    public static Socket socketOfClient;
    public static ServerSocket listener;
    public static BufferedReader nr;
    public static BufferedWriter nw;

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        client dialog = new client();
        JFormattedTextField ipTextField1 = dialog.ipTextField;

        dialog.setTitle("Client Form");
        dialog.setResizable(false);
        dialog.pack();
        dialog.setResizable(false);
        dialog.setVisible(true);

        System.exit(0);
    }


}
