package com.mariabartosh.net.packets.client;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.net.ServerConnection;

public class EatDonutPacket extends ClientPacket
{
    private int id;

    public EatDonutPacket(int id)
    {
        this.id = id;
    }

    @Override
    public void write(Json json)
    {
        super.write(json);
        json.writeValue("donutID", id);
    }

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        super.read(json, jsonData);
        id = jsonData.getInt("donutID");
    }

    public int getDonutId()
    {
        return id;
    }

    @Override
    public void process(ClientPacketProcessor processor, ServerConnection connection)
    {
        processor.on(this, connection);
    }
}
