package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Collections;

class World
{
    private static final float MAX_SNAKE_COUNT = 10;
    private float width;
    private float height;
    float cameraX;
    float cameraY;
    private Snake player;
    ArrayList<Donut> donuts;
    ArrayList<GameObject> gameObjects;
    private ArrayList<Snake> snakes;
    boolean isGameOver;
    private String[] names;

    World(float w, float h)
    {
        width = w;
        height = h;
    }

    void create()
    {
        isGameOver = false;
        gameObjects = new ArrayList<>();
        donuts = new ArrayList<>();

        for (int i = 0; i < 300; i++)
        {
            Donut donut = new Donut(this, (float) (Math.random() * width), (float) (Math.random() * height));
            donuts.add(donut);
            gameObjects.add(donut);
        }

        names = new String[]{"Corneliu", "Nicu", "Luca-Andrei", "Iulian", "Arnfinn", "Sebastian", "Johannes", "Ragnvald", "Judith",
                "Siv", "Else", "Margrethe", "Aneta", "Emilie", "Antonie", "Jain"};

        snakes = new ArrayList<>();
        player = new Snake(this, 50, 20, names);
        snakes.add(player);
        gameObjects.add(player);
        updateCameraPosition();

        addSnakeBots();
    }

    void update(float deltaTime)
    {
        updateCameraPosition();
        for (GameObject gameObject : gameObjects)
        {
            gameObject.update(deltaTime);
        }

        ArrayList<Snake> removeSnakes = new ArrayList<>();

        for (Snake snake : snakes)
        {
            if (snake.absorbing(donuts) && snake == player)
            {
                Assets.sounds.eat.play();
            }
            if (snake.checkCollision(snakes))
            {
                Assets.sounds.collision.play();
                removeSnakes.add(snake);
            }
            if (snake.getHeadX()  - snake.getRadius() <= 0 || snake.getHeadX() + snake.getRadius() >= width || snake.getHeadY() - snake.getRadius() <= 0 || snake.getHeadY() + snake.getRadius() >= height)
            {
                removeSnakes.add(snake);
            }
        }

        for (Snake snake : removeSnakes)
        {
            snake.breakdown(donuts, gameObjects);
            snakes.remove(snake);
            gameObjects.remove(snake);
            if (snake == player)
            {
                Assets.sounds.gameOver.play();
                isGameOver = true;
            }
        }
        addSnakeBots();
    }

    void draw(SpriteBatch batch)
    {
        batch.draw(Assets.images.background, 0, 0, (int) cameraX, (int) -cameraY, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        for (GameObject gameObject : gameObjects)
        {
            gameObject.draw(batch);
        }

        float cameraWidth = Gdx.graphics.getWidth();
        float cameraHeight = Gdx.graphics.getHeight();
        batch.draw(Assets.images.border, -cameraWidth - cameraX, -cameraHeight - cameraY, cameraWidth,height + 2 * cameraHeight);
        batch.draw(Assets.images.border, width - cameraX, -cameraHeight - cameraY, cameraWidth,height + 2 * cameraHeight);
        batch.draw(Assets.images.border, - cameraX, -cameraHeight - cameraY, width, cameraHeight);
        batch.draw(Assets.images.border, - cameraX, height - cameraY, width, cameraHeight);

        Collections.sort(snakes);
        for (Snake snake : snakes)
        {
            Assets.fonts.game.draw(batch,
                    snake.getName() + "   " + (int) snake.getScore(),
                    Gdx.graphics.getWidth() - 170,
                    Gdx.graphics.getHeight() - (snakes.indexOf(snake) + 1) * 20);
        }
    }

    Snake getPlayer()
    {
        return player;
    }

    void dispose()
    {
    }

    float getWidth()
    {
        return width;
    }

    float getHeight()
    {
        return height;
    }

    float getCameraX()
    {
        return cameraX;
    }

    float getCameraY()
    {
        return cameraY;
    }

    private void updateCameraPosition()
    {
        cameraX = player.getHeadX() - (float) Gdx.graphics.getWidth() / 2;
        cameraY = player.getHeadY() - (float) Gdx.graphics.getHeight() / 2;
    }

    private void addSnakeBots()
    {
        while (snakes.size() <= MAX_SNAKE_COUNT)
        {
            SnakeBot snakeBot = new SnakeBot(this, 50, 20, names);
            snakes.add(snakeBot);
            gameObjects.add(snakeBot);
        }
    }
}
