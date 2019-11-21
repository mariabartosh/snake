package com.mariabartosh.net.packets;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.JsonSerializer;
import com.esotericsoftware.jsonbeans.JsonValue;

public class PacketSerializer implements JsonSerializer<Packet>
{
    @Override
    public void write(Json json, Packet object, Class knownType)
    {

    }

    @Override
    public Packet read(Json json, JsonValue jsonData, Class type)
    {
        String typeName = jsonData.getString("class");
        if (typeName.startsWith("com.mariabartosh.net.packets.client"))
        {
            try
            {
                return (Packet) json.readValue(Class.forName(typeName), jsonData);
            }
            catch (Exception ignored)
            {
            }
        }
        return null;
    }
}
