package com.mariabartosh.net.packets.client;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.JsonValue;
import com.mariabartosh.Server;
import com.mariabartosh.net.Connection;
import com.mariabartosh.net.packets.Packet;

public class StartPacket extends Packet
{
    private String name;

    public String getName()
    {
        return name;
    }

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        super.read(json, jsonData);
        name = jsonData.getString("name");
    }

    @Override
    public void process(Server server, Connection connection)
    {
        server.on(this, connection);
    }
}
