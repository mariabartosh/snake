package com.mariabartosh.world;

public class Donut extends GameObject
{
    private float x;
    private float y;
    private float radius = 30;
    private int textureIndex;
    World world;

    Donut(World world, float x, float y)
    {
        this.world = world;
        this.x = x;
        this.y = y;
        textureIndex = (int) (Math.random() * 11);
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public int getTextureIndex()
    {
        return textureIndex;
    }

    float getRadius()
    {
        return radius;
    }

    private void setX(float x)
    {
        this.x = x;
    }

    private void setY(float y)
    {
        this.y = y;
    }

    public void update(float deltaTime)
    {

    }

    public void relocate()
    {
        setX((float) (Math.random() * world.getWidth()));
        setY((float) (Math.random() * world.getHeight()));
    }
}
