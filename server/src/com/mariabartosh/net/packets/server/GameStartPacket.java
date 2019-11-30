package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.mariabartosh.net.packets.Packet;
import com.mariabartosh.world.Donut;
import com.mariabartosh.world.Segment;
import com.mariabartosh.world.Snake;
import com.mariabartosh.world.World;

public class GameStartPacket extends Packet
{
    private World world;
    private Snake snake;

    public GameStartPacket(World world, Snake snake)
    {
        this.world = world;
        this.snake = snake;
    }

    @Override
    public void write(Json json)
    {
        super.write(json);
        json.writeValue("distance", snake.getSegmentDistance());
        json.writeValue("width", world.getWidth());
        json.writeValue("height", world.getHeight());
        json.writeArrayStart("donuts");
        for (Donut donut : world.getDonuts())
        {
            json.writeObjectStart();
            json.writeValue("x", donut.getX());
            json.writeValue("y", donut.getY());
            json.writeValue("image", donut.getTextureIndex());
            json.writeValue("id", donut.getId());
            json.writeObjectEnd();
        }
        json.writeArrayEnd();

        json.writeArrayStart("snakes");
        for (Snake snake : world.getSnakes())
        {
            json.writeObjectStart();
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
            json.writeObjectEnd();
        }
        json.writeArrayEnd();

        json.writeValue("playerID", snake.getId());
    }
}
