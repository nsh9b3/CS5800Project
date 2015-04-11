package com.team.drawables;

import com.team.Point;

import java.awt.*;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Calendar;

import java.util.Arrays;


public class Sphere
{
    Point center;
    int radius;

    int speed;
    int pointPos;
    int numLocations;

    String title;
    Point nextPoint;
    Point[] points;

    String color;

    Point[] line;
    Timestamp time = new Timestamp(Calendar.getInstance().getTime().getTime());
    boolean makeRequests = false;

    public Sphere(int radius, int numLinePos, Point[] linePoints, int numTriPos, Point[] triPoints1, Point[] triPoints2, int speed, boolean startLeft, boolean isFirst, String color,String title)
    {

        this.radius = radius;
        this.speed = speed;
        this.color = color.toUpperCase();
        this.title = String.valueOf(title);
        this.line = linePoints;
        numLocations = (2 * numTriPos) + (2 * numLinePos) - 2;

        points = new Point[numLocations];

        int k = (numTriPos / 3) + 1;
        for (int i = 0; i < (numTriPos - 1); i++)
        {
            points[i] = new Point(triPoints1[k]);
            if (startLeft)
            {
                if (isFirst && k == 0)
                    pointPos = i - 1;
                else if ((! isFirst) && (k == 2 * (numTriPos / 3)))
                    pointPos = i - 1;
            }
            k = (k + 1) % numTriPos;
        }
        for (int i = numTriPos - 1; i < (numTriPos + numLinePos) - 1; i++)
        {
            points[i] = linePoints[i - numTriPos + 1];
        }
        k = (numTriPos / 3) - 1;
        for (int i = numTriPos + numLinePos - 1; i < (2 * numTriPos + numLinePos) - 2; i++)
        {
            points[i] = triPoints2[k];
            if (! startLeft)
            {
                if (isFirst && k == 0)
                    pointPos = i - 1;
                else if ((! isFirst) && (k == 2 * (numTriPos / 3)))
                    pointPos = i - 1;
            }
            k = (k - 1);
            if (k < 0)
                k = numTriPos - 1;
        }
        k = 0;
        for (int i = (2 * numTriPos) + numLinePos - 2; i < (2 * numTriPos + 2 * numLinePos) - 2; i++)
        {
            points[i] = linePoints[numLinePos - 1 - k];
            k = k + 1;
        }


        this.center = new Point(points[pointPos]);

    }

    public void draw(Graphics g) throws NoSuchFieldException
    {
        Color spColor;
        time.setTime(Calendar.getInstance().getTime().getTime());
        try
        {
            Field field = Class.forName("java.awt.Color").getField(color);
            spColor = (Color) field.get(null);
            g.setColor(spColor);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        g.fillOval((int) center.getX() - (radius / 2), (int) center.getY() - (radius / 2), radius, radius);
        g.setColor(Color.black);
        g.drawString(title,(int) center.getX() - (radius / 2), (int) center.getY() - (radius / 2));
        g.drawString("Speed " + String.valueOf(speed), center.getX() - (radius), (int) center.getY() + radius);
        updatePosition(g);
    }

    public void updatePosition(Graphics g)
    {
        pointPos = (pointPos + speed) % numLocations;
        center = points[pointPos];
        this.nextPoint = points[(pointPos + speed) % numLocations];
// Only show timestamp when your at the line.
        if(Arrays.asList(this.line).contains(this.nextPoint))
        {
            makeRequests = true;
            time.setTime(Calendar.getInstance().getTime().getTime());
            g.drawString(time.toString(), (int) center.getX() + (radius / 2), (int) center.getY() - (radius / 2));
        }else{
            g.drawString("",(int) center.getX() + (radius / 2), (int) center.getY() - (radius / 2));
        }
    }

    public void changeSpeed(boolean inc)
    {
        if (inc)
        {
            speed = speed + 1;
        } else
        {
            speed = speed - 1;
            if (speed < 0)
                speed = 0;
        }
    }
}
