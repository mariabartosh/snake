package com.mariabartosh.net.packets.client;

import com.badlogic.gdx.utils.Json;
import com.mariabartosh.net.packets.Packet;

public class MovementPacket extends Packet
{
    private float x;
    private float y;

    public MovementPacket(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void write(Json json)
    {
        super.write(json);
        json.writeValue("x", x);
        json.writeValue("y", y);
    }
}
