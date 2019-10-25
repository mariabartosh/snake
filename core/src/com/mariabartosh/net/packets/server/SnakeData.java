package com.mariabartosh.net.packets.server;

public class SnakeData
{
    private String name;
    private float radius;
    private int image;
    private int id;
    private float[] segmentsX;
    private float[] segmentsY;
    private float headX;
    private float headY;

    void setSegmentsX(float[] segmentsX)
    {
        this.segmentsX = segmentsX;
    }

    void setSegmentsY(float[] segmentsY)
    {
        this.segmentsY = segmentsY;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    void setImage(int image)
    {
        this.image = image;
    }

    void setRadius(float radius)
    {
        this.radius = radius;
    }

    public int getId()
    {
        return id;
    }

    public int getImage()
    {
        return image;
    }

    public float[] getSegmentsY()
    {
        return segmentsY;
    }

    public float[] getSegmentsX()
    {
        return segmentsX;
    }

    public float getRadius()
    {
        return radius;
    }

    public String getName()
    {
        return name;
    }

    void setName(String name)
    {
        this.name = name;
    }

    public float getHeadX()
    {
        return headX;
    }

    public float getHeadY()
    {
        return headY;
    }

    void setHeadX(float headX)
    {
        this.headX = headX;
    }

    void setHeadY(float headY)
    {
        this.headY = headY;
    }
}


