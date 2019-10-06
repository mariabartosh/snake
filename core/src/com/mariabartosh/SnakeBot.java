package com.mariabartosh;

import com.badlogic.gdx.math.Vector2;

public class SnakeBot extends Snake
{
    private long lastDirectionChange;
    private boolean clockWise;

    SnakeBot(World world, int segmentCount, float radius, String[] names)
    {
        super(world, segmentCount, radius, names);
    }

    @Override
    protected void move(float deltaTime)
    {
        if (System.currentTimeMillis() - lastDirectionChange > 500)
        {
            clockWise = Math.random() > 0.5;
            lastDirectionChange = System.currentTimeMillis();
        }

        Vector2 newVector = new Vector2(vector);
        newVector.rotate((float) (Math.random() * MAX_ANGLE_DELTA * (clockWise ? -1 : 1)));
        moveInDirection(deltaTime, newVector);
    }
}
