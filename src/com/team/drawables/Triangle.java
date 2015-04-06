package com.team.drawables;

import java.awt.Font;
import java.awt.Graphics;

public class Triangle
{
    int[] xPos;
    int[] yPos;
    int closestPoint;

    boolean isLeft;
    int screenHeight;
    int screenWidth;

    String x;

    public Triangle(int width, int height, boolean isLeft, String x)
    {
        screenHeight = height;
        screenWidth = width;

        this.isLeft = isLeft;
        this.x = x;

        int halfWidth = (int) Math.floor(width / 2);
        int halfHeight = (int) Math.floor(height / 2);

        int maxHeight = height - (int) (halfHeight + halfHeight / Math.sqrt(3));
        int minHeight = height - (int) (halfHeight - halfHeight / Math.sqrt(3));

        yPos = new int[]{maxHeight, halfHeight, minHeight};

        if(isLeft)
        {
            int fourthWidth = (int) Math.floor(halfWidth / 2);
            closestPoint = (fourthWidth + halfWidth) / 2;
            int furthestPoint = (fourthWidth) / 2;

            xPos = new int[]{furthestPoint, closestPoint, furthestPoint};
        }
        else
        {
            int fourthWidth = width - (int) Math.floor(halfWidth / 2);
            closestPoint = (halfWidth + fourthWidth) / 2;
            int furthestPoint = (fourthWidth + width) / 2;

            xPos = new int[]{furthestPoint, closestPoint, furthestPoint};
        }
    }

    public int getClosestXPoint()
    {
        return closestPoint;
    }

    public int getClosestYPoint()
    {
        return (int) Math.floor(screenHeight / 2);
    }

    public void draw(Graphics g)
    {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        g.drawPolygon(xPos, yPos, 3);
        if(isLeft)
            g.drawString(x, screenWidth / 4, screenHeight / 2);
        else
            g.drawString(x, (3 * screenWidth) / 4, screenHeight / 2);
    }

    public int[] getXPositions(int numLoc)
    {
        while(numLoc % 3 != 0)
        {
            numLoc = numLoc + 1;
        }

        int[] allXPos = new int[numLoc];
        int thirdLoc = numLoc / 3;

        allXPos[0] = xPos[0];
        allXPos[thirdLoc] = xPos[1];
        allXPos[2 * thirdLoc] = xPos[2];

        for(int i = 0; i < 3; i++)
        {
            int xDiff = (allXPos[((i + 1) % 3) * thirdLoc] - allXPos[i * thirdLoc]) / thirdLoc;

            for(int k = 1; k < thirdLoc; k++)
            {
                allXPos[i * thirdLoc + k] = allXPos[i * thirdLoc] + k * xDiff;
            }
        }
        return allXPos;
    }

    public int[] getYPositions(int numLoc)
    {
        while(numLoc % 3 != 0)
        {
            numLoc = numLoc + 1;
        }

        int[] allYPos = new int[numLoc];
        int thirdLoc = numLoc / 3;

        allYPos[0] = yPos[0];
        allYPos[thirdLoc] = yPos[1];
        allYPos[2 * thirdLoc] = yPos[2];

        for(int i = 0; i < 3; i++)
        {
            int xDiff = (allYPos[((i + 1) % 3) * thirdLoc] - allYPos[i * thirdLoc]) / thirdLoc;

            for(int k = 1; k < thirdLoc; k++)
            {
                allYPos[i * thirdLoc + k] = allYPos[i * thirdLoc] + k * xDiff;
            }
        }
        return allYPos;
    }
}

