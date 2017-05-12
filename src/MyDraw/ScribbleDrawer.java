package MyDraw;

import java.awt.Graphics;
import java.awt.Point;

public class ScribbleDrawer extends VisualObject implements Drawable
{
    public Point startPoint;
    public Point endPoint;
    
    public ScribbleDrawer(Point start, Point end)
    {
        startPoint = start;
        endPoint = end;
    }
    
    @Override
    public void draw(Graphics g)
    {
        if(startPoint.x < 0 && startPoint.y < 0)
        {
            return;
        }
        
        int x1 = (int) startPoint.getX();
        int y1 = (int) startPoint.getY();
        int x2 = (int) endPoint.getX();
        int y2 = (int) endPoint.getY();
        
        g.drawLine(x1, y1, x2, y2);
    }
}
