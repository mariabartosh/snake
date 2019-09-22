package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Vector;

public class Snake
{
    ArrayList<Segment> segments = new ArrayList<>();
    private float radius;
    private float speed = 50;
    private float direction;
    private float mouseX;
    private float mouseY;
    private float segmentDistance;

    Snake(int segmentCount, float radius)
    {
        this.radius = radius;
        segmentDistance = radius / 4;

        float windowCenterX = Gdx.graphics.getWidth() / 2.0f;
        float windowCenterY = Gdx.graphics.getHeight() / 2.0f;

        for (int i = 0; i < segmentCount; i++)
        {
            segments.add(new Segment(windowCenterX, windowCenterY - segmentDistance * i));
        }
        mouseX = segments.get(0).getX();
        mouseY = segments.get(0).getY();
    }

    public float getRadius()
    {
        return radius;
    }

    void advance(float deltaTime)
    {
        float s = speed * deltaTime;

        float x = Gdx.input.getX();
        float y = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (x != mouseX || y != mouseY)
        {
            Vector2 vector = new Vector2(x - segments.get(0).getX(), y - segments.get(0).getY());
            direction = vector.angleRad();
            mouseX = x;
            mouseY = y;
        }

        double cos = Math.cos(direction);
        double sin = Math.sin(direction);

        segments.get(0).setX((float)(segments.get(0).getX() + s * cos));
        segments.get(0).setY((float)(segments.get(0).getY() + s * sin));

        for (int i = 1; i < segments.size(); i++)
        {
            Segment current = segments.get(i);
            Segment next = segments.get(i - 1);

            Vector2 vector = new Vector2(next.getX() - current.getX(), next.getY() - current.getY());
            float angle = vector.angleRad();
            cos = Math.cos(angle);
            sin = Math.sin(angle);
            float l = vector.len();
            l -= segmentDistance;
            current.setX((float)(current.getX() + l * cos));
            current.setY((float)(current.getY() + l * sin));
        }
    }

}
