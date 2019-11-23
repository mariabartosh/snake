package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.MyGame;
import com.mariabartosh.net.packets.Packet;

public class SnakeMovementPacket extends ServerPacket
{
    private int id;
    private float x;
    private float y;

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        id = jsonData.getInt("id");
        x = jsonData.getFloat("x");
        y = jsonData.getFloat("y");
    }

    public int getId()
    {
        return id;
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
    public void process(MyGame game)
    {
        game.on(this);
    }
}
