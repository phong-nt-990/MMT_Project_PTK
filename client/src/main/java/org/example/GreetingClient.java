package org.example;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;

public class GreetingClient
{
    Image newimg;
    static BufferedImage bimg;
    byte[] bytes;

    public static void main(String [] args)
    {
        String serverName = "localhost";
        int port = 6066;
        try
        {
            Socket client = new Socket(serverName, port);
            Robot bot;
            bot = new Robot();
            bimg = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(bimg,"JPG",client.getOutputStream());
            client.close();
        } catch(IOException | AWTException e) {
            e.printStackTrace();
        }
    }
}
