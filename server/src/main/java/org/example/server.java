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
import java.net.*;
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
import java.util.ArrayList;
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
//    private ArrayList<String> process_datanameList = new ArrayList<>();
//    private ArrayList<Integer> process_pidList = new ArrayList<>();


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
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void receiveSignalFun() {
        try {
            ss = Program.is.readLine();
        } catch (IOException e) {
            ss = "QUIT";
            System.out.println(e);
        }
    }

    public void takepic() {
        while (true) {
            receiveSignalFun();
            System.out.println(ss);
            switch (ss) {
                case "TAKE": {
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
                    } catch (AWTException awte) {
                        System.out.println(awte);
                        break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                }
                case "QUIT": {
                    return;
                }
            }
        }
    }

    public boolean checkProcessIsApplication(String input)
    {
        String[] temp1 = input.split("");
        if (Objects.equals(temp1[0], "\"") && Objects.equals(temp1[1], "\"") && Objects.equals(temp1[2], ","))
            return false;
        else return true;
    }
    public void application() {
        while (true) {
            receiveSignalFun();
            System.out.println(ss);
            switch (ss) {
                case "XEM": {
                    try {
                        String line;

                        // Command process
                        String command = "powershell.exe Get-Process | Select MainWindowTitle,ProcessName,Id,HandleCount | ConvertTo-Csv -NoTypeInformation";
                        Process powerShellProcess = Runtime.getRuntime().exec(command);
                        powerShellProcess.getOutputStream().close();
                        BufferedReader input = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
                        line = input.readLine();
                        while ((line = input.readLine()) != null) {
                            if (checkProcessIsApplication(line))
                            {
                                String tempOfLine = line.substring(1, line.length());
                                Program.os.write(tempOfLine.substring(tempOfLine.indexOf("\"") + 2));
                                Program.os.newLine();
                                Program.os.flush();
                            }
//                        process_AddPIDList(line);
//                        process_AddProcessNameList(line);
                        }
                        Program.os.write("END_OF_SERVER_LIST");
                        Program.os.newLine();
                        Program.os.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case "KILL": {
                    boolean test01 = true;
//                    process_datanameList = null;
//                    process_pidList = null;
                    while (test01) {
                        receiveSignalFun();
                        switch (ss) {
                            case "KILLID": {
                                try {
                                    String u = "";
                                    u = Program.is.readLine();
                                    boolean test02 = false;
                                    String errorcode = "";
//                                    String line;
//                                    Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe" + " /FO CSV /NH");
//                                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
//                                    while ((line = input.readLine()) != null) {
//                                        process_AddPIDList(line);
//                                        process_AddProcessNameList(line);
//                                    }
                                    if (u != "" | isNumeric(u)) {
                                        Process commandRun = Runtime.getRuntime().exec("TASKKILL /PID " + u);
                                        BufferedReader r = new BufferedReader(new InputStreamReader(commandRun.getInputStream()));
                                        BufferedReader rr = new BufferedReader(new InputStreamReader(commandRun.getErrorStream()));
                                        String s = r.readLine();
                                        if (s != null) {
                                            Program.os.write("Dừng tiến trình thành công.");
                                            Program.os.newLine();
                                            Program.os.flush();
                                            test02 = true;
                                        } else {
                                            errorcode = rr.readLine();
                                            errorcode = errorcode.substring(7, errorcode.length());
                                        }
                                    } else {
                                        errorcode = "PID không hợp lệ.";
                                    }

                                    if (!test02) {
                                        Program.os.write("Đã có lỗi xảy ra. Mã lỗi: " + errorcode);
                                        Program.os.newLine();
                                        Program.os.flush();
                                    }
                                } catch (IOException e) {
                                    System.out.println(e);
                                }
                                break;
                            }
                            case "QUIT": {
                                test01 = false;
                                break;
                            }
                        }
                    }
                    break;
                }
                case "START": {
                    boolean test01 = true;
                    while (test01) {
                        receiveSignalFun();
                        switch (ss) {
                            case "STARTID": {
                                try {
                                    String u = "";
                                    u = Program.is.readLine();
                                    boolean test02 = false;
                                    String errorcode = "";
                                    if (u != "") {
                                        try {
                                            Process p = new ProcessBuilder(u).start();
                                        } catch (IOException ee)
                                        {
                                            errorcode = String.valueOf(ee);
                                        }
                                        if (errorcode == "") {
                                            Program.os.write("Tiến trình khởi động thành công.");
                                            Program.os.newLine();
                                            Program.os.flush();
                                            test02 = true;
                                        }
                                    }
                                    if (!test02) {
                                        Program.os.write("Khởi động tiến trình thất bại. Mã lỗi: " + errorcode);
                                        Program.os.newLine();
                                        Program.os.flush();
                                    }
                                } catch (IOException e) {
                                    System.out.println(e);
                                }
                                break;
                            }
                            case "QUIT": {
                                test01 = false;
                                break;
                            }
                        }
                    }
                    break;
                }
                case "QUIT": {
                    return;
                }
            }
        }
    }

    public void process() {
        while (true) {
            receiveSignalFun();
            System.out.println(ss);
            switch (ss) {
                case "XEM": {
                    // "BEGIN_OF_SERVER_LIST
                    try {
                        String line;
                        Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe" + " /FO CSV /NH");
                        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        while ((line = input.readLine()) != null) {
//                        process_AddPIDList(line);
//                        process_AddProcessNameList(line);
                            Program.os.write(line);
                            Program.os.newLine();
                            Program.os.flush();
                        }
                        Program.os.write("END_OF_SERVER_LIST");
                        Program.os.newLine();
                        Program.os.flush();
//                        System.out.println("END_OF_SERVER_LIST");
//                        ObjectOutputStream oos = new ObjectOutputStream(Program.socketOfClient.getOutputStream());
//                        oos.writeObject(input);

                    } catch (Exception err) {
                        err.printStackTrace();
                    }
//                    System.out.println(process_datanameList);
//                    System.out.println(process_pidList);
                    break;
                }
                case "KILL": {
                    boolean test01 = true;
//                    process_datanameList = null;
//                    process_pidList = null;
                    while (test01) {
                        receiveSignalFun();
                        switch (ss) {
                            case "KILLID": {
                                try {
                                    String u = "";
                                    u = Program.is.readLine();
                                    boolean test02 = false;
                                    String errorcode = "";
//                                    String line;
//                                    Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe" + " /FO CSV /NH");
//                                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
//                                    while ((line = input.readLine()) != null) {
//                                        process_AddPIDList(line);
//                                        process_AddProcessNameList(line);
//                                    }
                                    if (u != "" | isNumeric(u)) {
                                        Process commandRun = Runtime.getRuntime().exec("TASKKILL /PID " + u);
                                        BufferedReader r = new BufferedReader(new InputStreamReader(commandRun.getInputStream()));
                                        BufferedReader rr = new BufferedReader(new InputStreamReader(commandRun.getErrorStream()));
                                        String s = r.readLine();
                                        if (s != null) {
                                            Program.os.write("Dừng tiến trình thành công.");
                                            Program.os.newLine();
                                            Program.os.flush();
                                            test02 = true;
                                        } else {
                                            errorcode = rr.readLine();
                                            errorcode = errorcode.substring(7, errorcode.length());
                                        }
                                    } else {
                                        errorcode = "PID không hợp lệ.";
                                    }

                                    if (!test02) {
                                        Program.os.write("Đã có lỗi xảy ra. Mã lỗi: " + errorcode);
                                        Program.os.newLine();
                                        Program.os.flush();
                                    }
                                } catch (IOException e) {
                                    System.out.println(e);
                                }
                                break;
                            }
                            case "QUIT": {
                                test01 = false;
                                break;
                            }
                        }
                    }
                    break;
                }
                case "START": {
                    boolean test01 = true;
                    while (test01) {
                        receiveSignalFun();
                        switch (ss) {
                            case "STARTID": {
                                try {
                                    String u = "";
                                    u = Program.is.readLine();
                                    boolean test02 = false;
                                    String errorcode = "";
                                    if (u != "") {
                                        try {
                                            Process p = new ProcessBuilder(u).start();
                                        } catch (IOException ee)
                                        {
                                            errorcode = String.valueOf(ee);
                                        }
                                        if (errorcode == "") {
                                            Program.os.write("Tiến trình khởi động thành công.");
                                            Program.os.newLine();
                                            Program.os.flush();
                                            test02 = true;
                                        }
                                    }
                                    if (!test02) {
                                        Program.os.write("Khởi động tiến trình thất bại. Mã lỗi: " + errorcode);
                                        Program.os.newLine();
                                        Program.os.flush();
                                    }
                                } catch (IOException e) {
                                    System.out.println(e);
                                }
                                break;
                            }
                            case "QUIT": {
                                test01 = false;
                                break;
                            }
                        }
                    }
                    break;
                }
                case "QUIT": {
                    return;
                }
            }
        }
    }

    public boolean isNumeric(String str) {
        return str != null && str.matches("[0-9.]+");
    }

//    public String CSV_name_Processing(String input) {
//        String temp = input.substring(1, input.length() - 1);
//        int o = 0;
//        String[] output = temp.split("\",\"");
//        return output[0];
//    }
//
//    public int CSV_Processing(String input) {
//        String temp = input.substring(1, input.length() - 1);
//        int o = 0;
//        String[] output = temp.split("\",\"");
//        if (isNumeric(output[1])) {
//            o = Integer.parseInt(output[1]);
//        } else {
//            o = -1;
//        }
//        return o;
//    }

//    public void process_AddProcessNameList(String input) {
//        process_datanameList.add(CSV_name_Processing(input));
//    }
//
//    public void process_AddPIDList(String input) {
//        process_pidList.add(CSV_Processing(input));
//    }

    public void receiveSignal() {
        try {
            s = Program.is.readLine();
        } catch (IOException e) {
            s = "QUIT";
            System.out.println(e);
        }
    }

    public void button1_Click() {
        try {
            Program.serversocket = new ServerSocket(5656);
        } catch (IOException e0) {
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
            respondFromClient:
            {
                while (true) {

                    receiveSignal();

                    System.out.println(s);
                    ss = "";
                    switch (s) {
                        case "SHUTDOWN": {
                            shutdown();
                            s = "";
                            break;
                        }
                        case "TAKEPIC": {
                            takepic();
                            s = "";
                            break;
                        }
                        case "APPLICATION": {
                            application();
                            s = "";
                            break;
                        }
                        case "PROCESS": {
                            process();
                            s = "";
                            break;
                        }
                        case "QUIT": {
                            finish();
                            s = "";
                            break respondFromClient;
                        }
                    }
                }
            }
        } catch (IOException e) {
            try {
                finish();
            } catch (IOException e1) {
                System.out.println(e1);
            }
//            textArea1.append("Connection closed\n");
//            System.out.println("Connection closed");
        }
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

