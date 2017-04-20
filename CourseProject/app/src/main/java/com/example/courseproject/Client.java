package com.example.courseproject;

import android.content.Intent;
import android.widget.Button;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Client extends Thread {
    String IP = "10.0.2.2";
    int PORT;
    Socket clientSocket;
    DataInputStream in;
    DataOutputStream out;
    Semaphore semaphore;
    static int X, Y;
    Game game;
    Button[][] buttons;

    public Client(int p, Semaphore sem, Game g, Button[][] b)
    {
        PORT = p;
        semaphore = sem;
        game = g;
        setDaemon(true);
        buttons = b;
    }

    public void write(String x, String y)
    {
        try {
            out.writeUTF(x);
            out.writeUTF(y);
           // notify();
        }
        catch(Exception e)
        {
            System.out.println("Can't send massage");
            e.printStackTrace();
        }
    }

    public static int getX()
    {
        return X;
    }

    public static int getY()
    {
        return Y;
    }

    public void run()
    {
        try {
            InetAddress ipAddress = InetAddress.getByName(IP); // создаем объект который отображает вышеописанный IP-адрес.
            clientSocket = new Socket(ipAddress, PORT); // создаем сокет используя IP-адрес и порт сервера.
            System.out.println("Conneckted to server");
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            if(in.readUTF() == "O"){
                game.switchPlayers();
            }
            Player player;
            Button button;
            Player winner;
            while(true)
            {
                semaphore.acquire();
                X = Integer.parseInt(in.readUTF());
                Y = Integer.parseInt(in.readUTF());
                System.out.println("Client:" + X);
                System.out.println("Client:" + Y);
                semaphore.release();
                sleep(1);
            }

        }
        catch (Exception x) {
            System.out.println("Can't connect to server");
            x.printStackTrace();
        }
    }
}
