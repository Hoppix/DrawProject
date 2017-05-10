package MyDraw;

import java.awt.Graphics;
import java.awt.Point;

import MyDraw.Drawable;

public class OvalDrawer implements Drawable
{	
	public Point startPoint;
	public Point endPoint;
	
	public OvalDrawer()
	{
		startPoint = new Point();
		endPoint = new Point();
	}

	public void draw(Graphics g) 
	{
		
	}
	public void setStart(Point p) 
	{
		startPoint = p;
	}
	public void setEnd(Point p) 
	{
		endPoint = p;
	}
}
