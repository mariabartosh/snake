package com.mariabartosh.net.packets.server;

import com.esotericsoftware.jsonbeans.Json;
import com.mariabartosh.net.packets.Packet;
import com.mariabartosh.world.DonutBonus;
import com.mariabartosh.world.Snake;

import java.util.ArrayList;

public class SnakeDeathPacket extends Packet
{
    private Snake deadSnake;
    private Snake snakeWinner;
    private ArrayList<DonutBonus> donutBonuses;

    public SnakeDeathPacket(Snake deadSnake, Snake snakeWinner, ArrayList<DonutBonus> donutBonuses)
    {
        this.deadSnake = deadSnake;
        this.snakeWinner = snakeWinner;
        this.donutBonuses = donutBonuses;
    }

    @Override
    public void write(Json json)
    {
        super.write(json);
        json.writeArrayStart("donuts");
        for (DonutBonus donut : donutBonuses)
        {
            json.writeObjectStart();
            json.writeValue("x", donut.getX());
            json.writeValue("y", donut.getY());
            json.writeValue("image", donut.getTextureIndex());
            json.writeValue("id", donut.getId());
            json.writeObjectEnd();
        }
        json.writeArrayEnd();

        json.writeObjectStart("winner");
        json.writeValue("id", snakeWinner.getId());
        json.writeValue("score", snakeWinner.getScore());
        json.writeValue("radius", snakeWinner.getRadius());
        json.writeObjectEnd();

        json.writeObjectStart("dead");
        json.writeValue("id", deadSnake.getId());
        json.writeObjectEnd();
    }
}
