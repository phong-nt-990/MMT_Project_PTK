package org.example;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;

public class client{
    public client(){
        try{
            //creates new socket on port 6667
            Socket sclient = new Socket("10.131.2.195",6667);
            OutputStream sOutToServer = sclient.getOutputStream();
            DataOutputStream out = new DataOutputStream(sOutToServer);
            InputStream sInFromServer = sclient.getInputStream();
            DataInputStream in = new DataInputStream(sInFromServer);

            //Takes screenshot and sends it to server as byte array
            BufferedImage screencap = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screencap,"jpg",baos);

            //Gets screenshot byte size and sends it to server and flushes then closes the connection
            byte[] size = ByteBuffer.allocate(4).putInt(baos.size()).array();
            out.write(size);
            out.write(baos.toByteArray());
            out.flush();
            sclient.close();
        }catch(HeadlessException | AWTException | IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        client t = new client();

    }
}