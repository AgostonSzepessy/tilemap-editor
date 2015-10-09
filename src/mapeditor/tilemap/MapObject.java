/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapeditor.tilemap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import mapeditor.managers.Camera;

/**
 *
 * @author agoston
 */
public class MapObject
{
    private int x;
    private int y;
    
    private BufferedImage img;
    int height;
    int width;
    
    public MapObject()
    {
	this(0, 0);
    }

    public MapObject(int x, int y)
    {
	this.x = x;
	this.y = y;
	
	try
	{
	    img = ImageIO.read(getClass().getResourceAsStream("/object.png"));
	    height = img.getHeight();
	    width = img.getWidth();
	}
	catch (IOException ex)
	{
	    ex.printStackTrace();
	}
    }
    
    public MapObject(MapObject obj)
    {
	this.x = obj.getX();
	this.y = obj.getY();
	
	try
	{
	    img = ImageIO.read(getClass().getResourceAsStream("/object.png"));
	    height = img.getHeight();
	    width = img.getWidth();
	}
	catch (IOException ex)
	{
	    ex.printStackTrace();
	}
    }

    public int getX()
    {
	return x;
    }

    public int getY()
    {
	return y;
    }

    public void setX(int x)
    {
	this.x = x;
    }

    public void setY(int y)
    {
	this.y = y;
    }

    public void draw(Camera c, Graphics2D g)
    {
	c.draw(img, x, y, g);
    }
    
    public boolean inBounds(int xPosition, int yPosition)
    {
	if(xPosition < x || xPosition > x + width || yPosition < y || yPosition > y + height)
	{
	    return false;
	}
	return true;
    }
}
