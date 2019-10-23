package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.MyGame;
import com.mariabartosh.net.packets.Packet;

public class AddSnakePacket extends Packet
{
    private SnakeData snake;

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        snake = new SnakeData();
        snake.setName(jsonData.getString("name"));
        snake.setRadius(jsonData.getFloat("radius"));
        snake.setImage(jsonData.getInt("image"));
        snake.setId(jsonData.getInt("id"));
        JsonValue segmentsData = jsonData.get("segments");
        int segmentsSize = segmentsData.size;
        snake.setSegmentsX(new float[segmentsSize]);
        snake.setSegmentsY(new float[segmentsSize]);

        for (int j = 0; j < segmentsSize; j++)
        {
            JsonValue segmentData = segmentsData.get(j);
            snake.getSegmentsX()[j] = segmentData.getFloat("x");
            snake.getSegmentsY()[j] = segmentData.getFloat("y");
        }
    }

    public SnakeData getSnake()
    {
        return snake;
    }

    @Override
    public void process(MyGame game)
    {
        game.on(this);
    }

}
