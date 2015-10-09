/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapeditor.tilemap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import mapeditor.MapEditorPanel;
import mapeditor.dialogue.ResizeDialogue;
import mapeditor.managers.Camera;
import mapeditor.managers.Input;

/**
 *
 * @author agoston
 */
public class TileMap
{
    private Tile[][] tiles;
    private Tile currentTile;
    private int currentTileId;
    private BufferedImage tileSet;
    
    private int[][] map;
    /**
     * How high the tile map is, in tiles.
     */
    private int mapHeight;
    /**
     * How wide the tile map is, in tiles.
     */
    private int mapWidth;    
    
    /**
     * How many tiles there are horizontally in
     * the tileset.
     */
    private int numberOfTilesAcross;
    /**
     * How many tiles there are vertically in
     * the tileset.
     */
    private int numberOfTilesHigh;
    
    /**
     * The total amount of tiles in the tileset.
     */
    private int totalNumTiles;
    /**
     * Size of the tile, including height and width, meaning that
     * all tiles must be squares.
     */
    private int tileSize;
    
    private String fileName;
    
    private Camera camera;
    
    /**
     * Used for notifying the loadFile() function so that it
     * doesn't create a new instance of the map array.
     */
    private boolean openingFile;
    
    private boolean resized;
    
    private MapObjectLayer objectLayers;
    private int numLayers;
    private ArrayList<String> layerKeys;
    private boolean objectLayerActive;
    private MapObject mapObject;
    private int currentLayer;
    
    public TileMap(int tileSize, String path, int height, int width)
    {
	this.tileSize = tileSize;
	
	mapHeight = height;
	mapWidth = width;
	
	openingFile = false;
	
	currentTileId = 0;
	
	
	
	loadFile(path);
	
    }
    
    /**
     * Loads a tile map from the path specified. The tileset image
     * must be in the same directory as the tile map file.
     * @param path The path to load the tile map file from.
     */
    public TileMap(String path)
    {
	// read in the file
	openingFile = true;
	
	File temp = new File(path);
	String directory = temp.getParent();
	
	BufferedReader br = null;
	try
	{
	    br = new BufferedReader(new FileReader(path));
	}
	catch (FileNotFoundException ex)
	{
	    ex.printStackTrace();
	}
	
	try
	{
	    if(!(directory.endsWith("/")))
		directory += "/";
	    fileName = directory + br.readLine();
	}
	catch (IOException ex)
	{
	    ex.printStackTrace();
	}
	
	try
	{
	    tileSize = Integer.parseInt(br.readLine());
	}
	catch (IOException ex)
	{
	    ex.printStackTrace();
	}
	
	try
	{
	    mapHeight = Integer.parseInt(br.readLine());
	}
	catch (IOException ex)
	{
	    ex.printStackTrace();
	}
	
	try
	{
	    mapWidth = Integer.parseInt(br.readLine());
	}
	catch (IOException ex)
	{
	    ex.printStackTrace();
	}
	
	try
	{
	    numLayers = Integer.parseInt(br.readLine());
	}
	catch (IOException ex)
	{
	    ex.printStackTrace();
	}
	
	map = new int[mapHeight][mapWidth];
	
	for(int row = 0; row < mapHeight; ++row)
	{
	    String mapInfo = "";
	    try
	    {
		mapInfo = br.readLine();
	    }
	    catch (IOException ex)
	    {
		ex.printStackTrace();
	    }
	    String[] mapTiles = mapInfo.split("\\s+");
	    for(int col = 0; col < mapWidth; ++col)
	    {
		map[row][col] = Integer.parseInt(mapTiles[col]);
	    }
	}
	
	objectLayers = new MapObjectLayer();
	layerKeys = new ArrayList<String>();
	objectLayerActive = true;
	
	if(numLayers != 0)
	{
	    for(int i = 0; i < numLayers; ++i)
	    {
		String layer = "";
		try
		{
		    layer = br.readLine();
		    layerKeys.add(layer);
		}
		catch (IOException ex)
		{
		    ex.printStackTrace();
		}
		int numObjects = 0;

		try
		{
		    numObjects = Integer.parseInt(br.readLine());
		}
		catch (IOException ex)
		{
		    ex.printStackTrace();
		}

		for(int j = 0; j < numObjects; ++j)
		{
		    String line = "";
		    try
		    {
			line = br.readLine();
		    }
		    catch (IOException ex)
		    {
			ex.printStackTrace();
		    }
		    String positions[] = line.split("\\s+");
		    objectLayers.add(layer, new MapObject(Integer.parseInt(positions[0]), Integer.parseInt(positions[1])));
		}
	    }
	}
	
	loadFile(fileName);
    }
    
