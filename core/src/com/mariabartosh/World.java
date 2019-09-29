package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class World
{
    private float width;
    private float height;
    private float cameraX;
    private float cameraY;
    private Snake player;
    ArrayList<Donut> donuts;
    ArrayList<GameObject> gameObjects;
    ArrayList<Snake> snakes;
    Texture texture;
    Sound soundCollision;
    Sound soundEat;

    World(float w, float h)
    {
        width = w;
        height = h;
    }

    public void create()
    {
        soundCollision = Gdx.audio.newSound(Gdx.files.internal("collision.mp3"));
        soundEat = Gdx.audio.newSound(Gdx.files.internal("eat.mp3"));
        texture = new Texture(Gdx.files.internal("background.png"));
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        gameObjects = new ArrayList<>();
        snakes = new ArrayList<>();
        player = new Snake(this,50, 10);
        snakes.add(player);
        gameObjects.add(player);
        updateCameraPosition();

        for (int i = 0; i < 5; i++)
        {
            SnakeBot snakeBot = new SnakeBot(this,50, 10);
            snakes.add(snakeBot);
            gameObjects.add(snakeBot);
        }

        donuts = new ArrayList<>();

        for (int i = 0; i < 300; i++)
        {
            Donut donut = new Donut(this, (float) (Math.random() * width), (float) (Math.random() * height));
            donuts.add(donut);
            gameObjects.add(donut);
        }
    }

    public void update(float deltaTime)
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
        }

        for (Snake snake : removeSnakes)
        {
            snake.breakdown(donuts, gameObjects);
            snakes.remove(snake);
            gameObjects.remove(snake);
        }
    }

    public void draw(SpriteBatch batch)
    {
        batch.draw(texture, 0, 0, (int) cameraX, (int) -cameraY, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        for (GameObject gameObject : gameObjects)
        {
            gameObject.draw(batch);
        }
    }

    public Snake getPlayer()
    {
        return player;
    }

    public void dispose()
    {
        soundEat.dispose();
        soundCollision.dispose();
    }

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
    }

    public float getCameraX()
    {
        return cameraX;
    }

    public float getCameraY()
    {
        return cameraY;
    }

    private void updateCameraPosition()
    {
        cameraX = player.segments.get(0).getX() - Gdx.graphics.getWidth() / 2;
        cameraY = player.segments.get(0).getY() - Gdx.graphics.getHeight() / 2;
    }
}
