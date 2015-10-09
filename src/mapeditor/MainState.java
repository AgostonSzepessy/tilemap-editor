/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapeditor;

import mapeditor.dialogue.NewFileDialogue;
import mapeditor.dialogue.ResizeDialogue;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.JFileChooser;
import mapeditor.managers.Input;
import mapeditor.tilemap.TileMap;

/**
 *
 * @author agoston
 */
public class MainState
{
    /**
     * The name of the image that the tileset
     * is in.
     */
    private String imageFile;
    
    NewFileDialogue newFileDialogue;
    ResizeDialogue resizeDialogue;
    
    private boolean creatingNewFile;
    private int height;
    private int width;
    private int tileSize;
    
    private TileMap tileMap;
    
    /**
     * Used from the outside to get information about the map.
     */
    private boolean mapCreated;
    /**
     * Used for inside stuff.
     */
    private boolean mapExists;
    private boolean mapSaved;
    
    private RandomAccessFile randomFile;
    private File fileToWriteTo;
    
    public void MainState()
    {
	creatingNewFile = false;
	mapCreated = false;
	mapSaved = false;
	randomFile = null;
	fileToWriteTo = null;
    }
    
    public void update()
    {
	if(Input.isKeyDown(Input.CONTROL) && Input.isKeyPressed(Input.N))
	{
	    newFileDialogue = new NewFileDialogue(this);
	}
	
	if(creatingNewFile)
	{
	    tileMap = new TileMap(tileSize, imageFile, height, width);
	    mapCreated = true;
	    mapExists = true;
	    creatingNewFile = false;
	}
	
	if(Input.isKeyDown(Input.CONTROL) && Input.isKeyPressed(Input.S) && !mapSaved && !Input.isKeyDown(Input.SHIFT))
	{
            System.out.println("saving");
	    saveAs();
	}
	else if(Input.isKeyDown(Input.CONTROL) && Input.isKeyPressed(Input.S) && mapSaved && !Input.isKeyDown(Input.SHIFT))
	{
	    save();
	}
	
	if(Input.isKeyDown(Input.CONTROL) && Input.isKeyPressed(Input.O))
	{
	    openFile();
	    mapSaved = true;
	    mapCreated = true;
	    mapExists = true;
	    creatingNewFile = false;
	}
	
	if(Input.isKeyDown(Input.CONTROL) && Input.isKeyPressed(Input.R) && mapExists)
	{
	    resizeDialogue = new ResizeDialogue(this);
	}
	
	if(tileMap != null)
	{
	    tileMap.update();
	}
    }
    
    public void createNewFile(String imageFile, int width, int height, int tileSize)
    {
	creatingNewFile = true;
	this.height = height;
	this.width = width;
	this.tileSize = tileSize;
	this.imageFile = imageFile;
    }
    
    public void resizeMap(int left, int right, int top, int bottom, int action)
    {
	tileMap.resize(left, right, top, bottom, action);
    }
    
    public void draw(Graphics2D g)
    {
	if(tileMap != null)
	{
	    tileMap.draw(g);
	}
    }
    
    public boolean isMapCreated()
    {
	return mapCreated;
    }
    
    public void setMapCreated(boolean b)
    {
	mapCreated = b;
    }
    
    private void saveAs()
    {
	JFileChooser fileChooser = new JFileChooser();
	int returnValue = fileChooser.showSaveDialog(null);
	
	if(returnValue == JFileChooser.APPROVE_OPTION)
	{
	    fileToWriteTo = fileChooser.getSelectedFile();	    
	    save();  
	}
    }
    
    private void save()
    {
	// FIXME: segfault caused if 's' is pressed when editor is started and
        // new file is created
        if(fileToWriteTo == null)
            return;
        fileToWriteTo.delete();
	
	try
	{
	    randomFile = new RandomAccessFile(fileToWriteTo, "rw");
	    randomFile.write(tileMap.getFileContents().getBytes());
	    mapSaved = true;
	    randomFile.close();
	}
	catch (IOException ex)
	{
	    ex.printStackTrace();
	}
    }
    
    private void openFile()
    {
	JFileChooser fileChooser = new JFileChooser();
	int returnValue = fileChooser.showOpenDialog(null);
	
	byte[] buffer = null;
	
	if(returnValue == JFileChooser.APPROVE_OPTION)
	{
	    fileToWriteTo = fileChooser.getSelectedFile();
	    try
	    {
		tileMap = new TileMap(fileToWriteTo.getCanonicalPath());
	    }
	    catch (IOException ex)
	    {
		ex.printStackTrace();
	    }
	}
    }
}
