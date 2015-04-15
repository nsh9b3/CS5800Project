package com.team.drawables;

import com.team.Point;

import java.awt.*;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Calendar;

import java.util.Arrays;
import java.util.Random;


public class Sphere
{
    Point center;
    int radius;

    int speed;
    int pointPos;
    int numLocations;

    String title;
    Point[] points;

    // the other spheres, this way they can talk to one another.
    Sphere[] fellowSpheres;
    String color;

    Point[] line;

    // the time stamp
    Timestamp time = new Timestamp(Calendar.getInstance().getTime().getTime());

    // attempt to keep spheres out of CS
    boolean permission;
    boolean allowOthers;

    public Sphere(int radius, int numLinePos, Point[] linePoints, int numTriPos, Point[] triPoints1, Point[] triPoints2, int speed, boolean startLeft, boolean isFirst, String color, String title)
    {

        this.radius = radius;
        this.speed = speed;
        this.color = color.toUpperCase();
        this.title = String.valueOf(title);
        this.line = linePoints;
        this.permission = false;
        this.allowOthers = true;

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
        g.drawString(title, (int) center.getX() - (radius / 2), (int) center.getY() - (radius / 2));
        g.drawString("Speed " + String.valueOf(speed), center.getX() - (radius), (int) center.getY() + radius);
        // BEFORE updatingPosition see if you have permission to move onwards
        if(allowedToMove())
        {
            updatePosition(g);
        }
    }

    private boolean allowedToMove()
    {
        if(inCS()){
            return true;
        }else{
            if(wantIntoCS()){
                this.time.setTime(Calendar.getInstance().getTime().getTime());
                makeRequests();
                if (this.permission == false)
                {
                    return false;
                }
                return true;
            }else{
                return true;
            }
        }
    }

    private void updatePosition(Graphics g)
    {
        g.drawString("Permission: "+Boolean.toString(permission), (int) center.getX() + (radius * 2), (int) center.getY() - (radius * 2));
        g.drawString("Allow Others: "+Boolean.toString(allowOthers), (int) center.getX() + (radius * 2), (int) center.getY() - (radius / 2));

        pointPos = (pointPos + speed) % numLocations;
        center = points[pointPos];
        g.drawString("", (int) center.getX() + (radius / 2), (int) center.getY() - (radius / 2));
        if (!inCS())
        {
            this.permission = false;
        }
    }

    public void changeSpeed(boolean inc)
    {
        if (inc)
        {
            speed++;
        } else
        {
            speed--;
            if (speed < 0)
                speed = 0;
        }
    }

    // Tells sphere who the other spheres are
    public void whoAreMyFellowSpheres(Sphere[] left, Sphere[] right)
    {
        fellowSpheres = new Sphere[left.length + right.length];
        fellowSpheres[0] = left[0];
        fellowSpheres[1] = left[1];
        fellowSpheres[2] = right[0];
        fellowSpheres[3] = right[1];
    }


    public void whoAreMyFellowSpheres(Sphere[] spheres)
    {
        fellowSpheres = new Sphere[spheres.length-1];
        for(int i = 0; i < fellowSpheres.length; i++){
            if(spheres[i] != this){
               fellowSpheres[i] = spheres[i];
            }
        }
    }


    // Finds out if other spheres are ok with this accessing CS
    private void makeRequests()
    {
        // Ask all spheres a question
        for (int i = 0; i < fellowSpheres.length; i++)
        {
            if (fellowSpheres[i] != this) // Dont ask yourself
            {
                // if any of your fellow spheres does not give the ok then you can't enter.
                if (fellowSpheres[i].okToEnterCS(this.time) == false)
                {
                    this.permission = false;
                    break;
                }
            }
        }
        // if no one says no then that means you can enter cs
        this.permission = true;
    }

    private boolean wantIntoCS()
    {
        Point nextPoint = points[(pointPos + speed) % numLocations];
        if (Arrays.asList(line).contains(nextPoint))
        {
            this.allowOthers = false;
            return true;
        } else
        {
            this.allowOthers = true;
            return false;
        }
    }


    private boolean inCS()
    {
        if (Arrays.asList(line).contains(points[pointPos]))
        {
            this.allowOthers = false;
            return true;
        } else
        {
            this.allowOthers = true;
            return false;
        }
    }

    public boolean okToEnterCS(Timestamp stamp)
    {
        //return allowOthers;
        if (this.allowOthers)
        {
            if (wantIntoCS())
            {
                if (stamp.after(time))
                {
                    return true;
                } else
                {
                    return false;
                }
            } else
            {
                return true;
            }
        }
        else{
            return false;
        }

    }
}
