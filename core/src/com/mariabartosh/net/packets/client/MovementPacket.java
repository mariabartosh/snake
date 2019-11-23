package com.mariabartosh.net.packets.client;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.net.ServerConnection;

public class MovementPacket extends ClientPacket
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

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        super.read(json, jsonData);
        x = jsonData.getFloat("x");
        y = jsonData.getFloat("y");
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    @Override
    public void process(ClientPacketProcessor processor, ServerConnection connection)
    {
        processor.on(this, connection);
    }
}
