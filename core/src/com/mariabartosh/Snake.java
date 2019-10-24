package com.mariabartosh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Snake extends GameObject implements Comparable<Snake>
{
    static final float MAX_ANGLE_DELTA = 3;
    private ArrayList<Segment> segments = new ArrayList<>();
    private float radius;
    private float segmentDistance;
    private TextureAtlas.AtlasRegion segmentTexture;
    private TextureAtlas.AtlasRegion eyes;
    private Vector2 vector;
    private World world;
    private String name;
    private int score;
    private boolean ignored;

    public Snake(World world, float radius, String name, int imageIndex, int id)
    {
        super(id);
        this.name = name;
        this.world = world;
        this.radius = radius;
        segmentDistance = radius / 3;
        segmentTexture = Assets.images.segments.get(imageIndex);
        eyes = Assets.images.eyes;
        vector = new Vector2(0, 1);
    }

    float getRadius()
    {
        return radius;
    }

    public void update(float deltaTime)
    {
        move(deltaTime);
        updateSegments();
    }

    public void update(float x, float y)
    {
        vector.x = x - segments.get(0).getX();
        vector.y = y - segments.get(0).getY();
    }

    private void updateSegments()
    {
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
            current.setX((float) (current.getX() + l * cos));
            current.setY((float) (current.getY() + l * sin));
        }
    }

    Donut eat(ArrayList<Donut> donuts)
    {
        for (Donut donut : donuts)
        {
            if (donut.isIgnored())
            {
                continue;
            }
            Vector2 vector = new Vector2(donut.getX() - getHeadX(), donut.getY() - getHeadY());
            if (vector.len() <= radius + donut.getRadius())
            {
                return donut;
            }
        }
        return null;
    }

    private void move(float deltaTime)
    {
        if (this == world.getPlayer())
        {
            float x = Gdx.input.getX() + world.getCameraX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY() + world.getCameraY();

            Vector2 newVector = new Vector2(x - getHeadX(), y - getHeadY());
            moveInDirection(deltaTime, newVector);
        }
        else
        {
            moveInDirection(deltaTime, vector);
        }

    }

    void moveInDirection(float deltaTime, Vector2 newVector)
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
        float speed = 150;
        float s = speed * deltaTime;
        segments.get(0).setX((float) (getHeadX() + s * cos));
        segments.get(0).setY((float) (getHeadY() + s * sin));
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
        float leftEyeX = getHeadX() + radius / 1.5f * (float) (Math.cos(vector.angleRad() + Math.PI / 4)) - eyesRadius - world.getCameraX();
        float rightEyeX = getHeadX() + radius / 1.5f * (float) (Math.cos(vector.angleRad() - Math.PI / 4)) - eyesRadius - world.getCameraX();
        float leftEyeY = getHeadY() + radius / 1.5f * (float) (Math.sin(vector.angleRad() + Math.PI / 4)) - eyesRadius - world.getCameraY();
        float rightEyeY = getHeadY() + radius / 1.5f * (float) (Math.sin(vector.angleRad() - Math.PI / 4)) - eyesRadius - world.getCameraY();
        batch.draw(eyes, leftEyeX, leftEyeY, 2 * eyesRadius, 2 * eyesRadius);
        batch.draw(eyes, rightEyeX, rightEyeY, 2 * eyesRadius, 2 * eyesRadius);

        Assets.fonts.game.draw(batch, name, getHeadX() + radius * 2 - world.getCameraX(), getHeadY() + radius * 2 - world.getCameraY());
    }

    Snake checkCollision(ArrayList<Snake> snakes)
    {
        for (Snake snake : snakes)
        {
            if (snake != this && !snake.ignored)
            {
                for (int i = 1; i < this.segments.size(); i++)
                {
                    Vector2 vector = new Vector2(snake.getHeadX() - this.segments.get(i).getX(), snake.getHeadY() - this.segments.get(i).getY());
                    if (vector.len() <= radius + snake.getRadius())
                    {
                        System.out.println("segment: " + i);
                        return snake;
                    }
                }
            }
        }
        return null;
    }

    float getHeadX()
    {
        return segments.get(0).getX();
    }

    float getHeadY()
    {
        return segments.get(0).getY();
    }

    String getName()
    {
        return name;
    }

    int getScore()
    {
        return score;
    }

    @Override
    public int compareTo(Snake snake)
    {
        return (int) (snake.getScore() - score);
    }

    void setName(String name)
    {
        this.name = name;
    }

    public void addSegment(float x, float y)
    {
        segments.add(new Segment(x, y));
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public ArrayList<Segment> getSegments()
    {
        return segments;
    }

    public void setRadius(float radius)
    {
        this.radius = radius;
    }

    public void setIgnored(boolean ignored)
    {
        this.ignored = ignored;
    }
}
