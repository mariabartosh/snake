package com.mariabartosh;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mariabartosh.net.Connection;
import com.mariabartosh.net.packets.server.DonutsUpdatePacket;
import com.mariabartosh.net.packets.server.InvalidNamePacket;
import com.mariabartosh.net.packets.server.GameStartPacket;

import java.util.ArrayList;

public class MyGame extends Game
{

    SpriteBatch batch;
    World world;
    Skin skin;
    Stage ui;
    String playerName;
    Connection connection;
    private ConnectionScreen connectionScreen;

    @Override
    public void create()
    {
        Assets.create();
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
            float[] donutsX = packet.getDonutsX();
            float[] donutsY = packet.getDonutsY();
            int[] donutsImage = packet.getDonutsImage();
            int[] donutsId = packet.getDonutsId();
            for (int i = 0; i < donutsX.length; i++)
            {
                world.add(new Donut(world, donutsX[i], donutsY[i], donutsImage[i], donutsId[i]));
            }

            Snake snake = new Snake(world, packet.getSnakeRadius(), playerName, packet.getSnakeImage(), packet.getSnakeId());
            float[] segmentsX = packet.getSegmentsX();
            float[] segmentsY = packet.getSegmentsY();
            for (int i = 0; i < segmentsX.length; i++)
            {
                snake.addSegment(segmentsX[i], segmentsY[i]);
            }
            world.add(snake);
            world.setPlayer(snake);
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
}