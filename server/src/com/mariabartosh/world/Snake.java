package com.mariabartosh.world;

import com.badlogic.gdx.math.Vector2;
import com.mariabartosh.net.Connection;
import java.util.ArrayList;

public class Snake extends GameObject
{
    static final float MAX_ANGLE_DELTA = 3;
    private ArrayList<Segment> segments = new ArrayList<>();
    private float radius;
    private float segmentDistance;
    private int segmentTextureIndex;
    Vector2 vector;
    World world;
    private String name;
    private int score;
    private int countKills;
    private Connection connection;
    private boolean dead;

    public Snake(World world, int segmentCount, float radius, String name)
    {
        this.name = name;
        this.world = world;
        this.radius = radius;
        segmentDistance = radius / 3;
        
        float headX = (float) Math.random() * world.getWidth() * 0.8f + world.getWidth() * 0.1f;
        float headY = (float) Math.random() * world.getHeight() * 0.9f;

        for (int i = 0; i < segmentCount; i++)
        {
            segments.add(new Segment(headX, headY - segmentDistance * i));
        }

        segmentTextureIndex = (int) (Math.random() * 11);

        vector = new Vector2(0, 1);
    }

    public float getRadius()
    {
        return radius;
    }

    public void update(float x, float y)
    {
        segments.get(0).setX(x);
        segments.get(0).setY(y);
        updateSegments();
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

    void updateSegments()
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

    public boolean updateState()
    {
        score += 50;
        if (segments.size() <= 250)
        {
            segments.add(new Segment(segments.get(segments.size() - 1).getX(), segments.get(segments.size() - 1).getY()));
            return true;
        }
        return false;
    }

    public boolean eat(Donut donut)
    {
        Vector2 vector = new Vector2(donut.getX() - getHeadX(), donut.getY() - getHeadY());
        return (vector.len() <= radius + donut.getRadius());
    }

    public boolean checkCollision(Snake snake)
    {
        for (int i = 1; i < this.segments.size(); i++)
        {
            Vector2 vector = new Vector2(snake.getHeadX() - this.segments.get(i).getX(), snake.getHeadY() - this.segments.get(i).getY());
            if (vector.len() <= radius + snake.getRadius())
            {
                this.countKills++;
                if (radius <= 100)
                {
                    this.radius = (float) (20 * Math.sqrt((this.countKills + 1) / 1.2));
                }
                this.score += 500;
                return true;
            }
        }
        return false;
    }

    public ArrayList<DonutBonus> breakdown()
    {
        ArrayList<DonutBonus> donutBonuses = new ArrayList<>();
        for (int i = 0; i < segments.size(); i += 10)
        {
            DonutBonus donut = new DonutBonus(world, segments.get(i).getX() + (float) (Math.random() * 8), segments.get(i).getY() - (float) (Math.random() * 8));
            donutBonuses.add(donut);
            world.getDonuts().add(donut);
            world.getGameObjects().put(donut.getId(), donut);
        }
        return donutBonuses;
    }

    public float getHeadX()
    {
        return segments.get(0).getX();
    }

    public float getHeadY()
    {
        return segments.get(0).getY();
    }

    public String getName()
    {
        return name;
    }

    public float getScore()
    {
        return score;
    }

    public ArrayList<Segment> getSegments()
    {
        return segments;
    }

    public int getSegmentTextureIndex()
    {
        return segmentTextureIndex;
    }

    public Connection getConnection()
    {
        return connection;
    }

    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public void onRemove()
    {
        if (connection != null)
        {
            connection.setPlayer(null);
            connection = null;
        }
    }

    boolean isDead()
    {
        return dead;
    }

    void setDead(boolean dead)
    {
        this.dead = dead;
    }

    public float getSegmentDistance()
    {
        return segmentDistance;
    }
}
