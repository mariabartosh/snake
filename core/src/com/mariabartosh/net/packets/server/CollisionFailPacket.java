package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.MyGame;
import com.mariabartosh.net.packets.Packet;

public class CollisionFailPacket extends Packet
{
    private int snakeID;

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        snakeID = jsonData.getInt("id");
    }

    @Override
    public void process(MyGame game)
    {
        game.on(this);
    }

    public int getSnakeID()
    {
        return snakeID;
    }
}
