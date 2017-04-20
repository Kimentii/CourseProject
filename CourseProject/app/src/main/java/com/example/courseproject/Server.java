/*package com.example.courseproject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private int PORT;
    ServerSocket serverSocket;
    DataInputStream in;
    DataOutputStream out;

    public Server(int p)
    {
        PORT = p;
    }

    public void write(String x, String y)
    {
        try {
            out.writeUTF(x);
            out.writeUTF(y);
            //notify();
        }
        catch(Exception e)
        {
            System.out.println("Can't sent massage");
            e.printStackTrace();
        }
    }

    public void run()
    {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println(serverSocket.toString());
            System.out.println( serverSocket.getInetAddress().toString());
            System.out.println("Waiting for a client...");
            Socket socket = serverSocket.accept(); // заставляем сервер ждать подключений и выводим сообщение когда кто-то связался с сервером
            System.out.println("Got a client!");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            String getX;
            String getY;
            while(true)
            {
                //wait();
                getX = in.readUTF();
                getY = in.readUTF();
                System.out.println("Server:" + getX);
                System.out.println("Server:" + getY);
            }

        }
        catch(Exception e) { System.out.println("ERROR, can't make server port.");
            e.printStackTrace();
        }
    }
}
*/