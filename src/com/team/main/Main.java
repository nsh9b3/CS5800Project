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

    Sphere[] spheres;

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
        String color;
        boolean left,first;
        String[] titles = {"1/Q","2/W","3/E","4/R"};
        addKeyListener(this);
        setSize(width, height);
        setBackground(Color.white);

        tri1 = new Triangle(width, height, numPointsPerTriangle, true, "A");
        tri2 = new Triangle(width, height, numPointsPerTriangle, false, "B");

        int closestPointIndex = numPointsPerTriangle / 3;
        line = new Line(tri1.getPoints()[closestPointIndex], tri2.getPoints()[closestPointIndex], numPointsPerLine);

        spheres = new Sphere[4];

        for(int i = 0; i < spheres.length; i++){
            if(i < 2){
                color = "blue";
                left = true;
                first = (i == 0) ? true: false;
            }
            else
            {
                color = "red";
                left = false;
                first = (i == 2) ? true: false;
            }
            spheres[i] = new Sphere(50, numPointsPerLine, line.getPoints(), numPointsPerTriangle, tri1.getPoints(), tri2.getPoints(), 1, left,first,color,titles[i]);
        }
        for(int i=0; i < spheres.length; i++){
            spheres[i].whoAreMyFellowSpheres(spheres);
        }
    }

    public void paint(Graphics g)
    {
        g.drawString("Press the corresponding number or letter key to speed up or slow down the sphere.",100,50);

        tri1.draw(g);
        tri2.draw(g);
        line.draw(g);
        try
        {
            for(int i=0; i < spheres.length; i++){
                spheres[i].draw(g);
            }
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
        if (dir == '1' || dir == 'q')
        {
            spheres[0].changeSpeed((dir == '1'));
        } else if (dir == '2' || dir == 'w')
        {
            spheres[1].changeSpeed((dir == '2'));
        } else if (dir == '3' || dir == 'e')
        {
            spheres[2].changeSpeed((dir == '3'));
        } else if (dir == '4' || dir == 'r')
        {
            spheres[3].changeSpeed((dir == '4'));
        }
    }
}
