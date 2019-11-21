package com.mariabartosh.net.packets.client;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.JsonValue;
import com.mariabartosh.Server;
import com.mariabartosh.net.Connection;
import com.mariabartosh.net.packets.Packet;

public class CollisionPacket extends Packet
{
    private int snakeId;

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        super.read(json, jsonData);
        snakeId = jsonData.getInt("snake");
    }

    public int getSnakeId()
    {
        return snakeId;
    }

    @Override
    public void process(Server server, Connection connection)
    {
        server.on(this, connection);
    }
}
