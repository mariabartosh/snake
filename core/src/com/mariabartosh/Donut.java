package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Donut extends GameObject
{
    private float x;
    private float y;
    private float radius = 30;
    Texture texture;
    World world;

    Donut(World world, float x, float y)
    {
        this.world = world;
        this.x = x;
        this.y = y;
        String image = "donut" + ((int)(Math.random() * 11) + ".png");
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

    public void draw(SpriteBatch batch)
    {
        batch.draw(texture, x - radius - world.getCameraX(), y - radius - world.getCameraY(), radius * 2, radius * 2);
    }

    public void relocation()
    {
        setX((float) (Math.random() * world.getWidth()));
        setY((float) (Math.random() * world.getHeight()));
    }
}
