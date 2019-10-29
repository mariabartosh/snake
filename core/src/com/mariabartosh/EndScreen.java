package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EndScreen extends ScreenAdapter
{

    private MyGame game;
    private VerticalGroup elements;

    EndScreen(MyGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(game.ui);
        elements = new VerticalGroup();
        game.ui.addActor(elements);

        Label gameOverLabel = new Label("Game over!", game.skin);
        gameOverLabel.setColor(Color.BLACK);
        elements.addActor(gameOverLabel);

        TextButton restartButton = new TextButton("Restart", game.skin);
        elements.addActor(restartButton);

        TextButton exitButton = new TextButton("Exit", game.skin);
        elements.addActor(exitButton);

        restartButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(game.titleScreen);
                dispose();
            }
        });

        exitButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
                dispose();
            }
        });

        elements.space(20);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(.25f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(Assets.images.background, 0, 0, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();
        game.ui.act(delta);
        game.ui.draw();
    }

    @Override
    public void resize(int width, int height)
    {
        elements.setPosition((width - elements.getWidth()) / 2, (height + elements.getPrefHeight()) / 2);
        super.resize(width, height);
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(null);
        game.ui.clear();
    }
}
