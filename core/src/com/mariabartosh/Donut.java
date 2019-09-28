package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Donut extends GameObject
{
    private float x;
    private float y;
    private float radius = 8;
    Texture texture;

    Donut(float x, float y)
    {
        this.x = x;
        this.y = y;
        String image = "donut" + ((int)(Math.random() * 2 + 1) + ".png");
        texture = new Texture(Gdx.files.internal(image));
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

    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch)
    {
        batch.begin();
        batch.draw(texture, x, y, radius * 2, radius * 2);
        batch.end();
    }
}
