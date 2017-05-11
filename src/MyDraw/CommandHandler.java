package MyDraw;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JPanel;
import javax.swing.Timer;

public class CommandHandler
{
    //public Queue<Drawable> cmdQueue;
    public LinkedList<Drawable> cmdQueue;
    
    public GUIHandler parentHandler;
    private Timer timer;
    
    public Graphics paintG;
    public Graphics imageG;
    
    public String shape;
    
    public CommandHandler(GUIHandler handler)
    {
        parentHandler = handler;
        
        shape = "Scribble";
        
        cmdQueue = new LinkedList<Drawable>();
        
        imageG = handler.gui.getImageG();
        paintG = handler.gui.getPaintG();  
        
        handler.gui.drawPanel.addMouseListener(new MouseClickListener(this));
        handler.gui.drawPanel.addMouseMotionListener(new MouseClickListener(this));
        
        //timer = new Timer(10, new TimerListener(this));
        //timer.start();
    }
    
    public void execute(Drawable drawCommand)
    {
    	drawCommand.draw(imageG);
    	drawCommand.draw(paintG);
    }
}
