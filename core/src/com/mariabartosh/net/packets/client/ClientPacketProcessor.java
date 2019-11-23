package com.mariabartosh.net.packets.client;

import com.mariabartosh.net.ServerConnection;

public interface ClientPacketProcessor
{
    void on(StartPacket packet, ServerConnection connection);

    void on(EatDonutPacket packet, ServerConnection connection);

    void on(MovementPacket packet, ServerConnection connection);

    void on(CollisionPacket packet, ServerConnection connection);
}
