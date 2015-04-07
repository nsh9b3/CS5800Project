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

    public void init()
    {
        setSize(width, height);
        setBackground(Color.white);

        tri1 = new Triangle(width, height, 3, true, "A");
        tri2 = new Triangle(width, height, 3, false, "B");
    }

    @Override
    public void paint(Graphics g)
    {
        tri1.draw(g);
        tri2.draw(g);
    }

}
