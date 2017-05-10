package MyDraw;

import java.awt.Graphics;

import MyDraw.Drawable;

public class rektangle  extends visualObject implements Drawable
{	
	public rektangle()
	{
		
	}
	@Override
	public void draw(Graphics g) 
	{
		int x = upperLeft.x;
        int y = upperLeft.y;
        int w = Math.abs(lowerRight.x - upperLeft.x);
        int h = Math.abs(lowerRight.y - upperLeft.y);
        g.drawRect(x, y, w, h);
	}
}
