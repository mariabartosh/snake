package com.mariabartosh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class MyGame extends ApplicationAdapter
{
    SpriteBatch batch;
    BitmapFont font;
    World world;

    @Override
    public void create()
    {
        world = new World(4096, 4096);
        world.create();

        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void render()
    {
        float deltaTime = Gdx.graphics.getDeltaTime();
        world.update(deltaTime);

        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        world.draw(batch);

        font.draw(batch, "score: " + (world.getPlayer().getCountDonuts() * 50 + world.getPlayer().getCountKills() * 500), Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/20);
        batch.end();
    }

    @Override
    public void dispose()
    {
        batch.dispose();
        world.dispose();
    }
}
