package MyDraw;

import java.awt.Graphics;
import java.awt.Point;

import MyDraw.Drawable;

public class OvalDrawer extends VisualObject implements Drawable
{	
	public Point startPoint;
	public Point endPoint;
	
	public OvalDrawer(Point start, Point end)
    {
        startPoint = start;
        endPoint = end;
	}

	public void draw(Graphics g) 
	{
	    if(startPoint.x < 0 && startPoint.y < 0)
        {
            return;
        }
        
	    
	    int x = (int) startPoint.getX();
        int y = (int) startPoint.getY();
        int w = (int)(endPoint.getX() - startPoint.getX());
        int h = (int) (endPoint.getY() - startPoint.getY());
        
        if (w < 0)
        {
            w = Math.abs(w);
            x = (int) endPoint.getX();
        }
        if (h < 0)
        {
            h = Math.abs(h);
            y = (int) endPoint.getY();
        }
        
        g.drawOval(x, y, w, h);
	}
}
