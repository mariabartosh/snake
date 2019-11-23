package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.MyGame;
import com.mariabartosh.net.packets.Packet;

public class RemoveSnakePacket extends ServerPacket
{
    private int id;

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        id = jsonData.getInt("id");
    }

    public int getId()
    {
        return id;
    }

    @Override
    public void process(MyGame game)
    {
        game.on(this);
    }
}
