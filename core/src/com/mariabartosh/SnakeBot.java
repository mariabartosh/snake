package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
