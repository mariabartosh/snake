package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.MyGame;
import com.mariabartosh.net.packets.Packet;

public class GameStartPacket extends Packet
{
    private boolean validName;

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        validName = Boolean.parseBoolean(jsonData.getString("ValidName"));
    }

    public boolean isValidName()
    {
        return validName;
    }

    @Override
    public void process(MyGame game)
    {
        game.on(this);
    }
}
