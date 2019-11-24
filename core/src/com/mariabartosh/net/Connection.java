package com.mariabartosh.net;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.mariabartosh.MyGame;
import com.mariabartosh.net.packets.Packet;
import com.mariabartosh.net.packets.PacketSerializer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;

public class Connection implements Runnable
{
    private MyGame game;
    private BufferedReader reader;
    private Socket socket;
    private PrintWriter writer;
    private boolean running = true;
    private Json encoder;
    private Json decoder;
    private String ip;

    public Connection(MyGame game)
    {
        ip = System.getProperty("ip", null);
        if (ip == null)
        {
            Properties properties = new Properties();
            try
            {
                properties.load(Gdx.files.internal("config.properties").read());
            }
            catch (IOException ignored)
            {
            }
            ip = properties.getOrDefault("ip", "127.0.0.1").toString();
        }
        this.game  = game;
        decoder = new Json();
        decoder.setTypeName(null);
        decoder.setSerializer(Packet.class, new PacketSerializer());

        encoder = new Json();
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
                        //System.out.println("Received " + message);

                        Packet packet = decoder.fromJson(Packet.class, message);
                        game.queue.add(packet);
                    }
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    socket.dispose();
                    socket = null;
                    writer = null;
                    reader = null;
                }
                game.setConnectionScreen();
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
        if (isConnected())
        {
            //System.out.println("Sending " + encoder.toJson(packet));
            writer.println(encoder.toJson(packet));
            writer.flush();
        }
    }

    private void connect()
    {
        try
        {
            socket = Gdx.net.newClientSocket(Net.Protocol.TCP, ip, 5000, new SocketHints());
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


