package MyDraw;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseClickListener implements MouseListener, MouseMotionListener
{
    CommandHandler parentHandler;
    public int x;
    public int y;
    public RectangleDrawer rect;
    public OvalDrawer oval;
    public ScribbleDrawer scribble;
    private Point setupPoint;

    public MouseClickListener(CommandHandler handler)
    {
    	setupPoint = new Point(-1, -1);
        parentHandler = handler;
        rect = new RectangleDrawer(setupPoint, setupPoint);
        oval = new OvalDrawer(setupPoint, setupPoint);
        scribble = new ScribbleDrawer(setupPoint, setupPoint);
        //startPoint = new Point();
    }

    @Override
    public void mouseClicked(MouseEvent arg0)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent arg0)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent arg0)
    {
    	
    	
        switch (parentHandler.shape)
        {
        case "Rectangle":
            rect = new RectangleDrawer(arg0.getPoint(), arg0.getPoint());
            parentHandler.cmdQueue.add(rect);      
            
            break;
        case "Oval":
            oval = new OvalDrawer(arg0.getPoint(), arg0.getPoint());
            parentHandler.cmdQueue.add(oval);         
            
            break;
        case "Scribble":
            scribble = new ScribbleDrawer(arg0.getPoint(), arg0.getPoint());
            parentHandler.cmdQueue.add(scribble);
            
            break;

        default:
            break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0)
    {   
    	System.out.println("Released, fgColor: " + parentHandler.parentHandler.gui.getFGColor());
        switch (parentHandler.shape)
        {
        case "Rectangle":
            rect.endPoint = arg0.getPoint();
            
            parentHandler.cmdQueue.pop();
            parentHandler.cmdQueue.add(rect);
            parentHandler.execute(rect);
            
            break;
        case "Oval":
            oval.endPoint = arg0.getPoint();

            parentHandler.cmdQueue.pop();
            parentHandler.cmdQueue.add(oval); 
            parentHandler.execute(oval);
            
            break;
        case "Scribble":
            scribble.endPoint = arg0.getPoint();
            
            parentHandler.cmdQueue.pop();
            parentHandler.cmdQueue.add(scribble);  
            parentHandler.execute(scribble);
            
            break;

        default:
            break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {   
    	String backup = parentHandler.parentHandler.gui.getFGColor();
    	
        switch (parentHandler.shape)
        {
        case "Rectangle":       
        	parentHandler.parentHandler.doCommand("changeColor" + parentHandler.parentHandler.gui.getBGColor());
        	parentHandler.execute(rect);
        	parentHandler.parentHandler.doCommand("changeColor" + backup);
        	rect.endPoint = e.getPoint();
        	
            parentHandler.cmdQueue.pop();
            parentHandler.cmdQueue.add(rect);            
            parentHandler.execute(rect);
            break;
        case "Oval":
            oval.endPoint = e.getPoint();

            parentHandler.cmdQueue.pop();
            parentHandler.cmdQueue.add(oval);     
            parentHandler.execute(oval);
            break;
        case "Scribble":
            scribble.endPoint = e.getPoint();
            
            parentHandler.cmdQueue.pop();
            parentHandler.cmdQueue.add(scribble);
            parentHandler.execute(scribble);
            break;

        default:
            break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        // ignore
        
    }

}
