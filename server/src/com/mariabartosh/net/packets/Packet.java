package com.mariabartosh.net.packets;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.JsonSerializable;
import com.esotericsoftware.jsonbeans.JsonValue;
import com.mariabartosh.Server;
import com.mariabartosh.net.Connection;

public abstract class Packet implements JsonSerializable
{
    private Connection owner;
    private long timestamp;

    @Override
    public void write(Json json)
    {
        json.writeType(getClass());
    }

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        timestamp = jsonData.getLong("time");
    }

    public void process(Server server, Connection connection)
    {

    }

    public Connection getOwner()
    {
        return owner;
    }

    public void setOwner(Connection owner)
    {
        this.owner = owner;
    }

    public long getTimestamp()
    {
        return timestamp;
    }
}
