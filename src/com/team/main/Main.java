package com.team.main;

import com.team.drawables.Line;
import com.team.drawables.Sphere;
import com.team.drawables.Triangle;

import java.applet.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class Main extends Applet implements Runnable, KeyListener
{
    //dimensions in pixels
    int width = 1280;
    int height = 720;
    int numPointsPerTriangle = 99;
    int numPointsPerLine = 33;

    Triangle tri1;
    Triangle tri2;
    Line line;
    Sphere[] leftSpheres;
    Sphere[] rightSpheres;

    Thread runner;

    public void start()
    {
        if (runner == null)
        {
            runner = new Thread(this);
            runner.start();
        }
    }
// So...stop is deprecated ... ? oracle site suggests just setting the Tread to null only.
    public void stop()
    {
        if (runner != null)
        {
            runner.stop();
            runner = null;
        }
    }

    public void run()
    {
        while (true)
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
        addKeyListener(this);
        setSize(width, height);
        setBackground(Color.white);

        tri1 = new Triangle(width, height, numPointsPerTriangle, true, "A");
        tri2 = new Triangle(width, height, numPointsPerTriangle, false, "B");

        int closestPointIndex = numPointsPerTriangle / 3;
        line = new Line(tri1.getPoints()[closestPointIndex], tri2.getPoints()[closestPointIndex], numPointsPerLine);

        leftSpheres = new Sphere[2];
        rightSpheres = new Sphere[2];
        leftSpheres[0] = new Sphere(50, numPointsPerLine, line.getPoints(), numPointsPerTriangle, tri1.getPoints(), tri2.getPoints(), 1, true, true, "blue","1/Q");
        leftSpheres[1] = new Sphere(50, numPointsPerLine, line.getPoints(), numPointsPerTriangle, tri1.getPoints(), tri2.getPoints(), 1, true, false, "blue","2/W");
        rightSpheres[0] = new Sphere(50, numPointsPerLine, line.getPoints(), numPointsPerTriangle, tri1.getPoints(), tri2.getPoints(), 1, false, true, "red", "3/E");
        rightSpheres[1] = new Sphere(50, numPointsPerLine, line.getPoints(), numPointsPerTriangle, tri1.getPoints(), tri2.getPoints(), 1, false, false, "red", "4/R");
        leftSpheres[0].whoAreMyFellowSpheres(leftSpheres, rightSpheres);
        leftSpheres[1].whoAreMyFellowSpheres(leftSpheres, rightSpheres);
        rightSpheres[0].whoAreMyFellowSpheres(leftSpheres, rightSpheres);
        rightSpheres[1].whoAreMyFellowSpheres(leftSpheres, rightSpheres);

    }

    public void paint(Graphics g)
    {
        g.drawString("Press the corresponding number or letter key to speed up or slow down the sphere.",100,50);

        tri1.draw(g);
        tri2.draw(g);
        line.draw(g);
        try
        {
            leftSpheres[0].draw(g);
            leftSpheres[1].draw(g);
            rightSpheres[0].draw(g);
            rightSpheres[1].draw(g);
        } catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    // Creating key press to try to swap between "modes"
    @Override
    public void keyPressed(KeyEvent e)
    {
        char dir = e.getKeyChar();
        if (dir == 'c'){
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        char dir = e.getKeyChar();
        boolean inc;
        if (dir == '1' || dir == 'q')
        {
            if (dir == '1')
                inc = true;
            else
                inc = false;
            leftSpheres[0].changeSpeed(inc);
        } else if (dir == '2' || dir == 'w')
        {
            if (dir == '2')
                inc = true;
            else
                inc = false;
            leftSpheres[1].changeSpeed(inc);
        } else if (dir == '3' || dir == 'e')
        {
            if (dir == '3')
                inc = true;
            else
                inc = false;
            rightSpheres[0].changeSpeed(inc);
        } else if (dir == '4' || dir == 'r')
        {
            if (dir == '4')
                inc = true;
            else
                inc = false;
            rightSpheres[1].changeSpeed(inc);
        }
    }
}
