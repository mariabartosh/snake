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
    ShapeRenderer shapeRenderer;
    Snake player;
    ArrayList<Donut> donuts;
    ArrayList<GameObject> gameObjects;
    ArrayList<Snake> snakes;
    SpriteBatch batch;
    BitmapFont font;
    Texture texture;
    Sound soundCollision;
    Sound soundEat;

    @Override
    public void create()
    {
        soundCollision = Gdx.audio.newSound(Gdx.files.internal("collision.mp3"));
        soundEat = Gdx.audio.newSound(Gdx.files.internal("eat.mp3"));

        texture = new Texture(Gdx.files.internal("background.png"));
        batch = new SpriteBatch();
        font = new BitmapFont();

        gameObjects = new ArrayList<>();
        snakes = new ArrayList<>();
        shapeRenderer = new ShapeRenderer();
        player = new Snake(50, 10);
        snakes.add(player);
        gameObjects.add(player);

        for (int i = 0; i < 5; i++)
        {
            SnakeBot snakeBot = new SnakeBot(50, 10);
            snakes.add(snakeBot);
            gameObjects.add(snakeBot);
        }

        donuts = new ArrayList<>();

        for (int i = 0; i < 30; i++)
        {
            Donut donut = new Donut((float) (Math.random() * Gdx.graphics.getWidth()), (float) (Math.random() * Gdx.graphics.getHeight()));
            donuts.add(donut);
            gameObjects.add(donut);
        }
    }

    @Override
    public void render()
    {
        float deltaTime = Gdx.graphics.getDeltaTime();
        for (GameObject gameObject : gameObjects)
        {
            gameObject.update(deltaTime);
        }

        ArrayList<Snake> removeSnakes = new ArrayList<>();

        for (Snake snake : snakes)
        {
            if (snake.absorbing(donuts) && snake == player)
            {
                soundEat.play();
            }
            if (snake.checkCollision(snakes))
            {
                soundCollision.play();
                removeSnakes.add(snake);
            }
        }

        for (Snake snake : removeSnakes)
        {
            snake.breakdown(donuts, gameObjects);
            snakes.remove(snake);
            gameObjects.remove(snake);
        }

        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        for (GameObject gameObject : gameObjects)
        {
            gameObject.draw(batch);
        }

        font.draw(batch, "score: " + (player.getCountDonuts() * 50 + player.getCountKills() * 500), Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/20);
        batch.end();
    }

    @Override
    public void dispose()
    {
        shapeRenderer.dispose();
        soundEat.dispose();
        soundCollision.dispose();
    }
}
