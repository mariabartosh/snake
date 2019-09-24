package com.mariabartosh;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public abstract class GameObject
{
    public abstract void draw(ShapeRenderer shapeRenderer);
    public abstract void update(float deltaTime);
}
