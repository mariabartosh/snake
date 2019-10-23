package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.MyGame;
import com.mariabartosh.net.packets.Packet;

public class GameStartPacket extends Packet
{
    private DonutData[] donuts;
    private float worldWidth;
    private float worldHeight;
    private SnakeData[] snakes;
    private int playerId;

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        worldWidth = jsonData.getFloat("width");
        worldHeight = jsonData.getFloat("height");
        JsonValue donutsData = jsonData.get("donuts");
        int size = donutsData.size;
        donuts = new DonutData[size];

        for (int i = 0; i < size; i++)
        {
            JsonValue donutData = donutsData.get(i);
            donuts[i] = new DonutData();
            donuts[i].setX(donutData.getFloat("x"));
            donuts[i].setY(donutData.getFloat("y"));
            donuts[i].setImage(donutData.getInt("image"));
            donuts[i].setId(donutData.getInt("id"));
        }

        JsonValue snakesData = jsonData.get("snakes");
        size = snakesData.size;
        snakes = new SnakeData[size];
        for (int i = 0; i < size; i++)
        {
            JsonValue snakeData = snakesData.get(i);
            snakes[i] = new SnakeData();
            snakes[i].setName(snakeData.getString("name"));
            snakes[i].setRadius(snakeData.getFloat("radius"));
            snakes[i].setImage(snakeData.getInt("image"));
            snakes[i].setId(snakeData.getInt("id"));
            JsonValue segmentsData = snakeData.get("segments");
            int segmentsSize = segmentsData.size;
            snakes[i].setSegmentsX(new float[segmentsSize]);
            snakes[i].setSegmentsY(new float[segmentsSize]);

            for (int j = 0; j < segmentsSize; j++)
            {
                JsonValue segmentData = segmentsData.get(j);
                snakes[i].getSegmentsX()[j] = segmentData.getFloat("x");
                snakes[i].getSegmentsY()[j] = segmentData.getFloat("y");
            }

            playerId = jsonData.getInt("playerID");
        }
    }

    @Override
    public void process(MyGame game)
    {
        game.on(this);
    }

    public float getWorldWidth()
    {
        return worldWidth;
    }

    public float getWorldHeight()
    {
        return worldHeight;
    }

    public DonutData[] getDonuts()
    {
        return donuts;
    }

    public SnakeData[] getSnakes()
    {
        return snakes;
    }

    public int getPlayerId()
    {
        return playerId;
    }
}