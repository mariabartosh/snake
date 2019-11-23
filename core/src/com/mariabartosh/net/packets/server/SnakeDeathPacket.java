package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.MyGame;
import com.mariabartosh.net.packets.Packet;

public class SnakeDeathPacket extends ServerPacket
{
    private int deadSnakeId;
    private int snakeWinnerId;
    private int winnerScore;
    private float winnerRadius;
    private DonutData[] donuts;

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        JsonValue donutsData = jsonData.get("donuts");
        int size = donutsData.size;
        donuts = new DonutData[size];

        for (int i = 0; i < size; i++)
        {
            JsonValue donutData = donutsData.get(i);
            donuts[i] = new DonutData();
            donuts[i].setX(donutData.getFloat("x"));
            donuts[i].setY(donutData.getFloat("y"));
            donuts[i].setImage(donutData.getInt("image"));
            donuts[i].setId(donutData.getInt("id"));
        }

        JsonValue winnerData = jsonData.get("winner");
        snakeWinnerId = winnerData.getInt("id");
        winnerScore = winnerData.getInt("score");
        winnerRadius = winnerData.getFloat("radius");

        JsonValue deadData = jsonData.get("dead");
        deadSnakeId = deadData.getInt("id");
    }

    public DonutData[] getDonuts()
    {
        return donuts;
    }

    public int getDeadSnakeId()
    {
        return deadSnakeId;
    }

    public int getSnakeWinnerId()
    {
        return snakeWinnerId;
    }

    public float getWinnerRadius()
    {
        return winnerRadius;
    }

    public int getWinnerScore()
    {
        return winnerScore;
    }

    @Override
    public void process(MyGame game)
    {
        game.on(this);
    }
}
