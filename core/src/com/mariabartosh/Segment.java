package com.mariabartosh;

class Segment
{
    private float x;
    private float y;

    Segment(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    float getX()
    {
        return x;
    }

    float getY()
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

    @Override
    public String toString()
    {
        return "x = " + x + ", y = " + y;
    }
}
