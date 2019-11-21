package com.mariabartosh.world;

public class DonutBonus extends Donut
{
    DonutBonus(World world, float x, float y)
    {
        super(world, x, y);
    }

    @Override
    public void relocate()
    {
        world.getGameObjects().remove(this.getId());
        world.donuts.remove(this);
    }
}
