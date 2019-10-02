package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Snake extends GameObject
{
    protected static final float MAX_ANGLE_DELTA = 3;
    ArrayList<Segment> segments = new ArrayList<>();
    protected float radius;
    protected float speed = 150;
    protected float segmentDistance;
    protected int countDonuts;
    protected int countKills;
    Texture segmentTexture;
    Texture eyes;
    protected Vector2 vector;
    World world;

    Snake(World world, int segmentCount, float radius)
    {
        this.world = world;
        this.radius = radius;
        segmentDistance = radius / 3;

        float headX = (float) Math.random() * world.getWidth();
        float headY = (float) Math.random() * world.getHeight();

        for (int i = 0; i < segmentCount; i++)
        {
            segments.add(new Segment(headX, headY - segmentDistance * i));
        }

        String image = "z" + ((int)(Math.random() * 11) + ".png");
        segmentTexture = new Texture(Gdx.files.internal(image));
        eyes = new Texture(Gdx.files.internal("eyes.png"));
        vector = new Vector2(0,1);
    }

    public float getRadius()
    {
        return radius;
    }

    public void update(float deltaTime)
    {
        move(deltaTime);

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
        if (countDonuts % 1 == 0)
        {
            segments.add(new Segment(segments.get(segments.size() - 1).getX(), segments.get(segments.size() - 1).getY()));
        }
    }

    public int getCountDonuts()
    {
        return countDonuts;
    }

    public boolean absorbing(ArrayList<Donut> donuts)
    {
        for (Donut donut : donuts)
        {
            Vector2 vector = new Vector2(donut.getX() - segments.get(0).getX(), donut.getY() - segments.get(0).getY());
            if (vector.len() <= radius + donut.getRadius())
            {
                setCountBall();
                donut.relocation();
                return true;
            }
        }
        return false;
    }

    protected void move(float deltaTime)
    {
        float x = Gdx.input.getX() + world.getCameraX();
        float y = Gdx.graphics.getHeight() - Gdx.input.getY() + world.getCameraY();

        Vector2 newVector = new Vector2(x - segments.get(0).getX(), y - segments.get(0).getY());
        moveInDirection(deltaTime, newVector);
    }

    protected void moveInDirection(float deltaTime, Vector2 newVector)
    {
        float delta = vector.angle(newVector);


        if (Math.abs(delta) > MAX_ANGLE_DELTA)
        {
            vector.rotate(MAX_ANGLE_DELTA * Math.signum(delta));
        }
        else
        {
            vector.rotate(delta);
        }

        float direction = vector.angleRad();
        double cos = Math.cos(direction);
        double sin = Math.sin(direction);
        float s = speed * deltaTime;
        segments.get(0).setX((float)(segments.get(0).getX() + s * cos));
        segments.get(0).setY((float)(segments.get(0).getY() + s * sin));
    }

    public void draw(SpriteBatch batch)
    {
        for (int i = segments.size() - 1; i >= 0; i--)
        {
            batch.draw(segmentTexture,
                    segments.get(i).getX() - radius - world.getCameraX(),
                    segments.get(i).getY() - radius - world.getCameraY(),
                    radius * 2,
                    radius * 2);
        }
        float eyesRadius = radius / 2.5f;
        float leftEyeX = segments.get(0).getX() + radius / 1.5f * (float) (Math.cos(vector.angleRad() + Math.PI/ 4)) - eyesRadius - world.getCameraX();
        float rightEyeX = segments.get(0).getX() + radius / 1.5f * (float) (Math.cos(vector.angleRad() - Math.PI/ 4)) - eyesRadius - world.getCameraX();
        float leftEyeY = segments.get(0).getY() + radius / 1.5f * (float) (Math.sin(vector.angleRad() + Math.PI/ 4)) - eyesRadius - world.getCameraY();
        float rightEyeY = segments.get(0).getY() + radius / 1.5f * (float) (Math.sin(vector.angleRad() - Math.PI/ 4)) - eyesRadius - world.getCameraY();
        batch.draw(eyes, leftEyeX, leftEyeY, 2 * eyesRadius, 2 * eyesRadius);
        batch.draw(eyes, rightEyeX, rightEyeY, 2 * eyesRadius, 2 * eyesRadius);
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
            DonutBonus donut = new DonutBonus(world,segments.get(i).getX() + (float)(Math.random() * 8), segments.get(i).getY() - (float)(Math.random() * 8));
            donuts.add(donut);
            gameObjects.add(donut);
        }
    }

    public int getCountKills()
    {
        return countKills;
    }
}
