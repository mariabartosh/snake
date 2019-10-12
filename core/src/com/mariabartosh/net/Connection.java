package com.mariabartosh.net;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.mariabartosh.net.packets.Packet;
import com.mariabartosh.net.packets.client.StartPacket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Connection implements Runnable
{
    private BufferedReader reader;
    private Socket socket;
    private PrintWriter writer;
    private boolean running = true;

    public Connection()
    {
    }

    @Override
    public void run()
    {
        while (running)
        {
            connect();
            if (isConnected())
            {
                String message;
                try
                {
                    while ((message = reader.readLine()) != null)
                    {
                        System.out.println(message);
                    }
                }
                catch (Exception ignored)
                {
                    socket.dispose();
                    socket = null;
                    writer = null;
                    reader = null;
                }
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

    public void send(Packet packet)
    {
        Json json = new Json();
        writer.println(json.toJson(packet));
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
        }
        catch (GdxRuntimeException e)
        {
            System.out.println("Couldn't connect " + e.getMessage());
        }
    }

    public boolean isConnected()
    {
        return socket != null && socket.isConnected();
    }

    public void dispose()
    {
        running = false;
        socket.dispose();
    }
}


