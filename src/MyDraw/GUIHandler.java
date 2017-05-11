package MyDraw;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.*;




public class GUIHandler
{
    //TODO Draw commands in Command queue aufnehmen
    private JFileChooser chooser;
    private JFrame frame;
    public DrawGUIs gui;
    private String savePath;
    public Drawable drawer;
    public Graphics imageG;
    public Graphics paintG;
    public Queue<String> cmdQueue;
    public Stack<String> doneActions; //TODO: durchgef�hrte commands hier speichern (1.6)
    private Timer timer;
    
    public CommandHandler executioner;
    
   
    public GUIHandler(DrawGUIs getgui)
    {
        gui = getgui;

        chooser = new JFileChooser();
        cmdQueue = new LinkedList<String>();
        doneActions = new Stack<String>();        
        
        imageG = gui.getImageG();
        paintG = gui.getPaintG();
        frame = gui.getDrawFrame();
        
        executioner = new CommandHandler(this);

        chooser.addChoosableFileFilter(new BMPFileFilter());

        // Define action listener adapters that connect the  buttons to the app
        gui.clear.addActionListener(new DrawActionListener("clear", this));
        gui.quit.addActionListener(new DrawActionListener("quit", this));
        gui.auto.addActionListener(new DrawActionListener("auto", this));
        gui.save.addActionListener(new DrawActionListener("save", this));

        
		
        gui.shape_chooser.addItemListener(new ShapeItemListener(this));
        gui.color_chooser.addItemListener(new ColorItemListener(this));
        gui.colorBG_chooser.addItemListener(new ColorBGItemListener(this)); 

        gui.getDrawFrame().addWindowStateListener(new ResponsiveHandler(gui));
  
    }
    

    
    public void doCommand(String command)
    {
    	if (command == null)
    	{
    		return;
    	}
        if (command.equals("clear"))
        { 
           gui.clear();

        }
        else if (command.equals("quit"))
        {
            frame.dispose();
            System.exit(0);
        }
        else if(command.equals("auto"))
        {   
            doneActions.add(command);
            gui.autoDraw();
        }
        else if(command.equals("save"))
        {
            chooser.setVisible(true);

            int retrieve = chooser.showSaveDialog(gui.getDrawFrame());

            if(retrieve == chooser.APPROVE_OPTION)
            {
                String extension2 = chooser.getFileFilter().getDescription();

                if(extension2.equals("*bmp,*.BMP"))
                {
                    /**
                     *  ignore
                     */
                }
            }
            try
            {
                savePath = chooser.getSelectedFile().getAbsolutePath();

                try
                {
                    gui.writeImage(gui.getDrawing(), savePath + ".bmp");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            catch (NullPointerException e)
            {
                JOptionPane.showMessageDialog(frame, "Speichern abgebrochen");
                e.printStackTrace();
            }

        }   
        else if(command.contains("changeShape"))
        {
        	command = command.replace("changeShape", "");
        	System.out.println(command);
        	executioner.shape = command;
        	
        }
        else if(command.contains("changeColor"))
        {   
        	command = command.replace("changeColor", "");
        	ColorHashMap map = new ColorHashMap();
        	gui.color = map.StringToColor(command);         
        }
        else if(command.contains("changeBGColor"))
        {
        	command = command.replace("changeBGColor", "");
            ColorHashMap map = new ColorHashMap();
        	gui.colorBG = map.StringToColor(command); 
            gui.clear();
        }     
    }
}
