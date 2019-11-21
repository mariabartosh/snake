package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.mariabartosh.net.packets.Packet;

public class EatFailPacket extends Packet
{
    private int id;

    public EatFailPacket(int id)
    {
        this.id = id;
    }

    public void write(Json json)
    {
        super.write(json);
        json.writeValue("id", id);
    }
}
