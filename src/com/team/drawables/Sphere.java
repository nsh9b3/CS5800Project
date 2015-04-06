package com.team.drawables;

import java.awt.*;
import java.lang.reflect.Field;

public class Sphere
{
    int xCenter;
    int yCenter;

    int radius;

    int[] xPoints;
    int[] yPoints;

    int speed;

    String color;

    public Sphere(int xCenter, int yCenter, int radius, int numTriLoc, int numLineLoc, int tri1Pos[], int linePos[], int tri2Pos[], int speed, String color)
    {
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.radius = radius;
        this.speed = speed;
        this.color = color.toUpperCase();
    }

    public void draw(Graphics g) throws NoSuchFieldException
    {
        Color spColor;
        try
        {
            Field field = Class.forName("java.awt.Color").getField(color);
            spColor = (Color)field.get(null);
            g.setColor(spColor);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        g.fillOval(xCenter, yCenter - (radius / 2), radius, radius);
    }
}
