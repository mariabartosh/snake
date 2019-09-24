package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Vector;

public class Snake extends GameObject
{
    ArrayList<Segment> segments = new ArrayList<>();
    protected float radius;
    protected float speed = 50;
    protected float direction;
    private float mouseX;
    private float mouseY;
    protected float segmentDistance;
    protected int countBall;

    Snake(int segmentCount, float radius)
    {
        this.radius = radius;
        segmentDistance = radius / 4;

        float HeadX = (float) Math.random() * Gdx.graphics.getWidth();
        float HeadY = (float) Math.random() * Gdx.graphics.getHeight();

        for (int i = 0; i < segmentCount; i++)
        {
            segments.add(new Segment(HeadX, HeadY - segmentDistance * i));
        }

        mouseX = segments.get(0).getX();
        mouseY = segments.get(0).getY();
    }

    public float getRadius()
    {
        return radius;
    }

    public void update(float deltaTime)
    {
        moveInDirection(deltaTime);

        for (int i = 1; i < segments.size(); i++)
        {
            Segment current = segments.get(i);
            Segment next = segments.get(i - 1);

            Vector2 vector = new Vector2(next.getX() - current.getX(), next.getY() - current.getY());
            float angle = vector.angleRad();
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            float l = vector.len();
            l -= segmentDistance;
            current.setX((float)(current.getX() + l * cos));
            current.setY((float)(current.getY() + l * sin));
        }
    }

    public void setCountBall()
    {
        countBall++;
        if (countBall % 3 == 0)
        {
            segments.add(new Segment(segments.get(segments.size() - 1).getX(), segments.get(segments.size() - 1).getY()));
        }
    }

    protected void setDirection(float direction)
    {
        if (Math.abs(this.direction - direction) > 2)
        {
            this.direction = this.direction - 0.5f * Math.signum(direction);
        }
        else
        {
            this.direction = direction;
        }
    }

    public void absorbing(ArrayList<Ball> balls)
    {
        for (Ball ball : balls)
        {
            Vector2 vector = new Vector2(ball.getX() - segments.get(0).getX(), ball.getY() - segments.get(0).getY());
            if (vector.len() <= radius + ball.getRadius())
            {
                setCountBall();
                ball.setX((float) (Math.random() * Gdx.graphics.getWidth()));
                ball.setY((float) (Math.random() * Gdx.graphics.getHeight()));
            }
        }
    }

    protected void moveInDirection(float deltaTime)
    {
        float s = speed * deltaTime;

        float x = Gdx.input.getX();
        float y = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (x != mouseX || y != mouseY)
        {
            Vector2 vector = new Vector2(x - segments.get(0).getX(), y - segments.get(0).getY());
            direction = (vector.angleRad());
            direction = (vector.angleRad());

            /**
             if (Math.abs(direction - vector.angleRad()) < Math.toRadians(2))
             {
             direction = vector.angleRad();
             }
             else
             {
             direction -= Math.toRadians(2) * Math.signum(direction - vector.angleRad());
             }
             */

            mouseX = x;
            mouseY = y;
        }

        double cos = Math.cos(direction);
        double sin = Math.sin(direction);

        segments.get(0).setX((float)(segments.get(0).getX() + s * cos));
        segments.get(0).setY((float)(segments.get(0).getY() + s * sin));
    }

    public void draw(ShapeRenderer shapeRenderer)
    {
        for (Segment segment : segments)
        {
            shapeRenderer.setColor(segment.getColor());
            shapeRenderer.circle(segment.getX(), segment.getY(), radius);
        }
    }
}
