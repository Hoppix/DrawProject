package MyDraw;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

class DrawGUIs extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 5549826489329119875L;
    GUIHandler app; // A reference to the application, to send commands to.
    Color color;
    Color colorBG;
    BufferedImage saveImage;
    Graphics2D backgroundG;

    /**
    * The GUI constructor does all the work of creating the GUI and setting
    * up event listeners.  Note the use of local and anonymous classes.
    */
    public DrawGUIs(GUIHandler guiHandler)
    {
        //super("Draw_swing"); // Create the window
        app = guiHandler; // Remember the application reference
        color = Color.black; // the current drawing color
        colorBG = Color.white;
        this.setBackground(colorBG);
        this.setForeground(colorBG);
        super.setBackground(colorBG);
        super.setForeground(colorBG);
        
        Container frame = guiHandler.getFrame();
        
        
        // selector for drawing modes
        Choice shape_chooser = new Choice();
        shape_chooser.add("Scribble");
        shape_chooser.add("Rectangle");
        shape_chooser.add("Oval");

        // selector for drawing colors
        Choice color_chooser = new Choice();
        color_chooser.add("Black");
        color_chooser.add("Green");
        color_chooser.add("Red");
        color_chooser.add("Blue");

       
        // Create two buttons
        JButton clear = new JButton("Clear");
        JButton quit = new JButton("Quit");
        JButton auto = new JButton("Auto");
        JButton save = new JButton("Save");
                
        
        //Create two labels
        JLabel shapeLabel = new JLabel("Shape: ");
        JLabel colorLabel = new JLabel("Color: ");
        
            

        // Set a LayoutManager, and add the choosers and buttons to the window.
        //this.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));       
        frame.add(quit);
        frame.add(clear);
        frame.add(shapeLabel);
        frame.add(shape_chooser);
        frame.add(colorLabel);
        frame.add(color_chooser);
        frame.add(save);
        frame.add(auto);
        quit.setBounds(10, 10, 60, 20 );
        clear.setBounds(80, 10, 70, 20);
        shapeLabel.setBounds(160, 10, 50, 20);
        shape_chooser.setBounds(220, 10, 90, 20);
        colorLabel.setBounds(320, 10, 50, 20);
        color_chooser.setBounds(380, 10, 90, 20);
        auto.setBounds(480, 10, 60, 20);
        save.setBounds(550, 10, 70, 20);
        this.setVisible(true);
        
      
        
        
        // Define action listener adapters that connect the  buttons to the app
        clear.addActionListener(new DrawActionListener("clear", app));
        quit.addActionListener(new DrawActionListener("quit", app));
        auto.addActionListener(new DrawActionListener("auto", app));
        save.addActionListener(new DrawActionListener("save", app));

        // this class determines how mouse events are to be interpreted,
        // depending on the shape mode currently set    
        class ShapeManager implements ItemListener
        {
            DrawGUIs gui;

            abstract class ShapeDrawer extends MouseAdapter
                    implements MouseMotionListener
            {
                public void mouseMoved(MouseEvent e)
                {
                    /* ignore */
                }
            }

            // if this class is active, the mouse is interpreted as a pen
            class ScribbleDrawer extends ShapeDrawer
            {
                int lastx, lasty;

                public void mousePressed(MouseEvent e)
                {
                    lastx = e.getX();
                    lasty = e.getY();
                }

                public void mouseDragged(MouseEvent e)
                {
                    
                    Graphics g = gui.getGraphics();
                    int x = e.getX(), y = e.getY();
                    g.setColor(gui.color);
                    g.setPaintMode();
                    g.drawLine(lastx, lasty, x, y);
                    backgroundG.setColor(color);
                    backgroundG.drawLine(lastx, lasty, x, y);
                    lastx = x;
                    lasty = y;
                    
                }
            }

            // if this class is active, rectangles are drawn
            class RectangleDrawer extends ShapeDrawer
            {
                int pressx, pressy;
                int lastx = -1, lasty = -1;

                // mouse pressed => fix first corner of rectangle
                public void mousePressed(MouseEvent e)
                {
                    pressx = e.getX();
                    pressy = e.getY();
                }

                // mouse released => fix second corner of rectangle
                // and draw the resulting shape
                public void mouseReleased(MouseEvent e)
                {
                    Graphics g = gui.getGraphics();
                    if (lastx != -1)
                    {
                        // first undraw a rubber rect
                        g.setXORMode(gui.color);
                        g.setColor(gui.getBackground());
                        backgroundG.setXORMode(gui.color);
                        backgroundG.setColor(gui.getBackground());
                        doDraw(pressx, pressy, lastx, lasty, g);
                        lastx = -1;
                        lasty = -1;
                    }
                    // these commands finish the rubberband mode
                    g.setPaintMode();
                    g.setColor(gui.color);
                    backgroundG.setPaintMode();
                    backgroundG.setColor(gui.color);
                    // draw the finel rectangle
                    doDraw(pressx, pressy, e.getX(), e.getY(), g);
                }

                // mouse released => temporarily set second corner of rectangle
                // draw the resulting shape in "rubber-band mode"
                public void mouseDragged(MouseEvent e)
                {
                    Graphics g = gui.getGraphics();
                    // these commands set the rubberband mode
                    g.setXORMode(gui.color);
                    g.setColor(gui.getBackground());
                    backgroundG.setXORMode(gui.color);
                    backgroundG.setColor(gui.getBackground());
                    if (lastx != -1)
                    {
                        // first undraw previous rubber rect
                        doDraw(pressx, pressy, lastx, lasty, g);

                    }
                    lastx = e.getX();
                    lasty = e.getY();
                    // draw new rubber rect
                    doDraw(pressx, pressy, lastx, lasty, g);
                }

                public void doDraw(int x0, int y0, int x1, int y1, Graphics g)
                {
                    // calculate upperleft and width/height of rectangle
                    int x = Math.min(x0, x1);
                    int y = Math.min(y0, y1);
                    int w = Math.abs(x1 - x0);
                    int h = Math.abs(y1 - y0);
                    // draw rectangle
                    g.drawRect(x, y, w, h);
                    backgroundG.setColor(color);
                    backgroundG.drawRect(x, y, w, h);
                    
                }
            }

            // if this class is active, ovals are drawn
            class OvalDrawer extends RectangleDrawer
            {
                public void doDraw(int x0, int y0, int x1, int y1, Graphics g)
                {
                    int x = Math.min(x0, x1);
                    int y = Math.min(y0, y1);
                    int w = Math.abs(x1 - x0);
                    int h = Math.abs(y1 - y0);
                    // draw oval instead of rectangle
                    g.drawOval(x, y, w, h);
                    backgroundG.setColor(color);
                    backgroundG.drawOval(x, y, w, h);
                }
            }

            ScribbleDrawer scribbleDrawer = new ScribbleDrawer();
            RectangleDrawer rectDrawer = new RectangleDrawer();
            OvalDrawer ovalDrawer = new OvalDrawer();
            ShapeDrawer currentDrawer;

            public ShapeManager(DrawGUIs itsGui)
            {
                gui = itsGui;
                // default: scribble mode
                currentDrawer = scribbleDrawer;
                // activate scribble drawer
                gui.addMouseListener(currentDrawer);
                gui.addMouseMotionListener(currentDrawer);
            }

            // reset the shape drawer
            public void setCurrentDrawer(ShapeDrawer l)
            {
                if (currentDrawer == l) return;

                // deactivate previous drawer
                gui.removeMouseListener(currentDrawer);
                gui.removeMouseMotionListener(currentDrawer);
                // activate new drawer
                currentDrawer = l;
                gui.addMouseListener(currentDrawer);
                gui.addMouseMotionListener(currentDrawer);
            }

            // user selected new shape => reset the shape mode
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getItem()
                    .equals("Scribble"))
                {
                    setCurrentDrawer(scribbleDrawer);
                }
                else if (e.getItem()
                    .equals("Rectangle"))
                {
                    setCurrentDrawer(rectDrawer);
                }
                else if (e.getItem()
                    .equals("Oval"))
                {
                    setCurrentDrawer(ovalDrawer);
                }
            }
        }

        shape_chooser.addItemListener(new ShapeManager(this));     
        color_chooser.addItemListener(new ColorItemListener(this));

        // Handle the window close request similarly
        // Finally, set the size of the window, and pop it up        
        Container DrawContainer = this; 
        DrawContainer.setBackground(colorBG);
        this.setVisible(true); 
        
        this.setBounds(0, 37, 694, 535); 
        saveImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB );        
        backgroundG = saveImage.createGraphics();
        backgroundG.setColor(colorBG);
        backgroundG.fillRect(0, 37, 694, 535);
        
    } 
    //constructor end

    public void setHeight(int height)
    {
        Rectangle rekt = this.getBounds();
        this.setBounds(rekt.x, rekt.y, rekt.width, height);
    }

    public void setWidth(int width)
    {
        Rectangle rekt = this.getBounds(); //get rekt
        this.setBounds(rekt.x, rekt.y, width, rekt.height);
    }
    

    public void setFGColor(String new_color) throws ColorException
    {
        String colorLowercase = new_color.toLowerCase();
        if (colorLowercase.equals("black"))
        {
            color = Color.black;
        }
        else if (colorLowercase.equals("blue"))
        {
            color = Color.blue;
        }
        else if (colorLowercase.equals("red"))
        {
            color = Color.red;
        }
        else if (colorLowercase.equals("green"))
        {
            color = Color.green;
        }
        else if(colorLowercase.equals("white"))
        {
            color = Color.white;
        }
        else
        {
            throw new ColorException();
        }
              

    }
    public String getFGColor()
    {
        if(color.equals(Color.black))
        {
            return "black";
        }
        else if(color.equals(Color.blue))
        {
            return "blue";
        }
        else if(color.equals(Color.red))
        {
            return "red";
        }
        else if(color.equals(Color.green))
        {
            return "green";
        }
        else if(color.equals(Color.white))
        {
            return "white";
        }
        else
        {
            return null;
        }
    }
    
   
    

    public void setBGColor(String new_color) throws ColorException
    {
        String colorLowercase = new_color.toLowerCase();
        if (colorLowercase.equals("black"))
        {
            colorBG = Color.black;
        }
        else if (colorLowercase.equals("blue"))
        {
            colorBG = Color.blue;
        }
        else if (colorLowercase.equals("red"))
        {
            colorBG = Color.red;
        }
        else if (colorLowercase.equals("green"))
        {
            colorBG = Color.green;
        }
        else if(colorLowercase.equals("white"))
        {
            colorBG = Color.white;
        }
        else
        {
            throw new ColorException();
        }
              
    }
    
    public String getBGColor()
    {
        if(colorBG.equals(Color.black))
        {
            return "black";
        }
        else if(colorBG.equals(Color.blue))
        {
            return "blue";
        }
        else if(colorBG.equals(Color.red))
        {
            return "red";
        }
        else if(colorBG.equals(Color.green))
        {
            return "green";
        }
        else if(colorBG.equals(Color.white))
        {
            return "white";
        }
        else
        {
            return null;
        }
    }
    
    public void drawRectangle(Point upper_left, Point lower_right)
    {
        Graphics g = this.getGraphics();              
        int x = upper_left.x;
        int y = upper_left.y;
        int w = Math.abs(lower_right.x - upper_left.x);
        int h = Math.abs(lower_right.y - upper_left.y);
        // draw rectangle
        g.setColor(color);
        g.drawRect(x, y, w, h);
        backgroundG.setColor(color);
        backgroundG.drawRect(x, y, w, h);
        
    }
    
    public void drawOval(Point upper_left, Point lower_right)
    {
        Graphics g = this.getGraphics();        
        g.setColor(color);
        
        int x = upper_left.x;
        int y = upper_left.y;
        int w = Math.abs(lower_right.x - upper_left.x);
        int h = Math.abs(lower_right.y - upper_left.y);
        // draw rectangle
        g.drawOval(x, y, w, h);
        backgroundG.setColor(color);
        backgroundG.drawOval(x, y, w, h);
    }
    
    public void drawPolyLine(java.util.List<Point> points)
    {
        Graphics g = this.getGraphics();        
        g.setColor(color);
        backgroundG.setColor(color);
        for(int i = 0; i < points.size() -1; i++)
        {
            if(points.get(i+1).equals(null))
            {
                break;
            }
            Point pointA = points.get(i);
            Point pointB = points.get(i+1);
            
            int xA = pointA.x;
            int yA = pointA.y;
            int xB = pointB.x;
            int yB = pointB.y;
            
            g.drawLine(xA, yA, xB, yB);
            
        }
        
        for(int i = 0; i < points.size() -1; i++)
        {
            if(points.get(i+1).equals(null))
            {
                break;
            }
            Point pointA = points.get(i);
            Point pointB = points.get(i+1);
            
            int xA = pointA.x;
            int yA = pointA.y;
            int xB = pointB.x;
            int yB = pointB.y;
            
            backgroundG.drawLine(xA, yA, xB, yB);
            
        }
        
    }
    public Image getDrawing()
    {
        return saveImage;
    }
    
   
    
    public void clear()
    {
        Graphics g = this.getGraphics();
        g.setColor(colorBG);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        backgroundG.setColor(colorBG);
        backgroundG.fillRect(0, 0, this.getSize().width, this.getSize().height);

    }
    
    public void autoDraw()
    {
        Point pA = new Point(5, 5);
        Point pB = new Point (150, 150);
        Point pC = new Point(300,300);
        ArrayList<Point> points = new ArrayList<>();
        points.add(pA);
        points.add(pB);
        points.add(pC);
      
        try
        { 
            this.setBGColor("white");
            this.clear();

            this.setFGColor("black");
            this.drawRectangle(points.get(0), points.get(1));
            this.setFGColor("GrEEn");
            this.drawOval(points.get(1), points.get(2));
            this.setFGColor("RED");
            this.drawPolyLine(points);
            this.setFGColor("blue");
            this.drawOval(points.get(0), points.get(2));
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }                           
    }
    
    @Deprecated
    public void writeImageDeprecated(Image img, String filename) throws IOException
    {       
        File output = new File(filename);
        ImageIO.write((RenderedImage) img, "bmp", output);
    }
    
    public void writeImage(Image img, String filename) throws IOException
    {
        MyBMPFile.write(filename, img);
    }
    

    
    
}