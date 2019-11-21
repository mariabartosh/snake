package com.mariabartosh.net.packets.server;

import com.esotericsoftware.jsonbeans.Json;
import com.mariabartosh.net.packets.Packet;

public class CollisionFailPacket extends Packet
{
    private int id;

    public CollisionFailPacket(int id)
    {
        this.id = id;
    }

    public void write(Json json)
    {
        super.write(json);
        json.writeValue("id", id);
    }
}
