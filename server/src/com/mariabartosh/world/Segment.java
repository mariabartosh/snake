package com.mariabartosh.world;

public class Segment
{
    private float x;
    private float y;

    Segment(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    void setX(float x)
    {
        this.x = x;
    }

    void setY(float y)
    {
        this.y = y;
    }
}
