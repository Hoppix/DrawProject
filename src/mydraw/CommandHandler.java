package mydraw;

import Drawers.Drawable;
import Listener.MouseClickListener;

import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;

import javax.swing.Timer;

public class CommandHandler
{
    //public Queue<Drawable> cmdQueue;
    public LinkedList<Drawable> cmdQueue;
    public LinkedList<Drawable> undoneActions;
    
    public GUIHandler parentHandler;
    private Timer timer;
    
    public Graphics paintG;
    public Graphics imageG;
    
    public String shape;
    
    public Point startPoint;
    public Point endPoint; //dirty 
    
    public CommandHandler(GUIHandler handler)
    {
    	startPoint = new Point(-1, -1);
    	endPoint = new Point(-1, -1);
    	
        parentHandler = handler;
        
        shape = "Scribble";
        
        cmdQueue = new LinkedList<Drawable>();
        undoneActions = new LinkedList<Drawable>();
        
        
        imageG = handler.gui.imageG;
        paintG = handler.gui.paintG;
        
        handler.gui.drawPanel.addMouseListener(new MouseClickListener(this));
        handler.gui.drawPanel.addMouseMotionListener(new MouseClickListener(this));

    }
    
    public void execute(Drawable drawCommand)
    {
    	drawCommand.draw(imageG);
    	drawCommand.draw(paintG);
    }
}
