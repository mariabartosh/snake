package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.mariabartosh.net.packets.Packet;

public class SnakeMovementPacket extends Packet
{
    private int id;
    private float x;
    private float y;

    public SnakeMovementPacket(int id, float x, float y)
    {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public void write(Json json)
    {
        super.write(json);
        json.writeValue("id", id);
        json.writeValue("x", x);
        json.writeValue("y", y);
    }
}
