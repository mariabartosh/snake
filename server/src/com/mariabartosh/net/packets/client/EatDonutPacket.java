package com.mariabartosh.net.packets.client;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.Server;
import com.mariabartosh.net.Connection;
import com.mariabartosh.net.packets.Packet;

public class EatDonutPacket extends Packet
{
    private int donutId;

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        super.read(json, jsonData);
        donutId = jsonData.getInt("donutID");
    }

    public int getDonutId()
    {
        return donutId;
    }

    @Override
    public void process(Server server, Connection connection)
    {
        server.on(this, connection);
    }
}
