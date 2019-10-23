package com.mariabartosh;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mariabartosh.net.Connection;
import com.mariabartosh.net.packets.Packet;
import com.mariabartosh.net.packets.server.*;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MyGame extends Game
{

    SpriteBatch batch;
    World world;
    Skin skin;
    Stage ui;
    String playerName;
    Connection connection;
    private ConnectionScreen connectionScreen;
    public ConcurrentLinkedQueue<Packet> queue;

    @Override
    public void create()
    {
        Assets.create();
        queue = new ConcurrentLinkedQueue<>();
        playerName = "";
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("freezing-ui.json"));
        ui = new Stage(new ScreenViewport(), batch);
        connectionScreen = new ConnectionScreen(this);
        setScreen(connectionScreen);
        connection = new Connection(this);
        Thread thread = new Thread(connection);
        thread.start();
    }

    @Override
    public void dispose()
    {
        batch.dispose();
        connection.dispose();
    }

    @Override
    public void render()
    {
        super.render();

        if (!connection.isConnected() && !getScreen().equals(connectionScreen))
        {
            setScreen(connectionScreen);
        }

        while (!queue.isEmpty())
        {
            Packet packet = queue.poll();
            if (packet != null)
            {
                packet.process(this);
            }
        }
    }

    public void on(InvalidNamePacket packet)
    {
        //TODO
        Gdx.input.setInputProcessor(ui);
    }

    public void on(GameStartPacket packet)
    {
        Screen currentScreen = getScreen();
        if (currentScreen instanceof TitleScreen)
        {
            world = new World(packet.getWorldWidth(), packet.getWorldHeight(), this);
            world.create();

            for (int i = 0; i < packet.getDonuts().length; i++)
            {
                world.add(new Donut(world,
                        packet.getDonuts()[i].getX(),
                        packet.getDonuts()[i].getY(),
                        packet.getDonuts()[i].getImage(),
                        packet.getDonuts()[i].getId()));
            }

            for (SnakeData snakeData : packet.getSnakes())
            {
                Snake snake = new Snake(world, snakeData.getRadius(), snakeData.getName(), snakeData.getImage(), snakeData.getId());
                int size = snakeData.getSegmentsX().length;
                for (int j = 0; j < size; j++)
                {
                    snake.addSegment(snakeData.getSegmentsX()[j], snakeData.getSegmentsY()[j]);
                }
                world.add(snake);
            }

            world.setPlayer((Snake) world.gameObjects.get(packet.getPlayerId()));
            setScreen(new GameScreen(this));
            currentScreen.dispose();
        }
    }

    public void on(DonutsUpdatePacket packet)
    {
        if (world == null)
        {
            return;
        }
        Snake snake = (Snake) world.gameObjects.get(packet.getSnakeId());
        Donut donut = (Donut) world.gameObjects.get(packet.getDonutId());
        donut.relocate(packet.getDonutX(), packet.getDonutY());
        if (snake == world.getPlayer())
        {
            Assets.sounds.eat.play();
        }
        ArrayList<Segment> segments = snake.getSegments();
        segments.add(new Segment(segments.get(segments.size() - 1).getX(), segments.get(segments.size() - 1).getY()));
        snake.setScore(packet.getSnakeScore());
    }

    public void on(SnakeMovementPacket packet)
    {
        if (world == null)
        {
            return;
        }

        Snake snake = world.getSnake(packet.getId());
        if (snake != null && snake != world.getPlayer())
        {
            snake.update(packet.getX(), packet.getY());
        }
    }

    public void on(AddSnakePacket packet)
    {
        if (world.getPlayer().getId() != packet.getSnake().getId())
        {
            Snake snake = new Snake(world,
                    packet.getSnake().getRadius(),
                    packet.getSnake().getName(),
                    packet.getSnake().getImage(),
                    packet.getSnake().getId());
            int size = packet.getSnake().getSegmentsX().length;
            for (int j = 0; j < size; j++)
            {
                snake.addSegment(packet.getSnake().getSegmentsX()[j], packet.getSnake().getSegmentsY()[j]);
            }
            world.add(snake);
        }
    }

    public void on(SnakeDeathPacket packet)
    {
        for (int i = 0; i < packet.getDonuts().length; i++)
        {
            world.add(new DonutBonus(world,
                    packet.getDonuts()[i].getX(),
                    packet.getDonuts()[i].getY(),
                    packet.getDonuts()[i].getImage(),
                    packet.getDonuts()[i].getId()));
        }
        Snake winner = (Snake) world.gameObjects.get(packet.getSnakeWinnerId());
        winner.setScore(packet.getWinnerScore());
        winner.setRadius(packet.getWinnerRadius());

        Snake dead = (Snake) world.gameObjects.get(packet.getDeadSnakeId());
        if (dead == world.getPlayer())
        {
            Assets.sounds.gameOver.play();
            setScreen(new EndScreen(this));
        }
        else
        {
            Assets.sounds.collision.play();
            world.remove(dead);
        }
    }

    public void on(CollisionFailPacket packet)
    {
        Snake snake = (Snake) world.gameObjects.get(packet.getSnakeID());
        snake.setIgnored(false);
    }

    public void on(EatFailPacket packet)
    {
        if (world ==  null)
        {
            return;
        }
        Donut donut = world.getDonut(packet.getDonutID());
        if (donut != null)
        {
            donut.setIgnored(false);
        }
    }

    public void on(BotsMovementPacket packet)
    {
        for (int i = 0; i < packet.getSnakes().length; i++)
        {
            Snake snake = (Snake) world.gameObjects.get(packet.getSnakes()[i].getId());
            if (snake != world.getPlayer())
            {
                snake.update(packet.getSnakes()[i].getHeadX(), packet.getSnakes()[i].getHeadY());
            }
        }
    }

    public void on(DeathFromBordersPacket packet)
    {
        Snake snake = (Snake) world.gameObjects.get(packet.getId());
        if (snake == world.getPlayer())
        {
            Assets.sounds.gameOver.play();
            setScreen(new EndScreen(this));
        }
        else
        {
            world.remove(snake);
        }
    }
}