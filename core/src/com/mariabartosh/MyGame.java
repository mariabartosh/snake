package com.mariabartosh;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGame extends Game
{

    SpriteBatch batch;
    BitmapFont font;
    World world;
    Texture texture;

    @Override
    public void create()
    {
        batch = new SpriteBatch();
        font = new BitmapFont();
        world = new World(4096, 4096);
        texture = new Texture(Gdx.files.internal("background.png"));
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        setScreen(new TitleScreen(this));
    }

    @Override
    public void dispose()
    {
        batch.dispose();
        font.dispose();
    }
}