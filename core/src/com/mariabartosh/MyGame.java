package com.mariabartosh;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MyGame extends Game
{

    SpriteBatch batch;
    World world;
    Skin skin;
    Stage ui;
    String playerName;
    Connection connection;

    @Override
    public void create()
    {
        Assets.create();
        playerName = "";
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("freezing-ui.json"));
        ui = new Stage(new ScreenViewport(), batch);
        world = new World(4096, 4096);
        setScreen(new ConnectionScreen(this));
        connection = new Connection();
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
    }
}