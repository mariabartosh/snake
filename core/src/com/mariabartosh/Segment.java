package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class Segment
{
    private float x;
    private float y;
    private Color color;

    Segment (float x, float y)
    {
        this.x = x;
        this.y = y;

        color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random() + 0.5f);
    }

    public Color getColor()
    {
        return color;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
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
