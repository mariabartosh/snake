package com.mariabartosh;

public class DonutBonus extends Donut
{
    public DonutBonus(World world, float x, float y, int imageIndex, int id)
    {
        super(world, x, y, imageIndex, id);
    }
  /*  DonutBonus(World world, float x, float y)
    {
        super(world, x, y, (int) (Math.random() * 11));
    }

    @Override
    public void relocation()
    {
        world.gameObjects.remove(this);
        world.donuts.remove(this);
    }*/
}
