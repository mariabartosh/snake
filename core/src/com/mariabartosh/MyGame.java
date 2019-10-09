package com.mariabartosh;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MyGame extends Game
{

    SpriteBatch batch;
    BitmapFont font;
    World world;
    Texture texture;
    Skin skin;
    Stage ui;
    String playerName;

    @Override
    public void create()
    {
        playerName = "";
        batch = new SpriteBatch();
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("freezing-ui.json"));
        ui = new Stage(new ScreenViewport(), batch);
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