package com.team.drawables;

import com.team.Point;

import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;

public class Triangle
{
    int[] xPos;
    int[] yPos;
    int closestPoint;

    boolean isLeft;
    int screenHeight;
    int screenWidth;

    String x;

    Point[] points;

    String name;

    int width;
    int height;
    int numPointsPerLine;
    int numPointsTotal;

    int nameLocationX;
    int nameLocationY;

    public Triangle(int width, int height, int numPointsTotal, boolean isLeft, String name)
    {
        this.width = width;
        this.height = height;
        this.numPointsTotal = numPointsTotal;

        //If the number of points is not divisible by 3... make it
        while (numPointsTotal % 3 != 0)
        {
            numPointsTotal++;
        }

        points = new Point[numPointsTotal];

        this.numPointsPerLine = numPointsTotal / 3;

        this.isLeft = isLeft;
        this.name = name;

        int halfWidth = width / 2;
        int halfHeight = height / 2;

        int maxHeight = height - (int) (halfHeight + halfHeight / Math.sqrt(3));
        int minHeight = height - (int) (halfHeight - halfHeight / Math.sqrt(3));

        int fourthWidth = halfWidth / 2;
        int furthestPoint;

        if (isLeft)
        {
            closestPoint = (fourthWidth + halfWidth) / 2;
            furthestPoint = (fourthWidth) / 2;
        } else
        {
            closestPoint = (halfWidth + halfWidth + fourthWidth) / 2;
            furthestPoint = (halfWidth + fourthWidth + width) / 2;
        }

        points[0] = new Point(furthestPoint, maxHeight);
        points[numPointsPerLine] = new Point(closestPoint, halfHeight);
        points[2 * numPointsPerLine] = new Point(furthestPoint, minHeight);

        xPos = new int[] {furthestPoint, closestPoint, furthestPoint};
        yPos = new int[] {maxHeight, halfHeight, minHeight};

        for (int i = 0; i < 3; i++)
        {
            int xIncrement = (points[((i + 1) % 3) * numPointsPerLine].getX() - points[i * numPointsPerLine].getX()) / numPointsPerLine;
            int yIncrement = (points[((i + 1) % 3) * numPointsPerLine].getY() - points[i * numPointsPerLine].getY()) / numPointsPerLine;

            for (int k = 1; k < numPointsPerLine; k++)
            {
                points[(i * numPointsPerLine) + k] = new Point(points[(i * numPointsPerLine) + k - 1].getX() + xIncrement, points[(i * numPointsPerLine) + k - 1].getY() + yIncrement);
            }
        }

        if (isLeft)
            nameLocationX = furthestPoint + (closestPoint - furthestPoint) / 2;
        else
            nameLocationX = closestPoint + (furthestPoint - closestPoint) / 2;
        nameLocationY = halfHeight;
    }

    public void draw(Graphics g)
    {
        g.drawPolygon(xPos, yPos, 3);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        int xCent = (int) g.getFontMetrics().stringWidth(name);
        int yCent = (int) g. getFontMetrics().getStringBounds(name, g).getHeight();
        g.drawString(name, nameLocationX - (xCent / 2), nameLocationY + (yCent / 4));
    }

    public Point[] getPoints()
    {
        return points;
    }
}

