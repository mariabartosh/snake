package com.mariabartosh;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mariabartosh.net.Connection;
import com.mariabartosh.net.packets.server.InvalidNamePacket;
import com.mariabartosh.net.packets.server.GameStartPacket;

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

    }

    public void on(GameStartPacket packet)
    {
        Screen currentScreen = getScreen();
        if (currentScreen instanceof TitleScreen)
        {
            world = new World(packet.getWorldWidth(), packet.getWorldHeight());
            world.create();
            float[] donutsX = packet.getDonutsX();
            float[] donutsY = packet.getDonutsY();
            int[] donutsImage = packet.getDonutsImage();
            for (int i = 0; i < donutsX.length; i++)
            {
                world.add(new Donut(world, donutsX[i], donutsY[i], donutsImage[i]));
            }

            Snake snake = new Snake(world, packet.getSnakeRadius(), playerName, packet.getSnakeImage());
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
}