package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

public class pic extends JDialog {
    private JPanel contentPane;
    private JButton butTake;
    private JButton butQuitTake;
    private JLabel imagePane;
    public String s;

    private static Thread t;
    public pic() {
        s = "";
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(butTake);

        butTake.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lam();
            }
        });


        butQuitTake.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void lam() {
        Thread t = new GreetingServer();
        t.start();
    }

//    public void picInitComponent() {
//
//        this.setResizable(false);
//        this.setTitle("Take Screenshot Form");
//        this.pack();
//        this.setVisible(true);
//    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
class GreetingServer extends Thread
{
       public void run()
       {
           try {
               Program.socketOfClient.setSoTimeout(180000);
           } catch (SocketException e) {
               throw new RuntimeException(e);
           }
           while(true)
          {
               try
               {
                  BufferedImage img=ImageIO.read(ImageIO.createImageInputStream(Program.socketOfClient.getInputStream()));
                  JFrame frame = new JFrame();
                  frame.getContentPane().add(new JLabel(new ImageIcon(img)));
                  frame.pack();
                  frame.setVisible(true);
              }
             catch(SocketTimeoutException st)
             {
                   System.out.println("Socket timed out!");
                  break;
             }
             catch(IOException e)
             {
                  e.printStackTrace();
                  break;
             }
             catch(Exception ex)
            {
                  System.out.println(ex);
            }
          }
       }

}