/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapeditor.tilemap;

import java.awt.image.BufferedImage;

/**
 *
 * @author agoston
 */
public class Tile
{
    public static int PASSABLE = 0;
    public static int BLOCKED = 1;
    
    private int type;
    
    private BufferedImage image;
    
    private int size;
    
    private int positionX;
    private int positionY;
    
    public Tile(int type, BufferedImage img, int size)
    {
	this.type = type;
	image = img;
	this.size = size;
    }
    
    /**
     * Copy constructor.
     * @param t The object to construct the Tile from.
     */
    public Tile(Tile t)
    {
	this(t.getType(), t.getImage(), t.getSize());
    }
    
    /**
     * Gets what kind of tile it is: blocked or unblocked.
     * @return the type of tile it is
     */
    public int getType()
    {
	return type;
    }
    
    /**
     * Gets the size of the tile. Since all tiles are meant
     * to be perfect squares, it's the same size all the way
     * around.
     * @return the size of the tile
     */
    public int getSize()
    {
	return size;
    }
    
    /**
     * Sets the position of the tile to the x and y coordinate
     * specified.
     * @param x the x position of the tile
     * @param y the y position of the tile
     */
    public void setPosition(int x, int y)
    {
	positionX = x;
	positionY = y;
    }
    
    public BufferedImage getImage()
    {
	return image;
    }
    
    public int getX()
    {
	return positionX;
    }
    
    public int getY()
    {
	return positionY;
    }
}
