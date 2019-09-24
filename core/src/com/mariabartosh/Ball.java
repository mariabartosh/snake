package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ball extends GameObject
{
    private float x;
    private float y;
    private float radius = 5;

    Ball(float x, float y)
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

    public float getRadius()
    {
        return radius;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public void update(float deltaTime)
    {

    }

    public void draw(ShapeRenderer shapeRenderer)
    {
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.circle(x, y, radius);
    }
}
