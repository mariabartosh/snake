package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.mariabartosh.net.packets.client.CollisionPacket;
import com.mariabartosh.net.packets.client.EatDonutPacket;
import com.mariabartosh.net.packets.client.MovementPacket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class World
{
    private float width;
    private float height;
    private float cameraX;
    private float cameraY;
    private Snake player;
    ArrayList<Donut> donuts;
    HashMap<Integer, GameObject> gameObjects;
    private ArrayList<Snake> snakes;
    private MyGame game;
    private long lastSending;
    private float segmentDistance;

    World(float w, float h, MyGame game)
    {
        width = w;
        height = h;
        this.game = game;
    }

    void create()
    {
        gameObjects = new HashMap<>();
        donuts = new ArrayList<>();
        snakes = new ArrayList<>();
        lastSending = System.currentTimeMillis();
    }

    void update(float deltaTime)
    {
        updateCameraPosition();
        for (Snake snake : snakes)
        {
            snake.update(deltaTime);
        }

        if (System.currentTimeMillis() - lastSending > 100)
        {
            MovementPacket packet = new MovementPacket(player.getHeadX(), player.getHeadY());
            game.connection.send(packet);
            lastSending = System.currentTimeMillis();
        }

        Donut donut = player.eat(donuts);
        if (donut != null)
        {
            MovementPacket packet = new MovementPacket(player.getHeadX(), player.getHeadY());
            game.connection.send(packet);
            lastSending = System.currentTimeMillis();

            EatDonutPacket eatPacket = new EatDonutPacket(donut.getId());
            game.connection.send(eatPacket);
            donut.setIgnored(true);
        }

        Snake snake = player.checkCollision(snakes);
        if (snake != null)
        {
            MovementPacket packet = new MovementPacket(player.getHeadX(), player.getHeadY());
            game.connection.send(packet);
            lastSending = System.currentTimeMillis();

            CollisionPacket collisionPacket = new CollisionPacket(snake.getId());
            game.connection.send(collisionPacket);
            snake.setIgnored(true);
        }
    }

    void draw(SpriteBatch batch)
    {
        batch.draw(Assets.images.background, 0, 0, (int) cameraX, (int) -cameraY, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        final Matrix4 transformMatrix = new Matrix4();
        transformMatrix.translate(-cameraX, -cameraY, 0);
        batch.setTransformMatrix(transformMatrix);

        for (GameObject gameObject : gameObjects.values())
        {
            gameObject.draw(batch);
        }

        float cameraWidth = Gdx.graphics.getWidth();
        float cameraHeight = Gdx.graphics.getHeight();
        batch.draw(Assets.images.border, -cameraWidth, -cameraHeight, cameraWidth,height + 2 * cameraHeight);
        batch.draw(Assets.images.border, width, -cameraHeight, cameraWidth,height + 2 * cameraHeight);
        batch.draw(Assets.images.border, 0, -cameraHeight, width, cameraHeight);
        batch.draw(Assets.images.border, 0, height, width, cameraHeight);

        transformMatrix.idt();
        batch.setTransformMatrix(transformMatrix);

        Collections.sort(snakes);
        for (int i = 0; i < 10; i++)
        {
            Assets.fonts.game.draw(batch,
                    snakes.get(i).getName() + "   " + snakes.get(i).getScore(),
                    Gdx.graphics.getWidth() - 170,
                    Gdx.graphics.getHeight() - (i + 1) * 20);
        }
    }

    Snake getPlayer()
    {
        return player;
    }

    void setPlayer(Snake player)
    {
        this.player = player;
    }

    void dispose()
    {
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

    void add(Donut donut)
    {
        donuts.add(donut);
        gameObjects.put(donut.getId(), donut);
    }

    void add(Snake snake)
    {
        snakes.add(snake);
        gameObjects.put(snake.getId(), snake);
    }

    void remove(Snake snake)
    {
        gameObjects.remove(snake.getId());
        snakes.remove(snake);
    }

    Snake getSnake(int id)
    {
        GameObject object = gameObjects.get(id);
        if (object instanceof Snake)
        {
            return (Snake) object;
        }
        else
        {
            return null;
        }
    }

    Donut getDonut(int id)
    {
        GameObject object = gameObjects.get(id);
        if (object instanceof Donut)
        {
            return (Donut) object;
        }
        else
        {
            return null;
        }
    }

    public float getSegmentDistance()
    {
        return segmentDistance;
    }

    public void setSegmentDistance(float segmentDistance)
    {
        this.segmentDistance = segmentDistance;
    }
}
