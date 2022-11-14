package org.example;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Program {
    public static Socket socketOfClient;
    public static BufferedReader nr;
    public static BufferedWriter nw;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        client dialog = new client();
        dialog.setVisible(true);
    }



}
