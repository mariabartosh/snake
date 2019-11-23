package com.mariabartosh.net.packets.client;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.net.ServerConnection;

public class StartPacket extends ClientPacket
{
    private String name;

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public void write(Json json)
    {
        super.write(json);
        json.writeValue("name", name);
    }

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        super.read(json, jsonData);
        name = jsonData.getString("name");
    }

    @Override
    public void process(ClientPacketProcessor processor, ServerConnection connection)
    {
        processor.on(this, connection);
    }
}
