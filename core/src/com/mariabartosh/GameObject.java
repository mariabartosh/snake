package com.mariabartosh;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject
{
    private int id;
    GameObject(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public abstract void draw(SpriteBatch batch);

    public abstract void update(float deltaTime);
}
