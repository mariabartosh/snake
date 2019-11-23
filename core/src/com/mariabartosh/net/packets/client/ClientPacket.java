package com.mariabartosh.net.packets.client;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.net.ServerConnection;
import com.mariabartosh.net.packets.Packet;

public abstract class ClientPacket extends Packet
{
    private ServerConnection owner;
    private long timestamp;

    @Override
    public void write(Json json)
    {
        super.write(json);
        json.writeValue("time", System.currentTimeMillis());
    }

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        timestamp = jsonData.getLong("time");
    }

    public ServerConnection getOwner()
    {
        return owner;
    }

    public void setOwner(ServerConnection owner)
    {
        this.owner = owner;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public abstract void process(ClientPacketProcessor processor, ServerConnection connection);
}
