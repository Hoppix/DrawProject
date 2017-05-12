package MyDraw;

import java.awt.Color;
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
        	
        	parentHandler.startPoint = arg0.getPoint();
            //scribble = new ScribbleDrawer(arg0.getPoint(), arg0.getPoint());
            
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
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {   
    	gui.imageG.setColor(gui.colorBG);
    	gui.paintG.setColor(gui.colorBG);
    	
        switch (parentHandler.shape)
        {
        case "Rectangle": 
        	rect.startPoint = parentHandler.startPoint;
        	
        	parentHandler.execute(rect);
        	gui.imageG.setColor(gui.color);
        	gui.paintG.setColor(gui.color);

        	rect.endPoint = e.getPoint();        	
                      
            parentHandler.execute(rect);
            break;
            
        case "Oval":
        	oval.startPoint = parentHandler.startPoint;
        	
        	parentHandler.execute(oval);
        	gui.imageG.setColor(gui.color);
        	gui.paintG.setColor(gui.color);
        	
            oval.endPoint = e.getPoint();
   
            parentHandler.execute(oval);
            break;
            
        case "Scribble":
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
        	
        	parentHandler.execute(ovalFilled);
        	gui.imageG.setColor(gui.color);
        	gui.paintG.setColor(gui.color);
        	
            ovalFilled.endPoint = e.getPoint();
  
            parentHandler.execute(ovalFilled);
            break;
        
        case "FillRectangle":
        	rectFilled.startPoint = parentHandler.startPoint;
        	
        	parentHandler.execute(rectFilled);
        	gui.imageG.setColor(gui.color);
        	gui.paintG.setColor(gui.color);
        	
            rectFilled.endPoint = e.getPoint();
  
            parentHandler.execute(rectFilled);
            break;
            
        case "Line":
        	line.startPoint = parentHandler.startPoint;
        	
        	parentHandler.execute(line);
        	
        	gui.imageG.setColor(gui.color);
        	gui.paintG.setColor(gui.color);
        	
        	line.endPoint = e.getPoint();
        	
        	parentHandler.execute(line);
        	break;
        	
        case "Triangle":
        	triangle.startPoint = parentHandler.startPoint;
        	
        	parentHandler.execute(triangle);
        	
        	gui.imageG.setColor(gui.color);
        	gui.paintG.setColor(gui.color);
        	
        	triangle.endPoint = e.getPoint();
        	
        	parentHandler.execute(triangle);
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
