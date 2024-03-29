package com.mariabartosh.net.packets;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.MyGame;

public abstract class Packet implements Json.Serializable
{
    @Override
    public void write(Json json)
    {
        json.writeType(getClass());
        json.writeValue("time", System.currentTimeMillis());
    }

    @Override
    public void read(Json json, JsonValue jsonData)
    {

    }

    public void process(MyGame game)
    {

    }
}
