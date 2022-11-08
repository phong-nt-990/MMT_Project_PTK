package org.example;


import javax.swing.*;
import java.awt.event.*;
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
import java.awt.Robot;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.channels.ScatteringByteChannel;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.chrono.IsoChronology;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.xml.stream.util.StreamReaderDelegate;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Program
{
    public static ServerSocket serversocket;
    public static Socket socketOfServer;
    public static Socket socketOfClient;
    public static BufferedReader is;
    public static BufferedWriter os;
    public static DataOutputStream dos;
    public static DataInputStream dis;
    public static String s;
    Program() {
        s = "hello";
    }
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println(s);

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        server dialog = new server();
        dialog.setTitle("Open Server 1Form");

        dialog.setResizable(false);
        dialog.pack();
        dialog.setVisible(true);

        System.exit(0);
    }

}