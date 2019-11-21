package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.mariabartosh.net.packets.Packet;
import com.mariabartosh.world.Snake;

public class RemoveSnakePacket extends Packet
{
    private Snake snake;

    public RemoveSnakePacket(Snake snake)
    {
        this.snake = snake;
    }

    @Override
    public void write(Json json)
    {
        super.write(json);
        json.writeValue("id", snake.getId());
    }
}