    /**
     * Creates a new tile map.
     * @param path The path to the tileset image.
     */
    private void loadFile(String path)
    {
	File file = new File(path);
	fileName = file.getName();
	
	try
	{
	    tileSet = ImageIO.read(file);
	    numberOfTilesAcross =  tileSet.getWidth() / tileSize;
	    numberOfTilesHigh = tileSet.getHeight() / tileSize;
	    totalNumTiles = numberOfTilesAcross * numberOfTilesHigh;
	   
	    tiles = new Tile[numberOfTilesHigh][numberOfTilesAcross];
	    
	    for(int row = 0; row < numberOfTilesHigh; ++row)
	    {
		for(int col = 0; col < numberOfTilesAcross; ++col)
		{
		    tiles[row][col] = new Tile(row, 
			    tileSet.getSubimage(col * tileSize, row * tileSize, tileSize, tileSize), tileSize);
		}
	    }
	    
	    camera = new Camera(0, 0, (mapWidth - 1) * tileSize, (mapHeight - 1) * tileSize);
	    currentTile = new Tile(tiles[0][0]);
	    
	    if(openingFile)
		openingFile = false;
	    else
	    {
		map = new int[mapHeight][mapWidth];
		objectLayers = new MapObjectLayer();
		layerKeys = new ArrayList<String>();
		numLayers = 0;
		objectLayerActive = false;
	    }
	    
	mapObject = new MapObject(0, 0);
	}
	catch (IOException ex)
	{
	    ex.printStackTrace();
	}
    }
    
