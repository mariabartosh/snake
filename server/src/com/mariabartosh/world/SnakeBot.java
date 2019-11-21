package com.mariabartosh.world;

import com.badlogic.gdx.math.Vector2;

public class SnakeBot extends Snake
{
    private long lastDirectionChange;
    private boolean clockWise;

    SnakeBot(World world, int segmentCount, float radius, String name)
    {
        super(world, segmentCount, radius, name);
    }

    void move(float deltaTime)
    {
        if (System.currentTimeMillis() - lastDirectionChange > 500)
        {
            clockWise = Math.random() > 0.5;
            lastDirectionChange = System.currentTimeMillis();
        }

        Vector2 newVector = new Vector2(vector);

        float maxProximity = 150;

        if (getHeadX() < maxProximity)
        {
            newVector.setAngleRad(0);
        }
        else if (getHeadX() > world.getWidth() - maxProximity)
        {
            newVector.setAngleRad((float) Math.PI);
        }
        else if (getHeadY() < maxProximity)
        {
            newVector.setAngleRad((float) (Math.PI / 2));
        }
        else if (getHeadY() > world.getHeight() - maxProximity)
        {
            newVector.setAngleRad(-(float) (Math.PI / 2));
        }
        else
        {
            newVector.rotate((float) (Math.random() * MAX_ANGLE_DELTA * (clockWise ? -1 : 1)));
        }

        moveInDirection(deltaTime, newVector);
        updateSegments();
    }

    @Override
    public void onRemove()
    {

    }
}
