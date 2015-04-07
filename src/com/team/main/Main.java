package com.team.main;

import com.team.drawables.Line;
import com.team.drawables.Sphere;
import com.team.drawables.Triangle;

import java.applet.*;
import java.awt.*;
import java.util.Arrays;

public class Main extends Applet implements Runnable
{
    //dimensions in pixels
    int width = 1280;
    int height = 720;
    int numPointsPerTriangle = 9;
    int numPointsPerLine = 3;

    Triangle tri1;
    Triangle tri2;
    Line line;
    Sphere sp1;
    Sphere sp2;
    Sphere sp3;
    Sphere sp4;

    Thread runner;

    public void start()
    {
        if(runner == null)
        {
            runner = new Thread(this);
            runner.start();
        }
    }

    public void stop()
    {
        if(runner != null)
        {
            runner.stop();
            runner = null;
        }
    }

    public void run()
    {
        while(true)
        {
            repaint();
            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        }
    }


    public void init()
    {
        setSize(width, height);
        setBackground(Color.white);

        tri1 = new Triangle(width, height, numPointsPerTriangle, true, "A");
        tri2 = new Triangle(width, height, numPointsPerTriangle, false, "B");

        int closestPointIndex = numPointsPerTriangle / 3;
        line = new Line(tri1.getPoints()[closestPointIndex], tri2.getPoints()[closestPointIndex], numPointsPerLine);
        sp1 = new Sphere(50, numPointsPerLine, line.getPoints(), numPointsPerTriangle, tri1.getPoints(), tri2.getPoints(), 1, true, true, "blue");
        sp2 = new Sphere(50, numPointsPerLine, line.getPoints(), numPointsPerTriangle, tri1.getPoints(), tri2.getPoints(), 1, true, false, "blue");
        sp3 = new Sphere(50, numPointsPerLine, line.getPoints(), numPointsPerTriangle, tri1.getPoints(), tri2.getPoints(), 1, false, true, "red");
        sp4 = new Sphere(50, numPointsPerLine, line.getPoints(), numPointsPerTriangle, tri1.getPoints(), tri2.getPoints(), 1, false, false, "red");
    }

    public void paint(Graphics g)
    {
        tri1.draw(g);
        tri2.draw(g);
        line.draw(g);
        try
        {
            sp1.draw(g);
            sp2.draw(g);
            sp3.draw(g);
            sp4.draw(g);
        } catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
    }
}
