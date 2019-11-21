package com.mariabartosh.net;

import com.esotericsoftware.jsonbeans.Json;
import com.mariabartosh.Server;
import com.mariabartosh.net.packets.Packet;
import com.mariabartosh.net.packets.PacketSerializer;
import com.mariabartosh.world.Snake;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable
{
    public String name;
    private BufferedReader reader;
    private Socket socket;
    private Server server;
    private PrintWriter writer;
    private Json encoder;
    private Json decoder;
    private Snake player;
    private long lastMovementPacket;
    private long deltaTime;

    public Connection(Socket clientSocket, Server server)
    {
        this.server = server;
        socket = clientSocket;
        try
        {
            writer = new PrintWriter(clientSocket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        decoder = new Json();
        decoder.setTypeName(null);
        decoder.setSerializer(Packet.class, new PacketSerializer());

        encoder = new Json();
    }

    @Override
    public void run()
    {
        String message;
        try
        {
            while ((message = reader.readLine()) != null)
            {
                //System.out.println("Received " + message);

                Packet packet = decoder.fromJson(Packet.class, message);
                packet.setOwner(this);
                if (deltaTime == 0)
                {
                    deltaTime = System.currentTimeMillis() - packet.getTimestamp();
                }

                if (System.currentTimeMillis() - packet.getTimestamp() > deltaTime + 3000)
                {
                    close();
                    System.out.println("Connection removed");
                }
                else
                {
                    server.queue.add(packet);
                }
            }
        }
        catch (Exception ignored)
        {
        }

        close();
    }

    public void send(Packet packet)
    {
        //System.out.println("Sending " + encoder.prettyPrint(packet));
        if (isConnected())
        {
            writer.println(encoder.toJson(packet));
            writer.flush();
        }
    }

    public boolean isConnected()
    {
        return socket != null;
    }

    public void setPlayer(Snake player)
    {
        this.player = player;
    }

    public Snake getPlayer()
    {
        return player;
    }

    public boolean isInGame()
    {
        return player != null;
    }

    public long getLastMovementPacket()
    {
        return lastMovementPacket;
    }

    public void setLastMovementPacket(long lastMovementPacket)
    {
        this.lastMovementPacket = lastMovementPacket;
    }

    public void close()
    {
        if (isConnected())
        {
            System.out.println("Client disconnected: " + socket.getInetAddress());
            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            socket = null;
        }
    }
}

