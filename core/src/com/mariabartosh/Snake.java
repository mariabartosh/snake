package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Snake extends GameObject
{
    ArrayList<Segment> segments = new ArrayList<>();
    protected float radius;
    protected float speed = 50;
    protected float direction;
    private float mouseX;
    private float mouseY;
    protected float segmentDistance;
    protected int countDonuts;
    protected int countKills;
    Texture segmentTexture;
    Texture eyes;

    Snake(int segmentCount, float radius)
    {
        this.radius = radius;
        segmentDistance = radius / 3;

        float HeadX = (float) Math.random() * Gdx.graphics.getWidth();
        float HeadY = (float) Math.random() * Gdx.graphics.getHeight();

        for (int i = 0; i < segmentCount; i++)
        {
            segments.add(new Segment(HeadX, HeadY - segmentDistance * i));
        }

        mouseX = segments.get(0).getX();
        mouseY = segments.get(0).getY();

        String image = "z" + ((int)(Math.random() * 11) + ".png");
        segmentTexture = new Texture(Gdx.files.internal(image));
        eyes = new Texture(Gdx.files.internal("eyes.png"));
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
        countDonuts++;
        if (countDonuts % 2 == 0)
        {
            segments.add(new Segment(segments.get(segments.size() - 1).getX(), segments.get(segments.size() - 1).getY()));
        }
    }

    public int getCountDonuts()
    {
        return countDonuts;
    }

    protected void setDirection(float direction)
    {
        if (Math.abs(this.direction - direction) > 2)
        {
            this.direction -= 0.5f * Math.signum(direction - this.direction);
        }
        else
        {
            this.direction = direction;
        }
    }

    public boolean absorbing(ArrayList<Donut> donuts)
    {
        for (Donut donut : donuts)
        {
            Vector2 vector = new Vector2(donut.getX() - segments.get(0).getX(), donut.getY() - segments.get(0).getY());
            if (vector.len() <= radius + donut.getRadius())
            {
                setCountBall();
                donut.setX((float) (Math.random() * Gdx.graphics.getWidth()));
                donut.setY((float) (Math.random() * Gdx.graphics.getHeight()));
                return true;
            }
        }
        return false;
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

    public void draw(SpriteBatch batch)
    {
        for (int i = segments.size() - 1; i >= 0; i--)
        {
            batch.draw(segmentTexture, segments.get(i).getX() - radius, segments.get(i).getY() - radius, radius * 2, radius * 2);
        }
        batch.draw(eyes, segments.get(0).getX() - radius / 2 - radius / 4, segments.get(0).getY() + radius / 2 - radius / 4, radius / 2, radius / 2);
        batch.draw(eyes, segments.get(0).getX() + radius / 2 - radius / 4, segments.get(0).getY() + radius / 2 - radius / 4, radius / 2, radius / 2);
    }

    protected boolean checkCollision(ArrayList<Snake> snakes)
    {
        for (Snake snake : snakes)
        {
            if (snake != this)
            {
                for (int i = 1; i < snake.segments.size(); i++)
                {
                    Vector2 vector = new Vector2(this.segments.get(0).getX() - snake.segments.get(i).getX(), this.segments.get(0).getY() - snake.segments.get(i).getY());
                    if (vector.len() <= radius + snake.getRadius())
                    {
                        snake.radius *= 1.3;
                        snake.countKills++;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected void breakdown(ArrayList<Donut> donuts, ArrayList<GameObject> gameObjects)
    {
        for (int i = 0; i < segments.size(); i += 6)
        {
            Donut donut = new Donut(segments.get(i).getX() + (float)(Math.random() * 8), segments.get(i).getY() - (float)(Math.random() * 8));
            donuts.add(donut);
            gameObjects.add(donut);
        }
    }

    public int getCountKills()
    {
        return countKills;
    }
}
