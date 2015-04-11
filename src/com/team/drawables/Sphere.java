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

    boolean inCriticalSection;
    int criticalSectionMin1;
    int criticalSectionMax1;
    int criticalSectionMin2;
    int criticalSectionMax2;

    public Sphere(int radius, int numLinePos, Point[] linePoints, int numTriPos, Point[] triPoints1, Point[] triPoints2, int speed, boolean startLeft, boolean isFirst, String color, String title)
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
        criticalSectionMin1 = numTriPos - 1;
        for (int i = numTriPos - 1; i < (numTriPos + numLinePos) - 1; i++)
        {
            points[i] = linePoints[i - numTriPos + 1];
        }
        criticalSectionMax1 = (numTriPos + numLinePos) - 2;
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
        criticalSectionMin2 = (2 * numTriPos + numLinePos) - 2;
        for (int i = (2 * numTriPos) + numLinePos - 2; i < (2 * numTriPos + 2 * numLinePos) - 2; i++)
        {
            points[i] = linePoints[numLinePos - 1 - k];
            k = k + 1;
        }
        criticalSectionMax2 = (2 * numTriPos + 2 * numLinePos) - 3;


        this.center = new Point(points[pointPos]);
        this.inCriticalSection = false;
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
        if((pointPos >= criticalSectionMin1 && pointPos <= criticalSectionMax1) || (pointPos >= criticalSectionMin2 && pointPos <= criticalSectionMax2))
            g.setColor(Color.GREEN);
        g.fillOval((int) center.getX() - (radius / 2), (int) center.getY() - (radius / 2), radius, radius);
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        int xCent = (int) g.getFontMetrics().stringWidth(title);
        int yCent = (int) g. getFontMetrics().getStringBounds(title, g).getHeight();
        g.drawString(title, center.getX() - (xCent / 2), center.getY() - (yCent / 4));
        g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        xCent = (int) g.getFontMetrics().stringWidth("Speed " + String.valueOf(speed));
        yCent = (int) g. getFontMetrics().getStringBounds("Speed " + String.valueOf(speed), g).getHeight();
        g.drawString("Speed " + String.valueOf(speed), center.getX() - (xCent / 2), (int) center.getY() + (yCent / 2));
        updatePosition(g);
    }

    public void updatePosition(Graphics g)
    {
        pointPos = (pointPos + speed) % numLocations;
        center = points[pointPos];
        /*this.nextPoint = points[(pointPos + speed) % numLocations];
        // Only show timestamp when your at the line.
        if (Arrays.asList(this.line).contains(this.nextPoint))
        {
            makeRequests = true;
            time.setTime(Calendar.getInstance().getTime().getTime());
            g.drawString(time.toString(), (int) center.getX() + (radius / 2), (int) center.getY() - (radius / 2));
        } else
        {
            g.drawString("", (int) center.getX() + (radius / 2), (int) center.getY() - (radius / 2));
        }*/
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

    public void setInCriticalSection(boolean inCriticalSection)
    {
        this.inCriticalSection = inCriticalSection;
    }

    public boolean getIsInCriticalSection()
    {
        return this.inCriticalSection;
    }

}
