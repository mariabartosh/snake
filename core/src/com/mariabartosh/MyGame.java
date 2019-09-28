package com.mariabartosh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class MyGame extends ApplicationAdapter
{
    ShapeRenderer shapeRenderer;
    Snake snake;
    ArrayList<Donut> donuts;
    ArrayList<GameObject> gameObjects;
    ArrayList<Snake> snakes;
    SpriteBatch batch;
    BitmapFont font;
    Texture texture;

    @Override
    public void create()
    {
        texture = new Texture(Gdx.files.internal("black.jpg"));
        batch = new SpriteBatch();
        font = new BitmapFont();

        gameObjects = new ArrayList<>();
        snakes = new ArrayList<>();
        shapeRenderer = new ShapeRenderer();
        snake = new Snake(50, 10);
        snakes.add(snake);

        for (int i = 0; i < 5; i++)
        {
            SnakeBot snakeBot =new SnakeBot(50, 10);
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
        gameObjects.add(snake);
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
            snake.absorbing(donuts);
            if (snake.checkCollision(snakes))
            {
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
        batch.draw(texture, 0, 0);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (GameObject gameObject : gameObjects)
        {
            gameObject.draw(shapeRenderer, batch);
        }
        shapeRenderer.end();

        batch.begin();
        font.draw(batch, "score: " + (snake.getCountDonuts() * 50 + snake.getCountKills() * 500), Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/20);
        batch.end();
    }

    @Override
    public void dispose()
    {
        shapeRenderer.dispose();
    }
}
