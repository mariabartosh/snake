package com.mariabartosh;

public class SnakeBot extends Snake
{
    private long lastDirectionChange;

    SnakeBot(int segmentCount, float radius)
    {
        super(segmentCount, radius);
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
        if (System.currentTimeMillis() - lastDirectionChange > 1000)
        {
            direction = (float) (Math.random() * Math.PI * 2);
            lastDirectionChange = System.currentTimeMillis();
        }
    }

    @Override
    protected void moveInDirection(float deltaTime)
    {
        float s = speed * deltaTime;

        double cos = Math.cos(direction);
        double sin = Math.sin(direction);

        segments.get(0).setX((float)(segments.get(0).getX() + s * cos));
        segments.get(0).setY((float)(segments.get(0).getY() + s * sin));
    }
}
