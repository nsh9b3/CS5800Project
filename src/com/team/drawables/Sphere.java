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

    // Current location
    boolean inCS = false;
    boolean onRightSide = false;

    // Future Location
    boolean goingToCS = false;
    boolean goingToRightSide = false;

    String title;
    Point[] points;
    Point[] leftTriangle;
    Point[] rightTriangle;

    // the other spheres, this way they can talk to one another.
    Sphere[] fellowSpheres;
    String color;

    // Debugging strings
    String currentSpot = "";
    String nextSpot = "";

    Point[] line;

    // the time stamp
    Timestamp time = new Timestamp(Calendar.getInstance().getTime().getTime());

    public Sphere(int radius, int numLinePos, Point[] linePoints, int numTriPos, Point[] triPoints1, Point[] triPoints2, int speed, boolean startLeft, boolean isFirst, String color, String title)
    {

        this.radius = radius;
        this.speed = speed;
        this.color = color.toUpperCase();
        this.title = String.valueOf(title);
        line = linePoints;
        leftTriangle = triPoints1;
        rightTriangle = triPoints2;

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
        this.time.setTime(Calendar.getInstance().getTime().getTime());
        whereAmI();
        g.drawString("Entering CS? " + this.goingToCS, center.getX() + (radius) + 85, (center.getY() + radius) - 150);

        g.drawString("Permission " + String.valueOf(askEveryone()), center.getX() - (radius), center.getY() - radius);

        g.setColor(Color.black);
        g.drawString(title, center.getX() - (radius / 2), center.getY() - (radius / 2));
        g.drawString("Speed " + String.valueOf(speed), center.getX() - (radius), center.getY() + radius);
        if(this.inCS){
            this.currentSpot = "Center";
        }else if(this.onRightSide){
            this.currentSpot = "Right Side";
        }else{
            this.currentSpot = "Left Side";
        }
        if(this.goingToCS){
            this.nextSpot = "Going to Center";
        }else if(this.goingToRightSide){
            this.nextSpot = "Going to Right";
        }
        else{
            this.nextSpot = "Going to Left";
        }

        //g.drawString("Location " + this.currentSpot, center.getX() - (radius), 35 + (center.getY() + radius));
        //g.drawString(this.nextSpot, center.getX() - (radius), 65 + (center.getY() + radius));

        // BEFORE updatingPosition see if you have permission to move onwards
        finalDraw(g);
    }

    private void finalDraw(Graphics g) throws NoSuchFieldException{
        Color spColor;
        try
        {
            Field field = Class.forName("java.awt.Color").getField(this.color);
            spColor = (Color) field.get(null);
            g.setColor(spColor);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        g.fillOval(center.getX() - (radius / 2), center.getY() - (radius / 2), radius, radius);

        if(this.goingToCS)
        {
            if(this.inCS){
                updatePosition(g);
            }
            if(askEveryone() == false)
            {
                return;
            }else{
                updatePosition(g);
            }
        }else{
            updatePosition(g);

        }
    }

    private void updatePosition(Graphics g)
    {
        pointPos = (pointPos + speed) % numLocations;
        center = points[pointPos];
        this.time.setTime(Calendar.getInstance().getTime().getTime());
        g.drawString("Time " + String.valueOf(time), center.getX() - (radius), 55 + center.getY() - radius);
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

    public void whoAreMyFellowSpheres(Sphere[] spheres)
    {
        fellowSpheres = new Sphere[spheres.length-1];
        int index = 0;
        for(int i = 0; i < spheres.length; i++){
            if(spheres[i] != this){
               fellowSpheres[index] = spheres[i];
                index++;
            }
        }
    }

    private void whereAmI(){
        this.time.setTime(Calendar.getInstance().getTime().getTime());

        // Current location
        this.inCS = Arrays.asList(line).contains(this.center);
        this.onRightSide = Arrays.asList(rightTriangle).contains(this.center);

        Point futureCenter;
        if(!onRightSide){
            futureCenter = points[(pointPos + speed) % numLocations];

        }else{
            int temp = (pointPos + speed) % numLocations;
            temp = (temp + speed) % numLocations;
            futureCenter = points[temp];
        }

        // Future Location
        this.goingToCS = Arrays.asList(line).contains(futureCenter);
        this.goingToRightSide = Arrays.asList(rightTriangle).contains(futureCenter);
    }

    private boolean askEveryone(){
        boolean verdict = true;
        this.time.setTime(Calendar.getInstance().getTime().getTime());
        for (int i = 0; i < fellowSpheres.length; i++)
        {
            if(fellowSpheres[i].areYouInTheCS() || fellowSpheres[i].areYouGoingInToCS(time))
            {
                verdict = false;
            }
        }
        return verdict;
    }

    public boolean areYouGoingInToCS(Timestamp timeToCheck){
        if(this.time.before(timeToCheck))
           return this.goingToCS;
        else
            return false;
    }

    public boolean areYouInTheCS(){
        return this.inCS;
    }

}
