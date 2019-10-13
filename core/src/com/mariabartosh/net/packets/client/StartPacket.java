package com.mariabartosh.net.packets.client;

import com.badlogic.gdx.utils.Json;
import com.mariabartosh.net.packets.Packet;

public class StartPacket extends Packet
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
        json.writeValue("name", getName());
    }
}