    public void update()
    {
	int mouseX = Input.getMouseX() ;
	int mouseY = Input.getMouseY();

	int mouseRotations = Input.getMouseRotations();
	
	
	
	if(Input.isKeyPressed(Input.A) && !(Input.isKeyDown(Input.CONTROL) || Input.isKeyDown(Input.SHIFT)))
	{
	    camera.setPositionX(camera.getPositionX() - tileSize * 2);
	}
	
	if(Input.isKeyPressed(Input.D)&& !(Input.isKeyDown(Input.CONTROL) || Input.isKeyDown(Input.SHIFT)))
	{
	    camera.setPositionX(camera.getPositionX() + tileSize * 2);
	}
	if(Input.isKeyPressed(Input.S)&& !(Input.isKeyDown(Input.CONTROL) || Input.isKeyDown(Input.SHIFT)))
	{
	    camera.setPositionY(camera.getPositionY() + tileSize * 2);
	}
	if(Input.isKeyPressed(Input.W)&& !(Input.isKeyDown(Input.CONTROL) || Input.isKeyDown(Input.SHIFT)))
	{
	    camera.setPositionY(camera.getPositionY() - tileSize * 2);
	}
	
	if(Input.isKeyDown(Input.CONTROL) && Input.isKeyPressed(Input.L))
	{
	    String layerName = JOptionPane.showInputDialog(null, "Layer name: ", "Create new layer", JOptionPane.PLAIN_MESSAGE);
	    if(layerName != null)
	    {
		layerKeys.add(layerName);
		numLayers++;
		objectLayers.createLayer(layerName);
		currentLayer = numLayers - 1;
	    }
	}
	
	if(Input.isKeyDown(Input.CONTROL) && Input.isKeyDown(Input.SHIFT) && Input.isKeyPressed(Input.S))
	{
	    if(!objectLayerActive)
		objectLayerActive = true;
	    else
		objectLayerActive = false;
	}
	
	camera.update(tileSize);
	
	if(!objectLayerActive)
	{
	    if(mouseRotations < 0)
	    {
		currentTileId--;
	    }
	    if(mouseRotations > 0)
		currentTileId++;

	    if(currentTileId > totalNumTiles - 1)
		currentTileId = 0;
	    if(currentTileId < 0)
		currentTileId = totalNumTiles - 1;
	    
	    // get the tile from the tile map
	    // dividing it gives you the row
	    // modulus gives you the column
	    currentTile = tiles[currentTileId / numberOfTilesAcross][currentTileId % numberOfTilesAcross];

	    currentTile.setPosition((mouseX / tileSize + camera.getPositionX() / tileSize) * tileSize, (mouseY / tileSize + camera.getPositionY() / tileSize) * tileSize);

	    if(Input.isMouseButtonDown(Input.MOUSE_LEFT))
	    {
		int row = mouseY / tileSize + camera.getPositionY() / tileSize;
		int col = mouseX / tileSize + camera.getPositionX() / tileSize;
		map[row][col] = currentTileId;
	    }
	    // used for erasing tiles
	    if(Input.isMouseButtonDown(Input.MOUSE_RIGHT))
	    {
		int row = mouseY / tileSize + camera.getPositionY() / tileSize;
		int col = mouseX / tileSize + camera.getPositionX() / tileSize;
		map[row][col] = 0;
	    }
	}
	// object layer is active
	else
	{
	    mapObject.setX(mouseX + camera.getPositionX());
	    mapObject.setY(mouseY + camera.getPositionY());
	    
	    if(mouseRotations < 0)
	    {
		currentLayer--;
	    }
	    if(mouseRotations > 0)
	    {
		currentLayer++;
	    }
	    
	    if(currentLayer >= numLayers)
	    {
		currentLayer = 0;
	    }
	    if(currentLayer < 0)
	    {
		currentLayer = numLayers - 1;
	    }
	    
	    if(Input.isMouseButtonPressed(Input.MOUSE_LEFT))
	    {
		if(!layerKeys.isEmpty())
		{
		    objectLayers.add(layerKeys.get(currentLayer), new MapObject(mapObject));
		}
	    }
	    if(Input.isMouseButtonDown(Input.MOUSE_RIGHT))
	    {
		if(!layerKeys.isEmpty())
		{
		    objectLayers.remove(layerKeys.get(currentLayer), mouseX + camera.getPositionX(), mouseY + camera.getPositionY());
		}
	    }
	}
	
    }
    
    public void draw(Graphics2D g)
    {
	int  xDrawLimit = (MapEditorPanel.WIDTH / tileSize) + camera.getPositionX() / tileSize + 1;
	int  yDrawLimit = (MapEditorPanel.HEIGHT / tileSize) + camera.getPositionY() / tileSize + 1;

	// draw tilemap
	for(int row = camera.getPositionY() / tileSize; row < yDrawLimit; ++row)
	{
	    for(int col = camera.getPositionX() / tileSize; col < xDrawLimit; ++col)
	    {
		int id = map[row][col];

		camera.draw(tiles[id / numberOfTilesAcross][id % numberOfTilesAcross].getImage(), col * tileSize, row * tileSize, g);
	    }
	}

	if(!objectLayerActive)
	{
	    // draw currentTile
	    camera.draw(currentTile.getImage(), currentTile.getX(), currentTile.getY(), g);
	}
	else
	    mapObject.draw(camera, g);
	
	if(!layerKeys.isEmpty())
	    objectLayers.draw(layerKeys.get(currentLayer), camera, g);
	
	 if(!layerKeys.isEmpty())
	{
	    String text = "Object Layer: " + layerKeys.get(currentLayer);

	    Font font = new Font(text, Font.PLAIN, 14);
	    g.setFont(font);
	    g.setColor(Color.WHITE);	    
	    g.drawString(text, MapEditorPanel.WIDTH - 150, 15);
	}
    }
    
