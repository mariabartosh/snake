package com.mariabartosh.net;

import java.util.ArrayList;

public interface ServerSnake
{
    int getId();

    ArrayList<ServerSegment> getSegments();

    int getSegmentTextureIndex();

    float getRadius();

    String getName();

    float getHeadX();

    float getHeadY();
}
