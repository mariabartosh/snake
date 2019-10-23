package com.mariabartosh;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;

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
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.world.draw(game.batch);

        Assets.fonts.game.draw(game.batch,
                "score: " + (game.world.getPlayer().getScore()),
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

