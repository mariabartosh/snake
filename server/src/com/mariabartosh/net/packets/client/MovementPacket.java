package com.mariabartosh.net.packets.client;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.JsonValue;
import com.mariabartosh.Server;
import com.mariabartosh.net.Connection;
import com.mariabartosh.net.packets.Packet;

public class MovementPacket extends Packet
{
    private float x;
    private float y;

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
    public void process(Server server, Connection connection)
    {
        server.on(this, connection);
    }
}
