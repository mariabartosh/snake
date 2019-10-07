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

        float maxProximity = 150;

        if (getHeadX() < maxProximity)
        {
            newVector.setAngleRad(0);
        }
        if (getHeadX() > world.getWidth() - maxProximity)
        {
            newVector.setAngleRad((float) Math.PI);
        }
        if (getHeadY() < maxProximity)
        {
            newVector.setAngleRad((float) (Math.PI / 2));
        }
        if (getHeadY() > world.getHeight() - maxProximity)
        {
            newVector.setAngleRad(-(float) (Math.PI / 2));
        }

        moveInDirection(deltaTime, newVector);
    }
}
