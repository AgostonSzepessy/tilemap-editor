/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapeditor.managers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import mapeditor.MapEditorPanel;

/**
 *
 * @author agoston
 */
public class Camera
{
    private int positionX;
    private int positionY;
    private int xBounds;
    private int yBounds;
    
    
    public Camera()
    {
	this(0, 0, 1000, 1000);
    }
    
    public Camera(int x, int y, int xBounds, int yBounds)
    {
	positionX = x;
	positionY = y;
	this.xBounds = xBounds;
	this.yBounds = yBounds;
    }
    
    /**
     *
     * @param img
     * @param x
     * @param y
     * @param g
     */
    public void draw(BufferedImage img, int x, int y, Graphics2D g)
    {
//	System.out.println("called");
	g.drawImage(img, x - positionX, y - positionY, null);
    }
    
    public void drawString(String text, int x, int y, Graphics2D g)
    {
	g.drawString(text, x - positionX, y - positionY);
    }
    
    public void setPosition(int x, int y)
    {
	positionX = x;
	positionY = y;
    }
    
    public void update(int tileSize)
    {
	checkBounds(tileSize);
    }
    
    private void checkBounds(int tileSize)
    {
//	System.out.println("camera positionX " + positionX);
	if(positionX + MapEditorPanel.WIDTH > xBounds)
	{
	    System.out.println("reset");
	    positionX = xBounds - (MapEditorPanel.WIDTH / tileSize * tileSize);
//	    int temp = positionX % tileSize;
//	    positionX += temp;
	}
	if(positionX < 0)
	    positionX = 0;
	if(positionY + MapEditorPanel.HEIGHT > yBounds)
	{
	    positionY = yBounds - MapEditorPanel.HEIGHT;
	    int temp = positionY % tileSize;
	    positionY -= temp;
	}
	if(positionY < 0)
	    positionY = 0;
    }

    public int getXBounds()
    {
	return xBounds;
    }

    public int getYBounds()
    {
	return yBounds;
    }

    public void setXBounds(int xBounds)
    {
	this.xBounds = xBounds;
    }

    public void setYBounds(int yBounds)
    {
	this.yBounds = yBounds;
    }
    
    public void setPositionX(int positionX)
    {
	this.positionX = positionX;
    }

    public void setPositionY(int positionY)
    {
	this.positionY = positionY;
    }

    public int getPositionX()
    {
	return positionX;
    }

    public int getPositionY()
    {
	return positionY;
    }
}
