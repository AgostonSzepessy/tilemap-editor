/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import mapeditor.managers.Input;

/**
 *
 * @author agoston
 */
public class MapEditorPanel extends JPanel implements Runnable, KeyListener, 
	MouseListener, MouseMotionListener, MouseWheelListener
{
    /**
     * Height of the map editor.
     */
    public static final int HEIGHT = 480;
    /**
     * Width of the map editor.
     */
    public static final int WIDTH =  HEIGHT * 16 / 9;
    
    /**
     * The thread that the map editor runs in.
     */
    private Thread thread;
    /**
     * Keeps the thread running if it's true.
     */
    private boolean running;
    
    public static final int FPS = 60;
    public static long TIME_PER_FRAME = 1000 / FPS;
    
    private BufferedImage canvas;
    private Graphics2D graphics;
    
    private MainState mainState;
    
    public MapEditorPanel()
    {
	super();
	setPreferredSize(new Dimension(WIDTH, HEIGHT));
	setFocusable(true);
	requestFocus();
    }
    
    @Override
    public void addNotify()
    {
	// this says that MapEditorPanel has been added to the window,
	// so the thread can start
	super.addNotify();
	if(thread == null)
	{
	    thread = new Thread(this);
	    addKeyListener(this);
	    addMouseMotionListener(this);
	    addMouseListener(this);
	    addMouseWheelListener(this);
	    thread.start();
	}
    }

    @Override
    public void run()
    {
	init();
	
	long startTime = System.nanoTime();
	long timeToSleep;
	long timeElapsed;
	int frames = 0;
	int time = 0;
	
	while(running)
	{
	   startTime = System.nanoTime();
	   
	   update();
	   render();
	   draw();
	   
	   timeElapsed = (System.nanoTime() - startTime) / 1000000;
	   timeToSleep = TIME_PER_FRAME - timeElapsed;
	   time += TIME_PER_FRAME;
	   frames++;
	   
	   if(time > 1000)
	   {
//	       System.out.println("FPS is " + frames / (time / 1000));
	       time = 0;
	       frames = 0;
	   }
	   
	   if(timeToSleep > 0)
	   {
		try
		{
		    thread.sleep(timeToSleep);
		}
		catch (InterruptedException ex)
		{
		    ex.printStackTrace();
		}
	   }
	}
	
	try
	{
	    thread.join();
	}
	catch (InterruptedException ex)
	{
	    ex.printStackTrace();
	}
    }
    
    /**
     * Updates the map editor.
     */
    private void update()
    {
	// update main state
	mainState.update();
	
	// set number of tiles when tilemap is created
	if(mainState.isMapCreated())
	{
	    mainState.setMapCreated(false);
	}
	
	Input.updateKeyStates();
    }
    
    /**
     * Draws everything to an offscreen buffer. This is
     * to prevent flickering.
     */
    private void render()
    {
	graphics.setColor(Color.BLACK);
	graphics.fillRect(0, 0, WIDTH, HEIGHT);
	mainState.draw(graphics);
    }
    
    /**
     * Draws everything to the screen.
     */
    private void draw()
    {
	Graphics2D tempGraphics = (Graphics2D) getGraphics();
	tempGraphics.drawImage(canvas, getLocation().x, getLocation().y, null);
	tempGraphics.dispose();
    }
    
    private void init()
    {
	canvas = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	graphics = (Graphics2D) canvas.getGraphics();
	
	mainState = new MainState();
	
	running = true;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
	Input.setKey(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
	Input.setKey(e.getKeyCode(), false);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
	Input.setButton(e.getButton(), true);
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
	Input.setButton(e.getButton(), false);
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
	Input.setMousePosition(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
	Input.setMousePosition(e.getX(), e.getY());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
	Input.setMouseRotation(e.getWheelRotation());
    }
}
