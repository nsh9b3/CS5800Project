package com.team.drawables;


import com.team.Point;

import java.awt.*;
import java.util.Arrays;

public class Line
{
    //This is the list of Points making up the line
    Point[] points;

    public Line(Point left, Point right, int numPoints)
    {
        //Create list of points used for the Line
        points = new Point[numPoints];

        //This list starts from the left most point and goes to the right most point
        points[0] = new Point(left);
        points[numPoints - 1] = new Point(right);

        //increment is the difference between each x value in the array points
        int increment = (right.getX() - left.getX()) / (numPoints - 1);

        //yValue is the same across all points
        int yValue = left.getY();

        //Create a for loop to fill points with data
        for (int i = 1; i < numPoints - 1; i++)
        {
            points[i] = new Point(points[i - 1].getX() + increment, yValue);
        }
    }

    public Point[] getPoints()
    {
        return points;
    }

    public void draw(Graphics g)
    {
        //Only use the first and last Point to draw the line
        g.drawLine(points[0].getX(), points[0].getY(), points[points.length - 1].getX(), points[points.length - 1].getY());
    }
}