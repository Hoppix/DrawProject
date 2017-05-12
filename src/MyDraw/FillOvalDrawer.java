package MyDraw;

import java.awt.*;

/**
 * Created by khopf on 11/05/2017.
 */
public class FillOvalDrawer extends VisualObject implements Drawable
{


    public FillOvalDrawer(Point start, Point end)
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

        g.fillOval(x, y, w, h);
    }
}
