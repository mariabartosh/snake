package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.mariabartosh.net.packets.Packet;
import com.mariabartosh.world.Segment;
import com.mariabartosh.world.Snake;

public class AddSnakePacket extends Packet
{
    private Snake snake;

    public AddSnakePacket(Snake snake)
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
        for (Segment segment : snake.getSegments())
        {
            json.writeObjectStart();
            json.writeValue("x", segment.getX());
            json.writeValue("y", segment.getY());
            json.writeObjectEnd();
        }
        json.writeArrayEnd();
    }
}
