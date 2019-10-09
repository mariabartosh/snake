package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

class Assets
{
    static final Sounds sounds = new Sounds();
    static final Images images = new Images();
    static final Fonts fonts = new Fonts();

    public static final class Sounds
    {
        Sound collision;
        Sound eat;
        Sound gameOver;
        private void create()
        {
            collision = Gdx.audio.newSound(Gdx.files.internal("collision.mp3"));
            eat = Gdx.audio.newSound(Gdx.files.internal("eat.mp3"));
            gameOver = Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));
        }
    }

    public static final class Images
    {
        Array<TextureAtlas.AtlasRegion> donuts;
        Array<TextureAtlas.AtlasRegion> segments;
        TextureAtlas.AtlasRegion eyes;
        Texture background;
        Texture border;

        private void create()
        {
            TextureAtlas myTextures = new TextureAtlas("texture.atlas");
            donuts = myTextures.findRegions("donut");
            segments = myTextures.findRegions("z");
            eyes = myTextures.findRegion("eyes");
            background = new Texture(Gdx.files.internal("background.png"));
            background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
            border = new Texture(Gdx.files.internal("black.jpg"));
        }
    }

    public static final class Fonts
    {
        BitmapFont game;

        private void create()
        {
            game = new BitmapFont();
        }
    }

    static void create()
    {
        sounds.create();
        images.create();
        fonts.create();
    }
}
