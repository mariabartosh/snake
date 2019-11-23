package com.mariabartosh.net.packets;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.net.packets.client.ClientPacket;

public class PacketSerializer implements Json.Serializer<Packet>
{
    @Override
    public void write(Json json, Packet object, Class knownType)
    {

    }

    @Override
    public ClientPacket read(Json json, JsonValue jsonData, Class type)
    {
        String typeName = jsonData.getString("class");
        if (typeName.startsWith("com.mariabartosh.net.packets.client"))
        {
            try
            {
                return (ClientPacket) json.readValue(Class.forName(typeName), jsonData);
            }
            catch (Exception ignored)
            {
            }
        }
        return null;
    }
}
