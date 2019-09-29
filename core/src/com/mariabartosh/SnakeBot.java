package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class SnakeBot extends Snake
{
    private long lastDirectionChange;
    private boolean clockWise;

    SnakeBot(int segmentCount, float radius)
    {
        super(segmentCount, radius);
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
