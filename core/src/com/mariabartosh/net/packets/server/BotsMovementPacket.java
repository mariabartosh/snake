package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.MyGame;
import com.mariabartosh.net.packets.Packet;

public class BotsMovementPacket extends ServerPacket
{
    private SnakeData[] snakes;

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        JsonValue snakesData = jsonData.get("snakes");
        int size = snakesData.size;
        snakes = new SnakeData[size];
        for (int i = 0; i < size; i++)
        {
            JsonValue snakeData = snakesData.get(i);
            snakes[i] = new SnakeData();
            snakes[i].setId(snakeData.getInt("id"));
            snakes[i].setHeadX(snakeData.getFloat("x"));
            snakes[i].setHeadY(snakeData.getFloat("y"));
        }
    }

    @Override
    public void process(MyGame game)
    {
        game.on(this);
    }

    public SnakeData[] getSnakes()
    {
        return snakes;
    }
}
