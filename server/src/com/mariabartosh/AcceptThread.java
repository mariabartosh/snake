package com.mariabartosh;

import com.mariabartosh.net.Connection;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class AcceptThread implements Runnable
{
    public Server server;

    AcceptThread(Server server)
    {
        this.server = server;
    }

    @Override
    public void run()
    {
        try
        {
            String ip = System.getenv("SNAKE_SERVER_BIND_ADDRESS");
            if (ip == null)
            {
                ip = "127.0.0.1";
            }
            InetAddress addr = InetAddress.getByName(ip);
            ServerSocket serverSocket = new ServerSocket(5000, 50, addr);
            System.out.println("listening on " + ip);

            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                Connection connection = new Connection(clientSocket, server);
                server.addConnection(connection);

                Thread t = new Thread(connection);
                t.start();
                System.out.println("got a connection from " + clientSocket.getInetAddress());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
