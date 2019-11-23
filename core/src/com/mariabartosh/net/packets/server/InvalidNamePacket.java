package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.MyGame;
import com.mariabartosh.net.packets.Packet;

public class InvalidNamePacket extends ServerPacket
{
    @Override
    public void read(Json json, JsonValue jsonData)
    {

    }

    @Override
    public void process(MyGame game)
    {
        game.on(this);
    }
}
