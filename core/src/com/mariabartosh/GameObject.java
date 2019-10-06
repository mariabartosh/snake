package com.mariabartosh;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject
{
    public abstract void draw(SpriteBatch batch);

    public abstract void update(float deltaTime);
}
