/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mapeditor;

import javax.swing.JFrame;

/**
 *
 * @author agoston
 */
public class MapEditor {

    private static JFrame window;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
	window = new JFrame();
	
	
	
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.add(new MapEditorPanel());
	window.setTitle("Map Editor");
	window.pack();
	window.setVisible(true);
	
    }
}
