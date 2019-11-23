package com.mariabartosh.world;

import com.mariabartosh.net.ServerSegment;

public class Segment implements ServerSegment
{
    private float x;
    private float y;

    Segment(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public float getX()
    {
        return x;
    }

    @Override
    public float getY()
    {
        return y;
    }

    @Override
    public void setX(float x)
    {
        this.x = x;
    }

    @Override
    public void setY(float y)
    {
        this.y = y;
    }
}
