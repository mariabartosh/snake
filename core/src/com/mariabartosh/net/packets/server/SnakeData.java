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

    public void setSegmentsX(float[] segmentsX)
    {
        this.segmentsX = segmentsX;
    }

    public void setSegmentsY(float[] segmentsY)
    {
        this.segmentsY = segmentsY;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setImage(int image)
    {
        this.image = image;
    }

    public void setRadius(float radius)
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

    public void setName(String name)
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

    public void setHeadX(float headX)
    {
        this.headX = headX;
    }

    public void setHeadY(float headY)
    {
        this.headY = headY;
    }
}


