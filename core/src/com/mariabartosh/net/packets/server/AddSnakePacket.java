package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.MyGame;
import com.mariabartosh.net.ServerSegment;
import com.mariabartosh.net.ServerSnake;

public class AddSnakePacket extends ServerPacket
{
    private SnakeData snakeData;
    private ServerSnake snake;

    public AddSnakePacket(ServerSnake snake)
    {
        this.snake = snake;
    }

    @Override
    public void write(Json json)
    {
        super.write(json);
        json.writeValue("name", snake.getName());
        json.writeValue("radius", snake.getRadius());
        json.writeValue("image", snake.getSegmentTextureIndex());
        json.writeValue("id", snake.getId());
        json.writeArrayStart("segments");
        for (ServerSegment segment : snake.getSegments())
        {
            json.writeObjectStart();
            json.writeValue("x", segment.getX());
            json.writeValue("y", segment.getY());
            json.writeObjectEnd();
        }
        json.writeArrayEnd();
    }

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        snakeData = new SnakeData();
        snakeData.setName(jsonData.getString("name"));
        snakeData.setRadius(jsonData.getFloat("radius"));
        snakeData.setImage(jsonData.getInt("image"));
        snakeData.setId(jsonData.getInt("id"));
        JsonValue segmentsData = jsonData.get("segments");
        int segmentsSize = segmentsData.size;
        snakeData.setSegmentsX(new float[segmentsSize]);
        snakeData.setSegmentsY(new float[segmentsSize]);

        for (int j = 0; j < segmentsSize; j++)
        {
            JsonValue segmentData = segmentsData.get(j);
            snakeData.getSegmentsX()[j] = segmentData.getFloat("x");
            snakeData.getSegmentsY()[j] = segmentData.getFloat("y");
        }
    }

    public SnakeData getSnakeData()
    {
        return snakeData;
    }

    @Override
    public void process(MyGame game)
    {
        game.on(this);
    }

}
