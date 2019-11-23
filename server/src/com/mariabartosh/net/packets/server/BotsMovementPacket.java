package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.mariabartosh.net.ServerSnake;
import com.mariabartosh.net.packets.Packet;

import java.util.ArrayList;

public class BotsMovementPacket extends Packet
{
    private ArrayList bots;

    public BotsMovementPacket(ArrayList bots)
    {
        this.bots = bots;
    }

    @Override
    public void write(Json json)
    {
        super.write(json);

        json.writeArrayStart("snakes");
        for (Object object : bots)
        {
            ServerSnake snake = (ServerSnake) object;
            json.writeObjectStart();
            json.writeValue("id", snake.getId());
            json.writeValue("x", snake.getHeadX());
            json.writeValue("y", snake.getHeadY());
            json.writeObjectEnd();
        }
        json.writeArrayEnd();
    }
}
