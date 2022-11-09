package org.example;

import javax.naming.SizeLimitExceededException;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
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
import java.net.UnknownHostException;
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
import java.util.Objects;
import java.util.logging.Level;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class server extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    public JLabel ipFieldShow;
    public static String s;
    public static String ss;
    public static BufferedImage bimg;
    byte[] bytes;

    public void InitializeComponent() {
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        this.setResizable(false);
        this.pack();
        this.setTitle("Server Form");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button1_Click();
            }
        });
    }

    public server() {
        InitializeComponent();
        // Init s
//        outputToPane("Local IP: " + Inet4Address.getLocalHost().getHostAddress());
//        outputToPane("hi");
    }
    public void shutdown() {
        try {
            Runtime.getRuntime().exec("shutdown -s -t 00");
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }

    public void receiveSignalTakePic() {
        try
        {
            ss = Program.is.readLine();
        }
        catch (IOException e)
        {
            ss = "QUIT";
            System.out.println(e);
        }
    }
    public void takepic() {
        while (true)
        {
            receiveSignalTakePic();
            System.out.println(ss);
            switch (ss)
            {
                case "TAKE":
                {
                    try {
                        Robot bot = new Robot();
                        bimg = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
//                        ImageIO.write((RenderedImage) bimg, "PNG", Program.socketOfClient.getOutputStream());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(bimg, "PNG", baos);
                        baos.close();
                        ObjectOutputStream oos = new ObjectOutputStream(Program.socketOfClient.getOutputStream());
                        oos.writeObject(baos.size() + "");
                        Program.socketOfClient.getOutputStream().write(baos.toByteArray());
                        System.out.println("Screenshot sent");
                    } catch (AWTException awte)
                    {
                        System.out.println(awte);
                        break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                }
                case "QUIT":
                {
                    return;
                }
            }
        }
    }

    public void receiveSignal() {
        try
        {
            s = Program.is.readLine();
        }
        catch (IOException e)
        {
            s = "QUIT";
            System.out.println(e);
        }
    }

    public void button1_Click() {
        try {
            Program.serversocket = new ServerSocket(5656);
        }
        catch (IOException e0) {
            System.out.println(e0);
            System.exit(1);
        }
        try {
//            outputToPane();
            System.out.println(Inet4Address.getLocalHost().getHostAddress());

//            outputToPane("Server is waiting to accept user...");

            System.out.println("Server is waiting to accept user...");
            Program.socketOfClient = Program.serversocket.accept();
            System.out.println("Accept a client");
            Program.is = new BufferedReader(new InputStreamReader(Program.socketOfClient.getInputStream()));
            Program.os = new BufferedWriter(new OutputStreamWriter(Program.socketOfClient.getOutputStream()));
            respondFromClient: {
                while (true) {

                    receiveSignal();

                    System.out.println(s);
                    switch (s) {
                        case "SHUTDOWN": {
                            shutdown();
                            break;
                        }
                        case "TAKEPIC": {
                            takepic();
                            break;
                        }
                        case "APPLICATION" : {
                            application();
                            break;
                        }
                        case "QUIT": {
                            finish();
                            break respondFromClient;
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            try {
                finish();
            }
            catch (IOException e1) {
                System.out.println(e1);
            }
//            textArea1.append("Connection closed\n");
//            System.out.println("Connection closed");
        }
    }

    public void application() {
//        System.out.println(Program.s);
    }
    public void finish() throws IOException {
        Program.serversocket.close();
        Program.socketOfServer = null;

//        System.out.println("Connection closed");
    }
}
//    private void openServer() {
//        String line;
//        Socket socketOfServer = null;
//        try {
//
//            listener = new ServerSocket(5656);
//        } catch (IOException e) {
//            System.out.println(e);
//            System.exit(1);
//        }
//
//        try {
//            System.out.println("Server is waiting to accept user...");
//            socketOfServer = listener.accept();
//            System.out.println("Accept a client");
//            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
//            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
//            while (true) {
//
//                line = is.readLine();
//                System.out.println(line);
//                switch (line)
//                {
//                    case "SHUTDOWN": sk.shutdown();
////                    case "KEYLOG": sk.keylog();
//                    case "QUIT": sk.serverClose(listener);
//                    case "TAKEPIC": sk.takepic(is, os);
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Server stopped");
//        }
//    }
//
//}

//    public void receiveSignal(String s)
//    {
//        try {
//            SocketFunction.is = new BufferedReader(new InputStreamReader(SocketFunction.sk.getInputStream()));
//            s = SocketFunction.is.readLine();
//        } catch (IOException e) {
//            s = "QUIT";
//            throw new RuntimeException(e);
//        }
//    }

