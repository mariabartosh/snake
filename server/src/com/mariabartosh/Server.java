package com.mariabartosh;

import com.badlogic.gdx.math.Vector2;
import com.mariabartosh.net.Connection;
import com.mariabartosh.net.packets.Packet;
import com.mariabartosh.net.packets.client.CollisionPacket;
import com.mariabartosh.net.packets.client.EatDonutPacket;
import com.mariabartosh.net.packets.client.MovementPacket;
import com.mariabartosh.net.packets.client.StartPacket;
import com.mariabartosh.net.packets.server.*;
import com.mariabartosh.world.Donut;
import com.mariabartosh.world.DonutBonus;
import com.mariabartosh.world.Snake;
import com.mariabartosh.world.World;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Server
{
    private World world;
    public LinkedBlockingQueue<Packet> queue;
    private final Set<Connection> connections = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args)
    {
        new Server().run();
    }

    private void run()
    {
        world = new World(4096, 4096, this);
        world.create();
        queue = new LinkedBlockingQueue<>();
        AcceptThread acceptThread = new AcceptThread(this);
        Thread t = new Thread(acceptThread);
        t.start();
        long lastUpdate = System.currentTimeMillis();
        long lastPackageSent = System.currentTimeMillis();

        while (true)
        {
            Packet packet = null;
            try
            {
                packet = queue.poll(1, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException ignored)
            {

            }
            if (packet != null)
            {
                packet.process(this, packet.getOwner());
            }
            float deltaTime = System.currentTimeMillis() - lastUpdate;
            if (deltaTime > 17)
            {
                world.update(deltaTime / 1000);
                lastUpdate = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - lastPackageSent > 100)
            {
                broadcast(new BotsMovementPacket(world));
                lastPackageSent = System.currentTimeMillis();
            }
        }
    }

    public void broadcast(Packet packet)
    {
        for (Connection connection : new ArrayList<>(connections))
        {
            if (connection.isConnected())
            {
                if (connection.isInGame())
                {
                    connection.send(packet);
                }
            }
            else
            {
                connections.remove(connection);
                if (connection.isInGame())
                {
                    broadcast(new RemoveSnakePacket(connection.getPlayer()));
                    world.remove(connection.getPlayer());
                }
            }
        }
    }

    public boolean checkValidName(String name)
    {
        if (name == null || !name.matches("[a-zA-Z0-9]{3,15}"))
        {
            return false;
        }

        for (Connection connection : connections)
        {
            if (name.equals(connection.name))
            {
                return false;
            }
        }
        return true;
    }

    public void on(StartPacket packet, Connection connection)
    {
        String name = packet.getName();
        if (connection.isInGame())
        {
            return;
        }

        if ((connection.name == null && checkValidName(name)) || connection.name.equals(name))
        {
            connection.name = name;
            Snake snake = new Snake(world, 50, 20, name);
            world.add(snake);
            connection.setPlayer(snake);
            snake.setConnection(connection);
            connection.setLastMovementPacket(packet.getTimestamp());
            connection.send(new GameStartPacket(world, snake));
        }
        else
        {
            InvalidNamePacket invalidNamePacket = new InvalidNamePacket();
            connection.send(invalidNamePacket);
        }
    }

    public void on(EatDonutPacket packet, Connection connection)
    {
        Donut donut = world.getDonut(packet.getDonutId());
        Snake player = connection.getPlayer();
        if (donut != null && player != null && player.eat(donut))
        {
            donut.relocate();
            boolean elongation = player.updateState();
            DonutsUpdatePacket updatePacket = new DonutsUpdatePacket(player, donut, elongation, donut instanceof DonutBonus);
            broadcast(updatePacket);
        }
        else
        {
            connection.send(new EatFailPacket(packet.getDonutId()));
        }
    }

    public void on(MovementPacket packet, Connection connection)
    {
        if (!connection.isInGame())
        {
            return;
        }
        Snake player = connection.getPlayer();
        Vector2 vector = new Vector2(player.getHeadX() - packet.getX(), player.getHeadY() - packet.getY());
        long deltaTime = packet.getTimestamp() - connection.getLastMovementPacket();
        if (vector.len() <= 150 * deltaTime / 1000.0f + 10)
        {
            connection.setLastMovementPacket(packet.getTimestamp());
            player.update(packet.getX(), packet.getY());
            SnakeMovementPacket snakeMovementPacket = new SnakeMovementPacket(player.getId(), packet.getX(), packet.getY());
            broadcast(snakeMovementPacket);
        }
        else
        {
            connection.close();
            System.out.println("Connection removed");
        }
    }

    void addConnection(Connection connection)
    {
        connections.add(connection);
    }

    public void on(CollisionPacket packet, Connection connection)
    {
        Snake snake = world.getSnake(packet.getSnakeId());
        Snake player = connection.getPlayer();
        if (snake != null && player  != null && player.checkCollision(snake))
        {
            ArrayList<DonutBonus> donutBonuses = snake.breakdown();
            SnakeDeathPacket deathPacket = new SnakeDeathPacket(snake, player, donutBonuses);
            broadcast(deathPacket);
            world.remove(snake);
        }
        else
        {
            connection.send(new CollisionFailPacket(packet.getSnakeId()));
        }
    }
}

