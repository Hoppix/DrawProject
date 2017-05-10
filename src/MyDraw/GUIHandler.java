package MyDraw;


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

import javax.swing.*;




public class GUIHandler extends MouseAdapter implements MouseMotionListener
{
    //TODO command queue
    private JFileChooser chooser;
    private JFrame frame;
    public DrawGUIs gui;
    private String savePath;
    public Drawable drawer;
    public Graphics2D imageG;
    public Graphics g;
    public Queue<String> cmdQueue;
    private Timer timer;
   
    public GUIHandler(DrawGUIs getgui)
    {
        gui = getgui;
        frame = gui.getDrawFrame();
        chooser = new JFileChooser();
        imageG = gui.getImageG();
        drawer = new RectangleDrawer(); //default style
        cmdQueue = new LinkedList<String>();

        chooser.addChoosableFileFilter(new BMPFileFilter());

        // Define action listener adapters that connect the  buttons to the app
        gui.clear.addActionListener(new DrawActionListener("clear", this));
        gui.quit.addActionListener(new DrawActionListener("quit", this));
        gui.auto.addActionListener(new DrawActionListener("auto", this));
        gui.save.addActionListener(new DrawActionListener("save", this));

        gui.shape_chooser.addItemListener(new ShapeManager(gui));
        //gui.getDrawPanel().addMouseListener(this);
		//gui.getDrawPanel().addMouseMotionListener(this);
        gui.shape_chooser.addItemListener(new ShapeItemListener(this));
        gui.color_chooser.addItemListener(new ColorItemListener(this));
        gui.colorBG_chooser.addItemListener(new ColorBGItemListener(this)); 

        gui.getDrawFrame().addWindowStateListener(new ResponsiveHandler(gui));
        
        timer = new Timer(10, new TimerListener(this));
        timer.start();
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
        	switch (command)
        	{
        		case "Rectangle": 
        		{       			
        			drawer = new RectangleDrawer();
        			break;
        		}
        		case "Oval": 
        		{
        			drawer = new OvalDrawer();
        			break;
        		}
			
        		case "Scribble": 
        		{      			
        			break;
        		}
        	
        		default: 
        		{
        			System.out.println("not yet implemented");
        			break;
        		}
        	}
        }
        else if(command.contains("changeColor"))
        {   
        	command = command.replace("changeColor", "");
        	ColorHashMap map = new ColorHashMap();
        	gui.color = map.StringToColor(command);         
        }
        else if(command.contains("changeBGColor"))
        {
        	System.out.println(command);
        	command = command.replace("changeBGColor", "");
            ColorHashMap map = new ColorHashMap();
        	gui.colorBG = map.StringToColor(command); 
            gui.clear();
        }
    }
    
    public void mousePressed(MouseEvent e)
	{
		//set starting point
    	System.out.println("mousePressed");
    	drawer.setStart(e.getPoint());
    	
	}
    
    public void mouseDragged(MouseEvent e)
	{
    	drawer.setEnd(e.getPoint());
        //expand object
	}
    
    public void mouseReleased(MouseEvent e)
    {
    	System.out.println("mouseReleased");
    	drawer.setEnd(e.getPoint());
    	g = gui.getDrawPanel().getGraphics();
    	drawer.draw(g);
    	drawer.draw(imageG);
    	//draw final object
    }
}
