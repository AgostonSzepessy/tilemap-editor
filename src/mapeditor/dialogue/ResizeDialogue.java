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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import mapeditor.MainState;

/**
 *
 * @author agoston
 */
public class ResizeDialogue extends JFrame
{
    private GridLayout layout;
    
    private JLabel leftLabel;
    private JLabel rightLabel;
    private JLabel bottomLabel;
    private JLabel topLabel;
    
    private JTextField leftText;
    private JTextField rightText;
    private JTextField bottomText;
    private JTextField topText;
    
    private JButton resizeButton;
    private JButton cancelButton;
    
    private JRadioButton upscaleRadioButton;
    private JRadioButton downscaleRadioButton;
    private ButtonGroup buttonGroup;
    
    private ButtonListener buttonListener;
    
    private MainState mainState;
    
    public ResizeDialogue(MainState mainState)
    {
	super("Resize Map");
	setPreferredSize(new Dimension());
	
	this.mainState = mainState;
	
	leftLabel = new JLabel("Left:");
	rightLabel = new JLabel("Right:");
	bottomLabel = new JLabel("Bottom:");
	topLabel = new JLabel("Top:");
	
	buttonListener = new ButtonListener();
	
	resizeButton = new JButton("Resize");
	resizeButton.setActionCommand("resize");
	resizeButton.addActionListener(buttonListener);
	
	cancelButton = new JButton("Cancel");
	cancelButton.setActionCommand("cancel");
	cancelButton.addActionListener(buttonListener);
	
	leftText = new JTextField(2);
	rightText = new JTextField(2);
	topText = new JTextField(2);
	bottomText = new JTextField(2);
	
	upscaleRadioButton = new JRadioButton("Upscale");
	downscaleRadioButton = new JRadioButton("Downscale");
	
	buttonGroup = new ButtonGroup();
	buttonGroup.add(upscaleRadioButton);
	buttonGroup.add(downscaleRadioButton);
	
	layout = new GridLayout(6, 2);
	
	setLayout(layout);
	
	add(upscaleRadioButton);
	add(downscaleRadioButton);
	add(topLabel);
	add(topText);
	add(bottomLabel);
	add(bottomText);
	add(rightLabel);
	add(rightText);
	add(leftLabel);
	add(leftText);
	add(resizeButton);
	add(cancelButton);
	
	setResizable(false);
	setSize(new Dimension(250, 200));
	setVisible(true);
	
    }
    
    private class ButtonListener implements ActionListener
    {

	@Override
	public void actionPerformed(ActionEvent e)
	{
	    if(e.getActionCommand().equals("resize"))
	    {
		int left = getValue(leftText);
		int right = getValue(rightText);
		int bottom = getValue(bottomText);
		int top = getValue(topText);
		String action = "";
		
		if(upscaleRadioButton.isSelected())
		    action = "upscale";
		if(downscaleRadioButton.isSelected())
		    action = "downscale";
		
		mainState.resizeMap(left, right, top, bottom, action);
		dispose();
	    }
	    
	    if(e.getActionCommand().equals("cancel"))
	    {
		dispose();
	    }
	}
	
	private int getValue(JTextField field)
	{
	    int value;
	    if(field.getText().isEmpty())
		value = 0;
	    else
		value = Integer.parseInt(field.getText());
	    return value;
		
	}
	
    }
}
