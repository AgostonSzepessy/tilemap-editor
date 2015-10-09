/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapeditor.dialogue;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import mapeditor.MainState;

/**
 *
 * @author agoston
 */
public class NewFileDialogue extends JFrame
{
    private JLabel heightLabel;
    private JLabel widthLabel;
    private JLabel titleLabel;
    private JLabel fileLabel;
    private JLabel tileSizeLabel;
    private JLabel errorMessage;
    
    private JTextField heightTextBox;
    private JTextField widthTextBox;
    private JTextField titleTextBox;
    private JTextField fileTextBox;
    private JTextField tileSizeTextBox;
    
    private JButton createButton;
    private JButton browseButton;
    
    private ButtonListener buttonListener;
    
    private GridLayout layout;
    private MainState mainState;
    
    private String tileSetPath;
    
    public NewFileDialogue(MainState mainState)
    {
	super("New File");
	layout = new GridLayout(5, 2);
	this.mainState = mainState;
	
	tileSetPath = null;
	
	heightLabel = new JLabel("Height");
	widthLabel = new JLabel("Width");
	titleLabel = new JLabel("Map Name");
	fileLabel = new JLabel("Tileset file");
	tileSizeLabel = new JLabel("Tile size");
	errorMessage = new JLabel("");
	
	heightTextBox = new JTextField(3);
	widthTextBox = new JTextField(3);
	titleTextBox = new JTextField(12);
	fileTextBox = new JTextField(12);
	tileSizeTextBox = new JTextField(3);
	
	buttonListener = new ButtonListener();
	
	createButton = new JButton("Create");
	createButton.addActionListener(buttonListener);
	createButton.setActionCommand("createClicked");
	
	browseButton = new JButton("Browse");
	browseButton.addActionListener(buttonListener);
	browseButton.setActionCommand("browseClicked");
	
	setLayout(layout);
	
	add(heightLabel);
	add(heightTextBox);
	add(widthLabel);
	add(widthTextBox);
	add(tileSizeLabel);
	add(tileSizeTextBox);
	add(browseButton);
	add(createButton);
	add(errorMessage);
	
	
	//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setResizable(false);
	setSize(new Dimension(250, 200));
	setVisible(true);
	
    }
    
    private class ButtonListener implements ActionListener
    {

	@Override
	public void actionPerformed(ActionEvent e)
	{
	    if("createClicked".equals(e.getActionCommand()))
	    {
		String error = "";
		// if the user hasn't selected a tileset, don't let them create a tilemap
		if(tileSetPath == null)
		{
		    error += "Select a tileset.";
		    errorMessage.setText(error);
		}
		if(widthTextBox.getText().isEmpty() || heightTextBox.getText().isEmpty() || tileSizeTextBox.getText().isEmpty())
		{
		    error += "Fill out all fields.";
		    errorMessage.setText(error);
		}
		else
		{
		    System.out.println("new file");
		    mainState.createNewFile(
			    tileSetPath,
			    Integer.parseInt(widthTextBox.getText()), 
			    Integer.parseInt(heightTextBox.getText()),
			    Integer.parseInt(tileSizeTextBox.getText())
			    );
		    // close the jframe
		    dispose();
		}
	    }
	    if("browseClicked".equals(e.getActionCommand()))
	    {
		// open a file chooser and then get the path of the 
		// file that the user chooses
		JFileChooser chooser = new JFileChooser();
		int returnValue = chooser.showOpenDialog(NewFileDialogue.this);
		
		if(returnValue == JFileChooser.APPROVE_OPTION)
		{
		    File file = chooser.getSelectedFile();
		    		    
		    try
		    {
			tileSetPath = file.getCanonicalPath();
		    }
		    catch (IOException ex)
		    {
			ex.printStackTrace();
		    }
		    
		    
		}
	    }
	}
	
    }
}
