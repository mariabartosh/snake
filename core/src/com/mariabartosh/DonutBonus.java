package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DonutBonus extends Donut
{
    DonutBonus(World world, float x, float y)
    {
        super(world, x, y);
    }

    @Override
    public void relocation()
    {
        world.gameObjects.remove(this);
        world.donuts.remove(this);
    }
}
