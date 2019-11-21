package com.mariabartosh.world;

import com.mariabartosh.Server;
import com.mariabartosh.net.packets.server.*;
import java.util.ArrayList;
import java.util.HashMap;

public class World
{
    private static final float MAX_SNAKE_COUNT = 10;
    private float width;
    private float height;
    ArrayList<Donut> donuts;
    private HashMap<Integer, GameObject> gameObjects;
    private ArrayList<Snake> snakes;
    private ArrayList<SnakeBot> snakeBots;
    private String[] names;
    private Server server;

    public World(float w, float h, Server server)
    {
        width = w;
        height = h;
        this.server = server;
    }

    public void create()
    {
        gameObjects = new HashMap<>();
        donuts = new ArrayList<>();
        snakeBots = new ArrayList<>();
        snakes = new ArrayList<>();

        for (int i = 0; i < 300; i++)
        {
            Donut donut = new Donut(this, (float) (Math.random() * width), (float) (Math.random() * height));
            donuts.add(donut);
            gameObjects.put(donut.getId(), donut);
        }

        names = new String[]{"Corneliu", "Nicu", "Luca-Andrei", "Iulian", "Arnfinn", "Sebastian", "Johannes", "Ragnvald", "Judith",
                "Siv", "Else", "Margrethe", "Aneta", "Emilie", "Antonie", "Jain"};

        addSnakeBots();
    }

    public void update(float deltaTime)
    {
        ArrayList<Snake> removeSnakes = new ArrayList<>();

        for (SnakeBot snakeBot : snakeBots)
        {
            if (snakeBot.isDead())
            {
                continue;
            }
            snakeBot.move(deltaTime);

            for (Snake snake : snakes)
            {
                if (!snake.isDead() && snake != snakeBot)
                {
                    if (snakeBot.checkCollision(snake))
                    {
                        ArrayList<DonutBonus> donutBonuses = snake.breakdown();
                        removeSnakes.add(snake);
                        SnakeDeathPacket deathPacket = new SnakeDeathPacket(snake, snakeBot, donutBonuses);
                        server.broadcast(deathPacket);
                        snake.setDead(true);
                    }
                }
            }
        }

        for (Snake snake : snakes)
        {
            if (snake.getHeadX()  - snake.getRadius() <= 0 || snake.getHeadX() + snake.getRadius() >= width || snake.getHeadY() - snake.getRadius() <= 0 || snake.getHeadY() + snake.getRadius() >= height)
            {
                removeSnakes.add(snake);
                RemoveSnakePacket deathPacket = new RemoveSnakePacket(snake);
                server.broadcast(deathPacket);
            }
        }

        for (Snake snake : removeSnakes)
        {
            remove(snake);
        }

        for (SnakeBot snakeBot : snakeBots)
        {
            for (Donut donut : new ArrayList<>(donuts))
            {
                if (snakeBot.eat(donut))
                {
                    donut.relocate();
                    boolean elongation = snakeBot.updateState();
                    DonutsUpdatePacket packet = new DonutsUpdatePacket(snakeBot, donut, elongation, donut instanceof DonutBonus);
                    server.broadcast(packet);
                }
            }
        }

        addSnakeBots();
    }

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
    }

    public ArrayList<Donut> getDonuts()
    {
        return donuts;
    }

    private void addSnakeBots()
    {
        while (snakes.size() <= MAX_SNAKE_COUNT)
        {
            String name = names[(int) (Math.random() * (names.length - 1))];
            while (!server.checkValidName(name))
            {
                name = names[(int) (Math.random() * (names.length - 1))];
            }
            SnakeBot snakeBot = new SnakeBot(this, 50, 20, name);
            snakeBots.add(snakeBot);
            add(snakeBot);
        }
    }

    public void add(Snake snake)
    {
        gameObjects.put(snake.getId(), snake);
        snakes.add(snake);
        AddSnakePacket addSnakePacket = new AddSnakePacket(snake);
        server.broadcast(addSnakePacket);
    }

    public void remove(Snake snake)
    {
        gameObjects.remove(snake.getId());
        snakes.remove(snake);
        snakeBots.remove(snake);
        snake.onRemove();
    }

    HashMap<Integer, GameObject> getGameObjects()
    {
        return gameObjects;
    }

    public ArrayList<Snake> getSnakes()
    {
        return snakes;
    }

    public ArrayList<SnakeBot> getSnakeBots()
    {
        return snakeBots;
    }

    public Snake getSnake(int id)
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

    public Donut getDonut(int id)
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
}
