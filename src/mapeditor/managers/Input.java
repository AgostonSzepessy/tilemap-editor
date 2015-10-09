/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapeditor.managers;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author agoston
 */
public class Input
{
   
    private static int mouseX;
    private static int mouseY;
    private static int mouseRotations;

    // The keys that can be used
    public static final int CONTROL = 0;
    /**
     * The shift key.
     */
    public static final int SHIFT = 1;
    /**
     * Used for saving a file.
     */
    public static final int S = 2;
    /**
     * Used for opening a new map.
     */
    public static final int O = 3;
    /**
     * Used for creating a new map.
     */
    public static final int N = 4;
    /**
     * Used for resizing the map.
     */
    public static final int R = 5;
    /**
     * 
     */
    public static final int ESCAPE = 6;
    
    /**
     * Moves the map left.
     */
    public static final int A = 7;
    
    /**
     * Moves the map right.
     */
    public static final int D = 8;
    
    /**
     * Moves the map up.
     */
    public static final int W = 9;
    
    public static final int L = 10;
    /**
     * The number of keys used.
     */
    private static final int NUM_KEYS = 11;
    
    // The mouse keys that can be used
    /**
     * Left mouse button.
     */
    public static final int MOUSE_LEFT = 0;
    /**
     * Center mouse button.
     */
    public static final int MOUSE_CENTER = 1;
    /**
     * Right mouse button.
     */
    public static final int MOUSE_RIGHT = 2;
    private static final int NUM_MOUSE_KEYS = 3;
    
    /**
     * The current state of the keys: pressed or
     * not pressed.
     */
    private static boolean[] currentKeys = new boolean[NUM_KEYS];
    
      
    /**
     * The previous state of the keys: pressed or not
     * pressed.
     */
    private static boolean[] previousKeys = new boolean[NUM_KEYS];
    private static boolean[] currentMouseKeys = new boolean[NUM_MOUSE_KEYS];
    private static boolean previousMouseKeys[] = new boolean[NUM_MOUSE_KEYS];
    
    public static void updateKeyStates()
    {
	for(int i = 0; i < NUM_KEYS; ++i)
	{
	    previousKeys[i] = currentKeys[i];
	}
	for(int i = 0; i < NUM_MOUSE_KEYS; ++i)
	{
	    previousMouseKeys[i] = currentMouseKeys[i];
	}
	
	mouseRotations = 0;
    }
    
    public static void setKey(int keyCode, boolean pressed)
    {
	if(keyCode == KeyEvent.VK_CONTROL)
	    currentKeys[CONTROL] = pressed;
	if(keyCode == KeyEvent.VK_SHIFT)
	    currentKeys[SHIFT] = pressed;
	if(keyCode == KeyEvent.VK_S)
	    currentKeys[S] = pressed;
	if(keyCode == KeyEvent.VK_O)
	    currentKeys[O] = pressed;
	if(keyCode == KeyEvent.VK_R)
	    currentKeys[R] = pressed;
	if(keyCode == KeyEvent.VK_N)
	    currentKeys[N] = pressed;
	if(keyCode == KeyEvent.VK_ESCAPE)
	    currentKeys[ESCAPE] = pressed;
	if(keyCode == KeyEvent.VK_A)
	    currentKeys[A] = pressed;
	if(keyCode == KeyEvent.VK_D)
	    currentKeys[D] = pressed;
	if(keyCode == KeyEvent.VK_W)
	    currentKeys[W] = pressed;
	if(keyCode == KeyEvent.VK_L)
	    currentKeys[L] = pressed;
    }
    
    public static void setButton(int buttonCode, boolean pressed)
    {
	if(buttonCode == MouseEvent.BUTTON1)
	    currentMouseKeys[MOUSE_LEFT] = pressed;
	if(buttonCode == MouseEvent.BUTTON2)
	    currentMouseKeys[MOUSE_CENTER] = pressed;
	if(buttonCode == MouseEvent.BUTTON3)
	    currentMouseKeys[MOUSE_RIGHT] = pressed;
	

    }
    
    public static void setMousePosition(int mouseXPosition, int mouseYPosition)
    {
	mouseX = mouseXPosition;
	mouseY = mouseYPosition;
    }
    
    public static int getMouseX()
    {
	return mouseX;
    }
    
    public static int getMouseY()
    {
	return mouseY;
    }
    
    public static void setMouseRotation(int rotations)
    {
	mouseRotations = rotations;
    }
    
    public static int getMouseRotations()
    {
	return mouseRotations;
    }
    
    /**
     * Checks if a key was pressed. Only returns true once for a key,
     * even if it is held down.
     * @param key Key to check
     * @return true if the current state of the key is down
     * and the previous state is up.
     */
    public static boolean isKeyPressed(int key)
    {
	return currentKeys[key] && !previousKeys[key];
    }
    
    /**
     * Checks if a key is held down.
     * @param key The key to check
     * @return True while the key is held down.
     */
    public static boolean isKeyDown(int key)
    {
	return currentKeys[key];
    }
    
    /**
     * Checks if a mouse button is held down.
     * @param button The button to check
     * @return True while the button is held down.
     */
    public static boolean isMouseButtonDown(int button)
    {
	return currentMouseKeys[button];
    }
    
    /**
     * Checks if a mouse button was pressed. Only returns true once for a button,
     * even if it is held down.
     * @param button The button to check
     * @return True if the current state of the button is down
     * and the previous state is up.
     */
    public static boolean isMouseButtonPressed(int button)
    {
	return currentMouseKeys[button] && !previousMouseKeys[button];
    }
        
}
