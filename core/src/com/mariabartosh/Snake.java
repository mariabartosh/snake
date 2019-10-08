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
    private int countDonuts;
    private int countKills;
    private TextureAtlas.AtlasRegion segmentTexture;
    private TextureAtlas.AtlasRegion eyes;
    Vector2 vector;
    World world;
    private String name;
    private float score;

    Snake(World world, int segmentCount, float radius, String[] names)
    {
        name = names[(int)(Math.random() * (names.length - 1))];
        this.world = world;
        this.radius = radius;
        segmentDistance = radius / 3;

        float headX = (float) Math.random() * world.getWidth();
        float headY = (float) Math.random() * world.getHeight();

        for (int i = 0; i < segmentCount; i++)
        {
            segments.add(new Segment(headX, headY - segmentDistance * i));
        }

        segmentTexture = world.segmentTexture.get((int) (Math.random() * 11));
        eyes = world.eyesTexture;

        vector = new Vector2(0, 1);
    }

    float getRadius()
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
            current.setX((float) (current.getX() + l * cos));
            current.setY((float) (current.getY() + l * sin));
        }
    }

    private void setCountBall()
    {
        countDonuts++;
        score += 50;
        segments.add(new Segment(segments.get(segments.size() - 1).getX(), segments.get(segments.size() - 1).getY()));
    }

    int getCountDonuts()
    {
        return countDonuts;
    }

    boolean absorbing(ArrayList<Donut> donuts)
    {
        for (Donut donut : donuts)
        {
            Vector2 vector = new Vector2(donut.getX() - getHeadX(), donut.getY() - getHeadY());
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

        Vector2 newVector = new Vector2(x - getHeadX(), y - getHeadY());
        moveInDirection(deltaTime, newVector);
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

    public void draw(SpriteBatch batch, BitmapFont font)
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

        font.draw(batch, name, getHeadX() + radius * 2 - world.getCameraX(), getHeadY() + radius * 2 - world.getCameraY());
    }

    boolean checkCollision(ArrayList<Snake> snakes)
    {
        for (Snake snake : snakes)
        {
            if (snake != this)
            {
                for (int i = 1; i < snake.segments.size(); i++)
                {
                    Vector2 vector = new Vector2(this.getHeadX() - snake.segments.get(i).getX(), this.getHeadY() - snake.segments.get(i).getY());
                    if (vector.len() <= radius + snake.getRadius())
                    {
                        snake.radius *= 1.3;
                        snake.countKills++;
                        snake.score += 500;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    void breakdown(ArrayList<Donut> donuts, ArrayList<GameObject> gameObjects)
    {
        for (int i = 0; i < segments.size(); i += 6)
        {
            DonutBonus donut = new DonutBonus(world, segments.get(i).getX() + (float) (Math.random() * 8), segments.get(i).getY() - (float) (Math.random() * 8));
            donuts.add(donut);
            gameObjects.add(donut);
        }
    }

    int getCountKills()
    {
        return countKills;
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

    float getScore()
    {
        return score;
    }

    @Override
    public int compareTo(Snake snake)
    {
        return (int) (snake.getScore() - score);
    }
}
