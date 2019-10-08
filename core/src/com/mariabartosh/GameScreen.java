package com.mariabartosh;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

class GameScreen extends ScreenAdapter
{
    private MyGame game;

    GameScreen(MyGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
    }

    @Override
    public void render(float delta)
    {
        float deltaTime = Gdx.graphics.getDeltaTime();
        game.world.update(deltaTime);
        if (game.world.isGameOver)
        {
            game.setScreen(new EndScreen(game));
        }
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.font = new BitmapFont();
        game.batch.begin();
        game.world.draw(game.batch, game.font);

        game.font.draw(game.batch,
                "score: " + (game.world.getPlayer().getCountDonuts() * 50 + game.world.getPlayer().getCountKills() * 500),
                (float) Gdx.graphics.getWidth() / 20,
                (float) Gdx.graphics.getHeight() / 20);
        game.batch.end();
    }

    @Override
    public void dispose()
    {
        game.batch.dispose();
        game.world.dispose();
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(null);
        game.world.dispose();
    }
}

