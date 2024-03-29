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
    private boolean elongation;
    private boolean removed;

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        JsonValue donutData = jsonData.get("donut");
        donutId = donutData.getInt("id");
        donutX = donutData.getFloat("x");
        donutY = donutData.getFloat("y");
        removed = donutData.getBoolean("removed");

        JsonValue snakeData = jsonData.get("snake");
        snakeId = snakeData.getInt("id");
        snakeScore = snakeData.getInt("score");
        elongation = snakeData.getBoolean("elongation");
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

    public boolean isElongation()
    {
        return elongation;
    }

    public boolean isRemoved()
    {
        return removed;
    }
}
