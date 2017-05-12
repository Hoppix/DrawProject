package MyDraw;

import java.awt.*;

/**
 * Created by khopf on 11/05/2017.
 */
public class TriangleDrawer extends VisualObject implements Drawable
{


    public TriangleDrawer(Point start, Point end)
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
        
        int[] arrayX = {x1, x2, (x1 + x2)/2};
        int[] arrayY = {y2, y2, y1};
        
        
        g.drawPolygon(new Polygon(arrayX, arrayY, 3));


    }
}
