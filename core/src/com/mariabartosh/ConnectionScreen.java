package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ConnectionScreen extends ScreenAdapter
{
    private MyGame game;
    private long lastLabelChange;
    private Label label;

    ConnectionScreen(MyGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        lastLabelChange = System.currentTimeMillis();

        label = new Label("Connection...", game.skin);
        label.setPosition((float) Gdx.graphics.getWidth() / 2 - label.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2 - label.getHeight() / 2);
        game.ui.addActor(label);
    }

    @Override
    public void render(float delta)
    {
        if (System.currentTimeMillis() - lastLabelChange > 1500)
        {
            label.setText("Connection...");
            lastLabelChange = System.currentTimeMillis();
        }
        else if (System.currentTimeMillis() - lastLabelChange > 1000)
        {
            label.setText("Connection..");
        }
        else if (System.currentTimeMillis() - lastLabelChange > 500)
        {
            label.setText("Connection.");
        }

        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(Assets.images.background, 0, 0, (int) game.world.cameraX, (int) -game.world.cameraY, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();
        game.ui.act(delta);
        game.ui.draw();
        if (game.connection.getSocket() != null && game.connection.getSocket().isConnected())
        {
            game.setScreen(new TitleScreen(game));
        }
    }

    @Override
    public void hide()
    {
        game.ui.clear();
    }
}
