package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.mariabartosh.net.packets.Packet;
import com.mariabartosh.world.Snake;
import com.mariabartosh.world.World;

public class BotsMovementPacket extends Packet
{
    private World world;

    public BotsMovementPacket(World world)
    {
        this.world = world;
    }

    @Override
    public void write(Json json)
    {
        super.write(json);

        json.writeArrayStart("snakes");
        for (Snake snake : world.getSnakeBots())
        {
            json.writeObjectStart();

            json.writeValue("id", snake.getId());
            json.writeValue("x", snake.getHeadX());
            json.writeValue("y", snake.getHeadY());
            json.writeObjectEnd();
        }
        json.writeArrayEnd();
    }
}