    /**
     * Returns the number of tiles in the tilemap.
     * @return Number of tiles in tilemap.
     */
    public int getNumberOfTiles()
    {
	return numberOfTilesAcross * numberOfTilesHigh;
    }
    
    public String getFileContents()
    {
	String mapInText = fileName + "\n" + tileSize + "\n" + mapHeight + "\n" + mapWidth + "\n" + layerKeys.size() + "\n";
	
	for(int row = 0; row < mapHeight; ++row)
	{
	    for(int col = 0; col < mapWidth; ++col)
	    {
		if(col <= mapWidth - 2)
		    mapInText += String.valueOf(map[row][col]) + " ";
		else
		    mapInText += String.valueOf(map[row][col]);
	    }
	    mapInText += "\n";
	}
	
	String mapObjects = objectLayers.getContentsAsString(layerKeys, camera);
	
	String fileContents = mapInText + mapObjects;
	
	return fileContents;
    }
    
    /**
     * Resizes the tilemap.
     * @param left Amount to add/subtract from the left side.
     * @param right Amount to add/subtract from the right side.
     * @param top Amount to add/subtract from the top side.
     * @param bottom Amount to add/subtract from the bottom side.
     * @param action Subtract or add to the tilemap. To add use 
     * ResizeDialogue.UPSCALE and to subtract use ResizeDialogue.DOWNSCALE.
     */
    public void resize(int left, int right, int top, int bottom, int action)
    {
	// array to back the map up to
	int[][] temp = new int[mapHeight][mapWidth];
	
	// back up values
	for(int row = 0; row < mapHeight; ++row)
	{
	    for(int col = 0; col < mapWidth; ++col)
	    {
		temp[row][col] = map[row][col];
	    }
	}
	
	if(action == ResizeDialogue.UPSCALE)
	{
	    /*
	    in order to transfer new tiles while keeping the old ones
	    in the same place, the transfer needs to start at left - 1,
	    and it needs to end at the original length of the map.
	    */
	    int tempMapWidth = mapWidth + left;
	    int tempMapHeight = mapHeight + top;

	    mapWidth += left + right;
	    mapHeight += top + bottom;

	    map = new int[mapHeight][mapWidth];

	    for(int row = top, i = 0; row < tempMapHeight; ++row, ++i)
	    {
		for(int col = left, j = 0; col < tempMapWidth; ++col, ++j)
		{
		    map[row][col] = temp[i][j];
		}
	    }

	    camera.setXBounds((mapWidth - 1) * tileSize);
	    camera.setYBounds((mapHeight - 1) * tileSize);
	}
	if(action == ResizeDialogue.DOWNSCALE)
	{
	    /*
	    in order to reduce the size, the amount to reduce the map by needs 
	    to be subtracted from the map height & width. Then, the temp array 
	    needs to be copied to the map array starting with the offsets "left"
	    and "top". The map array starts at zero, and goes to mapWidth - 1, 
	    and mapHeight - 1. This reduces the map. Then the camera is set to the
	    proper position.
	    */
	    
	    mapWidth += -left - right;
	    mapHeight += -top - bottom;
	    
	    map = new int[mapHeight][mapWidth];
	    
	    for(int row = top, tempRow = 0; row < mapHeight; ++row, ++tempRow)
	    {
		for(int col = left, tempCol = 0; col < mapWidth; ++col, ++tempCol)
		{
		    map[tempRow][tempCol] = temp[row][col];
		}
	    }
	    camera.setXBounds((mapWidth - 1) * tileSize);
	    camera.setYBounds((mapHeight - 1) * tileSize);
	}
    }

    public boolean isResized()
    {
	return resized;
    }

    public void setResized(boolean resized)
    {
	this.resized = resized;
    }
    
}
