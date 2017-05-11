package MyDraw;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClickListener implements MouseListener
{
    CommandHandler parentHandler;
    public int x;
    public int y;

    public MouseClickListener(CommandHandler handler)
    {
        parentHandler = handler;
        //startPoint = new Point();
    }

    @Override
    public void mouseClicked(MouseEvent arg0)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent arg0)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent arg0)
    {
        switch (parentHandler.shape)
        {
        case "Rectangle":
            parentHandler.cmdQueue.add(new RectangleDrawer(arg0.getPoint(), arg0.getPoint()));            
            break;
        case "Oval":
            parentHandler.cmdQueue.add(new OvalDrawer(arg0.getPoint(), arg0.getPoint()));            
            break;
        case "Scribble":
            parentHandler.cmdQueue.add(new ScribbleDrawer(arg0.getPoint(), arg0.getPoint()));            
            break;

        default:
            break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0)
    {        
        
    }

}
