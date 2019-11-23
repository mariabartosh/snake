package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.MyGame;
import com.mariabartosh.net.packets.Packet;

public class EatFailPacket extends ServerPacket
{
    private int donutID;

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        donutID = jsonData.getInt("id");
    }

    @Override
    public void process(MyGame game)
    {
        game.on(this);
    }

    public int getDonutID()
    {
        return donutID;
    }
}
