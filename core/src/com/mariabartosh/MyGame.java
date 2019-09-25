package com.mariabartosh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class MyGame extends ApplicationAdapter
{
    ShapeRenderer shapeRenderer;
    Snake snake;
    ArrayList<Ball> balls;
    ArrayList<GameObject> gameObjects;
    ArrayList<Snake> snakes;

    @Override
    public void create()
    {
        gameObjects = new ArrayList<>();
        snakes = new ArrayList<>();
        shapeRenderer = new ShapeRenderer();
        snake = new Snake(50, 10);
        snakes.add(snake);

        for (int i = 0; i < 2; i++)
        {
            SnakeBot snakeBot =new SnakeBot(50, 10);
            snakes.add(snakeBot);
            gameObjects.add(snakeBot);
        }

        balls = new ArrayList<>();

        for (int i = 0; i < 30; i++)
        {
            Ball ball = new Ball((float) (Math.random() * Gdx.graphics.getWidth()), (float) (Math.random() * Gdx.graphics.getHeight()));
            balls.add(ball);
            gameObjects.add(ball);
        }
        gameObjects.add(snake);
    }

    @Override
    public void render()
    {
        ArrayList<Snake> removeSnakes = new ArrayList<>();
        for (Snake snake : snakes)
        {
            snake.update(Gdx.graphics.getDeltaTime());
            snake.absorbing(balls);
            if (snake.checkCollision(snakes))
            {
                removeSnakes.add(snake);
            }
        }

        for (Snake snake : removeSnakes)
        {
            snakes.remove(snake);
            gameObjects.remove(snake);
        }

        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (GameObject gameObject : gameObjects)
        {
            gameObject.draw(shapeRenderer);
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose()
    {
        shapeRenderer.dispose();
    }
}
