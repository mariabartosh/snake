package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

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
    private Texture texture;
    private Sound soundCollision;
    private Sound soundEat;
    private Sound gameOver;
    boolean isGameOver;

    World(float w, float h)
    {
        width = w;
        height = h;
    }

    void create()
    {
        isGameOver = false;
        soundCollision = Gdx.audio.newSound(Gdx.files.internal("collision.mp3"));
        soundEat = Gdx.audio.newSound(Gdx.files.internal("eat.mp3"));
        gameOver = Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));
        texture = new Texture(Gdx.files.internal("background.png"));
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        gameObjects = new ArrayList<>();

        donuts = new ArrayList<>();

        for (int i = 0; i < 300; i++)
        {
            Donut donut = new Donut(this, (float) (Math.random() * width), (float) (Math.random() * height));
            donuts.add(donut);
            gameObjects.add(donut);
        }

        snakes = new ArrayList<>();
        player = new Snake(this, 50, 20);
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
                soundEat.play();
            }
            if (snake.checkCollision(snakes))
            {
                soundCollision.play();
                removeSnakes.add(snake);
            }
            /*if (snake.getHeadX() <= 0 || snake.getHeadX() >= width || snake.getHeadY() <= 0 || snake.getHeadY() >= height)
            {
                soundCollision.play();
                removeSnakes.add(snake);
            }*/
        }

        for (Snake snake : removeSnakes)
        {
            snake.breakdown(donuts, gameObjects);
            snakes.remove(snake);
            gameObjects.remove(snake);
            if (snake == player)
            {
                gameOver.play();
                isGameOver = true;
            }
        }
        addSnakeBots();
    }

    void draw(SpriteBatch batch)
    {
        batch.draw(texture, 0, 0, (int) cameraX, (int) -cameraY, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        for (GameObject gameObject : gameObjects)
        {
            gameObject.draw(batch);
        }
    }

    Snake getPlayer()
    {
        return player;
    }

    void dispose()
    {
        soundEat.dispose();
        soundCollision.dispose();
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
            SnakeBot snakeBot = new SnakeBot(this, 50, 20);
            snakes.add(snakeBot);
            gameObjects.add(snakeBot);
        }
    }
}
