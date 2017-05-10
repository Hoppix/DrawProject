package MyDraw;

import java.awt.Graphics;
import java.awt.Point;

import MyDraw.Drawable;

public class RectangleDrawer implements Drawable
{	
	public Point startPoint;
	public Point endPoint;
	
	public RectangleDrawer()
	{
		startPoint = new Point();
		endPoint = new Point();
	}

	public void draw(Graphics g) 
	{
		int x = (int) startPoint.getX();
		int y = (int) startPoint.getY();
		int w = (int)(endPoint.getX() - startPoint.getX());
		int h = (int) (endPoint.getY() - startPoint.getY());
		System.out.println(x);
		System.out.println(y);
		System.out.println(w);
		System.out.println(h);
		g.drawRect(x, y, w, h);
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
