package MyDraw;

import java.awt.Graphics;

import MyDraw.Drawable;

public class Oval  extends visualObject implements Drawable
{	
	public Oval()
	{
		
	}
	@Override
	public void draw(Graphics g) 
	{
		int x = upperLeft.x;
        int y = upperLeft.y;
        int w = Math.abs(lowerRight.x - upperLeft.x);
        int h = Math.abs(lowerRight.y - upperLeft.y);
        g.drawOval(x, y, w, h);
	}
}