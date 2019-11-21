package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.mariabartosh.net.packets.Packet;
import com.mariabartosh.world.Donut;
import com.mariabartosh.world.Snake;

public class DonutsUpdatePacket extends Packet
{
    private Donut donut;
    private Snake snake;
    private boolean elongation;
    private boolean removed;

    public DonutsUpdatePacket(Snake snake, Donut donut, boolean elongation, boolean removed)
    {
        this.donut = donut;
        this.snake = snake;
        this.elongation = elongation;
        this.removed = removed;
    }

    @Override
    public void write(Json json)
    {
        super.write(json);
        json.writeObjectStart("donut");
        json.writeValue("id", donut.getId());
        json.writeValue("x", donut.getX());
        json.writeValue("y", donut.getY());
        json.writeValue("removed", removed);
        json.writeObjectEnd();

        json.writeObjectStart("snake");
        json.writeValue("id", snake.getId());
        json.writeValue("score", snake.getScore());
        json.writeValue("elongation", elongation);
        json.writeObjectEnd();
    }
}
