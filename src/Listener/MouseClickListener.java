package Listener;

import Drawers.*;
import MyDraw.*;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseClickListener implements MouseListener, MouseMotionListener
{
    CommandHandler parentHandler;
    GUIHandler guiHandler;
    DrawGUIs gui;
    
    public int x;
    public int y;
    
    public RectangleDrawer rect;
    public OvalDrawer oval;
    public ScribbleDrawer scribble;
    public FillOvalDrawer ovalFilled;
    public FillRectangleDrawer rectFilled;
    public LineDrawer line;
    public TriangleDrawer triangle;
    
    private Point setupPoint;

    public MouseClickListener(CommandHandler handler)
    {
    	setupPoint = new Point(-1, -1);
    	
        parentHandler = handler;
        guiHandler = handler.parentHandler;
        gui = guiHandler.gui;
        
        rect = new RectangleDrawer(setupPoint, setupPoint);
        oval = new OvalDrawer(setupPoint, setupPoint);
        scribble = new ScribbleDrawer(setupPoint, setupPoint);
        ovalFilled = new FillOvalDrawer(setupPoint, setupPoint);
        rectFilled = new FillRectangleDrawer(setupPoint, setupPoint);
        line = new LineDrawer(setupPoint, setupPoint);
        triangle = new TriangleDrawer(setupPoint, setupPoint);
        //startPoint = new Point();
    }

    @Override
    public void mouseClicked(MouseEvent arg0)
    {
        //ignore

    }

    @Override
    public void mouseEntered(MouseEvent arg0)
    {
    	//ignore

    }

    @Override
    public void mouseExited(MouseEvent arg0)
    {
    	//ignore

    }

    @Override
    public void mousePressed(MouseEvent arg0)
    {    	
    	parentHandler.startPoint = arg0.getPoint();    	
    	
        switch (parentHandler.shape)
        {
        case "Rectangle":
            rect = new RectangleDrawer(arg0.getPoint(), arg0.getPoint());
            
            break;
        case "Oval":
            oval = new OvalDrawer(arg0.getPoint(), arg0.getPoint());   
            
            break;
        case "Scribble":
        	// ignore
        	//parentHandler.startPoint = arg0.getPoint();
            
            break;
        case "FillOval":
        	ovalFilled = new FillOvalDrawer(arg0.getPoint(), arg0.getPoint());
        	break;
        case "FillRectangle":
        	rectFilled = new FillRectangleDrawer(arg0.getPoint(), arg0.getPoint());
        	break;
        case "Line":
        	line = new LineDrawer(arg0.getPoint(), arg0.getPoint());
        	break;
        case "Triangle":
        	triangle = new TriangleDrawer(arg0.getPoint(), arg0.getPoint());
        default:
            break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0)
    {   
    	gui.paintG.setPaintMode();
    	gui.imageG.setPaintMode();
    	gui.imageG.setColor(gui.color);
    	gui.paintG.setColor(gui.color);
        switch (parentHandler.shape)
        {
        case "Rectangle":
            rect.endPoint = arg0.getPoint();
                     
            parentHandler.cmdQueue.add(rect);
            parentHandler.execute(rect);           
            break;
            
        case "Oval":
            oval.endPoint = arg0.getPoint();

            parentHandler.cmdQueue.add(oval); 
            parentHandler.execute(oval);         
            break;
            
        case "Scribble":
            //done in Dragged
        	//scribble.endPoint = arg0.getPoint();
            
            //parentHandler.cmdQueue.add(scribble);  
            //parentHandler.execute(scribble);        
            break;
            
        case "FillOval":
        	ovalFilled.endPoint = arg0.getPoint();
            
            parentHandler.cmdQueue.add(ovalFilled);  
            parentHandler.execute(ovalFilled); 
            break;
            
        case "FillRectangle":
        	rectFilled.endPoint = arg0.getPoint();
            
            parentHandler.cmdQueue.add(rectFilled);  
            parentHandler.execute(rectFilled); 
            break;
        
        case "Line":
        	line.endPoint = arg0.getPoint();
        	
        	parentHandler.cmdQueue.add(line);
        	parentHandler.execute(line);
        	break;
        	
        case "Triangle":
        	triangle.endPoint = arg0.getPoint();
        	
        	parentHandler.cmdQueue.add(triangle);
        	parentHandler.execute(triangle);
        default:
            break;
        }
        parentHandler.startPoint = setupPoint;
        parentHandler.endPoint = setupPoint;
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {      	
    	//set XOR Mode
    	gui.paintG.setXORMode(gui.color);
		gui.paintG.setColor(gui.colorBG);
		gui.imageG.setXORMode(gui.color);
		gui.imageG.setColor(gui.colorBG);
    	
        switch (parentHandler.shape)
        {
        case "Rectangle": 
        	rect.startPoint = parentHandler.startPoint;
        	
        	if((parentHandler.endPoint.getX() != -1 && parentHandler.endPoint.getY() != -1))
        	{
        		parentHandler.execute(rect);
        	}

        	parentHandler.endPoint = e.getPoint();

        	rect.endPoint = e.getPoint();        	
                      
            parentHandler.execute(rect);
            break;
            
        case "Oval":
        	oval.startPoint = parentHandler.startPoint;
        	
        	if((parentHandler.endPoint.getX() != -1 && parentHandler.endPoint.getY() != -1))
        	{
        		parentHandler.execute(oval);
        	}

        	parentHandler.endPoint = e.getPoint();

        	oval.endPoint = e.getPoint();        	
                      
            parentHandler.execute(oval);
            break;
            
        case "Scribble":
        	//set PaintMode
        	gui.paintG.setPaintMode();
        	gui.imageG.setPaintMode();
        	gui.imageG.setColor(gui.color);
        	gui.paintG.setColor(gui.color);
        	
            scribble.startPoint = parentHandler.startPoint;
            scribble.endPoint = e.getPoint();
            parentHandler.startPoint = e.getPoint();

            parentHandler.cmdQueue.add(scribble);
            parentHandler.execute(scribble);
            break;
            
        case "FillOval":
        	ovalFilled.startPoint = parentHandler.startPoint;
        	
        	if((parentHandler.endPoint.getX() != -1 && parentHandler.endPoint.getY() != -1))
        	{
        		parentHandler.execute(ovalFilled);
        	}

        	parentHandler.endPoint = e.getPoint();

        	ovalFilled.endPoint = e.getPoint();        	
                      
            parentHandler.execute(ovalFilled);
            break;
        
        case "FillRectangle":
        	rectFilled.startPoint = parentHandler.startPoint;
        	
        	if((parentHandler.endPoint.getX() != -1 && parentHandler.endPoint.getY() != -1))
        	{
        		parentHandler.execute(rectFilled);
        	}

        	parentHandler.endPoint = e.getPoint();

        	rectFilled.endPoint = e.getPoint();        	
                      
            parentHandler.execute(rectFilled);
            break;
            
        case "Line":
        	line.startPoint = parentHandler.startPoint;
        	
        	if((parentHandler.endPoint.getX() != -1 && parentHandler.endPoint.getY() != -1))
        	{
        		parentHandler.execute(line);
        	}

        	parentHandler.endPoint = e.getPoint();

        	line.endPoint = e.getPoint();        	
                      
            parentHandler.execute(line);
            break;
        	
        case "Triangle":
        	triangle.startPoint = parentHandler.startPoint;
        	
        	if((parentHandler.endPoint.getX() != -1 && parentHandler.endPoint.getY() != -1))
        	{
        		parentHandler.execute(triangle);
        	}

        	parentHandler.endPoint = e.getPoint();

        	triangle.endPoint = e.getPoint();        	
                      
            parentHandler.execute(triangle);
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
