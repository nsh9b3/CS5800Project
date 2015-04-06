package com.team.main;

import com.team.drawables.Line;
import com.team.drawables.Sphere;
import com.team.drawables.Triangle;

import java.applet.*;
import java.awt.*;
import java.util.Arrays;

public class Main extends Applet
{
    //dimensions in pixels
    int width = 1280;
    int height = 720;

    Triangle tri1;
    Triangle tri2;
    Line line;
    Sphere sphere1;

    public void init()
    {
        setSize(width, height);
        setBackground(Color.white);
        tri1 = new Triangle(width, height, true, "A");
        tri2 = new Triangle(width, height, false, "B");
        line = new Line(tri1.getClosestXPoint(), tri1.getClosestYPoint(), tri2.getClosestXPoint(), tri2.getClosestYPoint());
        sphere1 = new Sphere(width / 2, height / 2, 50, 50, 50, new int[]{}, new int[]{}, new int[]{}, 10, "red");
    }

    @Override
    public void paint(Graphics g)
    {
        tri1.draw(g);
        tri2.draw(g);
        line.draw(g);
        try
        {
            sphere1.draw(g);
        } catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
    }

}
