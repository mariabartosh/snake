package com.mariabartosh;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mariabartosh.net.packets.client.StartPacket;

public class TitleScreen extends ScreenAdapter
{
    private MyGame game;
    private Label invalidName;
    private Label nameInputTitle;
    private Label nameInputInfo;
    private TextField nameInput;
    private TextButton playButton;
    private TextButton exitButton;
    private VerticalGroup elements;

    TitleScreen(MyGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(game.ui);

        elements = new VerticalGroup();
        game.ui.addActor(elements);

        nameInputTitle = new Label("Enter your name", game.skin);
        nameInputTitle.setColor(Color.BLACK);
        elements.addActor(nameInputTitle);

        nameInputInfo = new Label("(3 to 15 letters and numbers)", game.skin);
        nameInputInfo.setFontScale(0.65f);
        nameInputInfo.setColor(Color.BLACK);
        elements.addActor(nameInputInfo);

        invalidName = new Label("name is taken", game.skin);
        invalidName.setFontScale(0.65f);
        invalidName.setColor(Color.RED);
        invalidName.setVisible(false);
        elements.addActor(invalidName);

        nameInput = new NameInput(game.playerName, game.skin);
        //nameInput.setWidth(nameInput.getWidth() * 2);
        elements.addActor(nameInput);

        playButton = new TextButton("Play", game.skin);
        elements.addActor(playButton);

        exitButton = new TextButton("Exit", game.skin);
        elements.addActor(exitButton);

        playButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if (nameInput.getText().length() < 3)
                {
                    return;
                }
                game.playerName = nameInput.getText();
                StartPacket startPacket = new StartPacket();
                startPacket.setName(nameInput.getText());
                game.connection.send(startPacket);
                Gdx.input.setInputProcessor(null);
            }
        });

        exitButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
                dispose();
            }
        });

        elements.space(10);
    }

    @Override
    public void resize(int width, int height)
    {
        elements.setPosition((width - elements.getWidth()) / 2, (height + elements.getPrefHeight()) / 2);
        super.resize(width, height);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(Assets.images.background, 0, 0, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();
        game.ui.act(delta);
        game.ui.draw();
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(null);
        game.ui.clear();
    }

    void setInvalidName(boolean visible)
    {
        this.invalidName.setVisible(visible);
    }
}
