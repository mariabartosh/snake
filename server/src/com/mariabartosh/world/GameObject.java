package com.mariabartosh.world;

public abstract class GameObject
{
    private static int nextObjectId;
    private int id;
    GameObject()
    {
        id = nextObjectId++;
    }

    public int getId()
    {
        return id;
    }

    public void onRemove()
    {

    }
}
