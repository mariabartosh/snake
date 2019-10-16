package com.mariabartosh.net.packets.server;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mariabartosh.MyGame;
import com.mariabartosh.net.packets.Packet;

public class GameStartPacket extends Packet
{
    private float[] donutsX;
    private float[] donutsY;
    private int[] donutsImage;
    private float worldWidth;
    private float worldHeight;
    private float snakeRadius;
    private int snakeImage;
    private float[] segmentsX;
    private float[] segmentsY;

    @Override
    public void read(Json json, JsonValue jsonData)
    {
        worldWidth = jsonData.getFloat("width");
        worldHeight = jsonData.getFloat("height");
        JsonValue donutsData = jsonData.get("donuts");
        int size = donutsData.size;
        donutsX = new float[size];
        donutsY = new float[size];
        donutsImage = new int[size];

        for (int i = 0; i < size; i++)
        {
            JsonValue donutData = donutsData.get(i);
            donutsX[i] = donutData.getFloat("x");
            donutsY[i] = donutData.getFloat("y");
            donutsImage[i] = donutData.getInt("image");
        }

        JsonValue snakeData = jsonData.get("snake");
        snakeRadius = snakeData.getFloat("radius");
        snakeImage = snakeData.getInt("image");
        JsonValue segmentsData = snakeData.get("segments");
        size =segmentsData.size;
        segmentsX = new float[size];
        segmentsY = new float[size];

        for (int i = 0; i < size; i++)
        {
            JsonValue segmentData = segmentsData.get(i);
            segmentsX[i] = segmentData.getFloat("x");
            segmentsY[i] = segmentData.getFloat("y");
        }
    }

    @Override
    public void process(MyGame game)
    {
        game.on(this);
    }

    public float[] getDonutsX()
    {
        return donutsX;
    }

    public float[] getDonutsY()
    {
        return donutsY;
    }

    public int[] getDonutsImage()
    {
        return donutsImage;
    }

    public float getWorldWidth()
    {
        return worldWidth;
    }

    public float getWorldHeight()
    {
        return worldHeight;
    }

    public float getSnakeRadius()
    {
        return snakeRadius;
    }

    public float[] getSegmentsX()
    {
        return segmentsX;
    }

    public float[] getSegmentsY()
    {
        return segmentsY;
    }

    public int getSnakeImage()
    {
        return snakeImage;
    }
}