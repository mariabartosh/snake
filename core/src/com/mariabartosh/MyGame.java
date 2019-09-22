package com.mariabartosh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MyGame extends ApplicationAdapter
{
    ShapeRenderer shapeRenderer;
    Snake snake;

    @Override
    public void create()
    {
        shapeRenderer = new ShapeRenderer();
        snake = new Snake(50, 10);
    }

    @Override
    public void render()
    {
		snake.advance(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 1, 0, 1);
        for (Segment segment : snake.segments)
        {
            shapeRenderer.circle(segment.getX(), segment.getY(), snake.getRadius());
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose()
    {
        shapeRenderer.dispose();
    }
}
