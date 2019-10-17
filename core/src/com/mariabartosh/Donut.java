package com.mariabartosh;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Donut extends GameObject
{
    private float x;
    private float y;
    private float radius = 30;
    private TextureAtlas.AtlasRegion texture;
    World world;

    public Donut(World world, float x, float y, int imageIndex, int id)
    {
        super(id);
        this.world = world;
        this.x = x;
        this.y = y;
        texture = Assets.images.donuts.get(imageIndex);
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
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

    public void draw(SpriteBatch batch)
    {
        batch.draw(texture, x - radius - world.getCameraX(), y - radius - world.getCameraY(), radius * 2, radius * 2);
    }

    public void relocation(float x, float y)
    {
        setX(x);
        setY(y);
    }
}
