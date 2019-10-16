package com.mariabartosh.net.packets;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class PacketSerializer implements Json.Serializer<Packet>
{
    @Override
    public void write(Json json, Packet object, Class knownType)
    {

    }

    @Override
    public Packet read(Json json, JsonValue jsonData, Class type)
    {
        String typeName = jsonData.getString("class");
        if (typeName.startsWith("com.mariabartosh.net.packets.server"))
        {
            try
            {
                return (Packet) json.readValue(Class.forName(typeName), jsonData);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }
}