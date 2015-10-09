/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapeditor.tilemap;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import mapeditor.managers.Camera;

/**
 *
 * @author agoston
 */
public class MapObjectLayer
{
    private HashMap<String, ArrayList<MapObject>> map;
    
    public MapObjectLayer()
    {
	map = new HashMap<String, ArrayList<MapObject>>();
    }
    
    public void add(String key, MapObject obj)
    {
	if(map.get(key) == null)
	{
	    map.put(key, new ArrayList<MapObject>());
	}
	map.get(key).add(obj);
    }
    
    public MapObject get(String key, int location)
    {
	return map.get(key).get(location);
    }

    public void remove(String key, int x, int y)
    {
	for(int i = 0; i < map.get(key).size(); ++i)
	{
	    if(map.get(key).get(i).inBounds(x, y))
	    {
		map.get(key).remove(i);
		return;
	    }
	}
    }
    
    public void createLayer(String key)
    {
	if(map.get(key) == null)
	{
	    map.put(key, new ArrayList<MapObject>());
	}
    }
    
    public void draw(String key, Camera c, Graphics2D g)
    {
	for(int i = 0; i < map.get(key).size(); ++i)
	{
	    map.get(key).get(i).draw(c, g);
	}
    }
    
    public String getContentsAsString(ArrayList<String> keys, Camera camera)
    {
	String text = "";
	
	// loop through the hashmap using the keys
	for(int i = 0; i < keys.size(); ++i)
	{
	    String layerName = keys.get(i);
	    // add layer name and a new line
	    text += layerName + "\n" + map.get(layerName).size() + "\n";
	    // get hashmap contents for a key
	    for(int j = 0; j < map.get(layerName).size(); ++j)
	    {
		int x = map.get(layerName).get(j).getX() + camera.getPositionX();
		int y = map.get(layerName).get(j).getY() + camera.getPositionY();
		
		text += x + " " + y + "\n";
	    }
	}
	
	return text;
    }
}
