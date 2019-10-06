package com.mariabartosh;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject
{
    public abstract void draw(SpriteBatch batch, BitmapFont font);

    public abstract void update(float deltaTime);
}
