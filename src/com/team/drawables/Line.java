package com.team.drawables;

import java.awt.Graphics;

public class Line
{
    int xPos0, xPos1;
    int yPos0, yPos1;

    public Line(int xPos0, int yPos0, int xPos1, int yPos1)
    {
        this.xPos0 = xPos0;
        this.yPos0 = yPos0;
        this.xPos1 = xPos1;
        this.yPos1 = yPos1;
    }

    public void draw(Graphics g)
    {
        g.drawLine(xPos0, yPos0, xPos1, yPos1);
    }
}