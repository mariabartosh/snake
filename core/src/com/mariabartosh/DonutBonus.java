package com.mariabartosh;

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
