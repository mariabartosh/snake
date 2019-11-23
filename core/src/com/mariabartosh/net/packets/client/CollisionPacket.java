package com.mariabartosh.net.packets.client;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.net.ServerConnection;

public class CollisionPacket extends ClientPacket
{
    private int id;

    public CollisionPacket(int id)
    {
        this.id = id;
    }

    @Override
    public void write(Json json)
    {
        super.write(json);
        json.writeValue("snake", id);
    }

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        super.read(json, jsonData);
        id = jsonData.getInt("snake");
    }

    public int getSnakeId()
    {
        return id;
    }

    @Override
    public void process(ClientPacketProcessor processor, ServerConnection connection)
    {
        processor.on(this, connection);
    }
}
