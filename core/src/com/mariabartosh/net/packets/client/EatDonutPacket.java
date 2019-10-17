package com.mariabartosh.net.packets.client;

import com.badlogic.gdx.utils.Json;
import com.mariabartosh.net.packets.Packet;

public class EatDonutPacket extends Packet
{
    float id;

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
}
