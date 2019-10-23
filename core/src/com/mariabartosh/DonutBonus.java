package com.mariabartosh;

public class DonutBonus extends Donut
{
    public DonutBonus(World world, float x, float y, int imageIndex, int id)
    {
        super(world, x, y, imageIndex, id);
    }

    @Override
    public void relocate(float x, float y)
    {
        world.gameObjects.remove(getId());
        world.donuts.remove(this);
    }
}
