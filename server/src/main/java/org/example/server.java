package org.example;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class server implements Runnable{
    private static Thread t;
    @Override
    public void run(){
        //Screenshot Frame
        final JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        t = new Thread(){
            public void run(){
                try{
                    int port = 6667;
                    ServerSocket serverSocket = new ServerSocket(port);
                    Socket server = serverSocket.accept();
                    System.out.println("[*]Waiting for screenshot on port " + serverSocket.getLocalPort() + "...\n");
                    DataInputStream in = new DataInputStream(server.getInputStream());
                    DataOutputStream out = new DataOutputStream(server.getOutputStream());
                    byte[] sizeAr = new byte[4];
                    in.read(sizeAr);
                    int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                    byte[] imageAr = new byte[size];
                    in.read(imageAr);

                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
                    ImageIO.write(image, "jpg", new File("screen.jpg"));
                    server.close();
                    JLabel label = new JLabel();
                    label.setIcon(new ImageIcon(image));
                    frame.getContentPane().add(label);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        };
        t.start();
        frame.setVisible(true);
    }
}