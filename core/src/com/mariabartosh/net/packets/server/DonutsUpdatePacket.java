package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.MyGame;
import com.mariabartosh.net.packets.Packet;

public class DonutsUpdatePacket extends Packet
{
    private int donutId;
    private float donutX;
    private float donutY;
    private int snakeId;
    private int snakeScore;

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        JsonValue donutData = jsonData.get("donut");
        donutId = donutData.getInt("id");
        donutX = donutData.getFloat("x");
        donutY = donutData.getFloat("y");

        JsonValue snakeData = jsonData.get("snake");
        snakeId = snakeData.getInt("id");
        snakeScore = snakeData.getInt("score");
    }

    @Override
    public void process(MyGame game)
    {
        game.on(this);
    }

    public int getDonutId()
    {
        return donutId;
    }

    public float getDonutY()
    {
        return donutY;
    }

    public float getDonutX()
    {
        return donutX;
    }

    public int getSnakeId()
    {
        return snakeId;
    }

    public int getSnakeScore()
    {
        return snakeScore;
    }
}
