package com.mariabartosh.net.packets.server;

public class DonutData
{
    private float x;
    private float y;
    private int image;
    private int id;

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public int getId()
    {
        return id;
    }

    public int getImage()
    {
        return image;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    void setImage(int image)
    {
        this.image = image;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }
}
