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
import java.nio.ByteBuffer;
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


public class server extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    public String s;
    public String ss;
    public static BufferedImage bimg;


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
            ss = Program.nr.readLine();
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
            switch (ss)
            {
                case "TAKE":
                {
                    Image newing;
                    byte[] bytes;
                    try {
                        Robot bot = new Robot();
                        bimg = bot.createScreenCapture(new Rectangle(0,0,200,100));
                        ImageIO.write(bimg, "PNG", Program.socketOfServer.getOutputStream());
                        Program.socketOfServer.close();
                        Program.listener.close();
                    } catch (AWTException awte) {
                        System.out.println(awte);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
//                    try {
////                        DataInputStream in = new DataInputStream(Program.socketOfServer.getInputStream());
////                        DataOutputStream out = new DataOutputStream(Program.socketOfServer.getOutputStream());
////                        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
//////                        ImageIO.write(image, "png", new File("screenshot.png"));
//////                        byte[] sizeAr = new byte[4];
//////                        in.read(sizeAr);
//////                        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
//////
//////                        byte[] imageAr = new byte[size];
//////                        in.read(imageAr);
////                          OutputStream sOutToClient = Program.socketOfServer.getOutputStream();
////                          DataOutputStream out = new DataOutputStream(sOutToClient);
////                          InputStream sInFromServer = Program.socketOfServer.getInputStream();
////                          DataInputStream in = new DataInputStream(sInFromServer);
////
////                          ByteArrayOutputStream baos = new ByteArrayOutputStream();
////                          ImageIO.write(image, "png", baos);
////
////                          byte[] size = ByteBuffer.allocate(4).putInt(baos.size()).array();
////                          out.write(size);
////                          out.write(baos.toByteArray());
////                          out.flush();
////
////
//                    } catch (AWTException | IOException awte)
//                    {
//                        System.out.println(awte);
//                        break;
//                    }

                }
                case "QUIT":
                {
                    return;
                }
            }
        }
    }
    public server() {
        // Init s
        s = "";

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

//        outputToPane("Local IP: " + Inet4Address.getLocalHost().getHostAddress());
//        outputToPane("hi");
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button1_Click();
            }
        });
    }

    public void receiveSignal() {
        try
        {
            s = Program.nr.readLine();
        }
        catch (IOException e)
        {
            s = "QUIT";
            System.out.println(e);
        }
    }
    public void button1_Click() {
        try {
            Program.listener = new ServerSocket(5656);
        }
        catch (IOException e0) {
            System.out.println(e0);
            System.exit(1);
        }
        try {
//            outputToPane("Local IP: " + Inet4Address.getLocalHost().getHostAddress());
            System.out.println(Inet4Address.getLocalHost().getHostAddress());
//            outputToPane("Server is waiting to accept user...");
            System.out.println("Server is waiting to accept user...");
            Program.socketOfServer = Program.listener.accept();
            System.out.println("Accept a client");
            Program.nr = new BufferedReader(new InputStreamReader(Program.socketOfServer.getInputStream()));
            Program.nw = new BufferedWriter(new OutputStreamWriter(Program.socketOfServer.getOutputStream()));
            respondFromClient: {
                while (true) {
                    receiveSignal();
                    System.out.println(s);
                    switch (s) {
                        case "SHUTDOWN": {
                            shutdown();
                            break;
                        }
                        case "QUIT": {
                            finish();
                            break respondFromClient;
                        }
                        case "TAKEPIC": {
                            takepic();
                            break;
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

    public void finish() throws IOException {
        Program.listener.close();
        Program.socketOfServer.close();

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

