package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Connection implements Runnable
{
    private BufferedReader reader;
    private Socket socket;
    private PrintWriter writer;
    private boolean running = true;

    Connection()
    {
    }

    @Override
    public void run()
    {
        while (running)
        {
            connect();
            if (socket != null && socket.isConnected())
            {
                String message;
                try
                {
                    while ((message = reader.readLine()) != null)
                    {
                        System.out.println(message);
                    }
                }
                catch (Exception ignored) {}
            }
            else
            {
                try
                {
                    Thread.sleep(2000);
                }
                catch (InterruptedException ignored) {}
            }
        }
    }

    private void send(String message)
    {
        writer.println(message);
        writer.flush();
    }

    private void connect()
    {
        try
        {
            socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "127.0.0.1", 5000, new SocketHints());
            System.out.println("connected");

            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            send("Hello, Disaffe4to");
        }
        catch (GdxRuntimeException e)
        {
            //TODO вывод сообщения
            System.out.println("Couldn't connect " + e.getMessage());
        }
    }

    public Socket getSocket()
    {
        return socket;
    }

    void dispose()
    {
        running = false;
        socket.dispose();
    }
}


