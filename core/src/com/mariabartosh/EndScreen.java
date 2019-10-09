package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EndScreen extends ScreenAdapter
{

    private MyGame game;

    EndScreen(MyGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(game.ui);
        Label l = new Label("Game over!", game.skin);
        l.setPosition((float) Gdx.graphics.getWidth() / 2 - l.getWidth() / 2, (float) Gdx.graphics.getHeight() * 5 / 7 - l.getHeight() / 2);
        game.ui.addActor(l);
        TextButton restart = new TextButton("Restart", game.skin);
        restart.setPosition((float) Gdx.graphics.getWidth() / 2 - restart.getWidth() / 2, (float) Gdx.graphics.getHeight() * 3 / 7 - restart.getHeight() / 2);
        game.ui.addActor(restart);

        TextButton exit = new TextButton("Exit", game.skin);
        exit.setPosition((float) Gdx.graphics.getWidth() / 2 - exit.getWidth() / 2, (float) Gdx.graphics.getHeight() * 2 / 7 - exit.getHeight() / 2);
        game.ui.addActor(exit);

        restart.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.world.create();
                game.setScreen(new TitleScreen(game));
                dispose();
            }
        });

        exit.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
                dispose();
            }
        });
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(.25f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(Assets.images.background, 0, 0, (int) game.world.cameraX, (int) -game.world.cameraY, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
}
