package com.mariabartosh;

public class SnakeBot extends Snake
{
    Thread thread;
    Runnable runnable;

    SnakeBot(int segmentCount, float radius)
    {
        super(segmentCount, radius);

        runnable = new MyRunnable();
        thread = new Thread(runnable);
        thread.start();
    }

    public class MyRunnable implements Runnable
    {
        public void run()
        {
            while (true)
            {
                direction = (float) (Math.random() * 6.28);
                try
                {
                    Thread.sleep(700);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
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
