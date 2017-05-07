package MyDraw;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

class DrawGUIs
{

    private static final long serialVersionUID = 5549826489329119875L;
    Color color;
    Color colorBG;
    BufferedImage saveImage;
    Graphics2D backgroundG;
    JPanel drawPanel;
    JFrame drawFrame;
    JButton clear;
    JButton quit;
    JButton auto;
    JButton save;
    JLabel shapeLabel;
    JLabel colorLabel;
    Choice shape_chooser;
    Choice color_chooser;

    /**
    * The GUI constructor does all the work of creating the GUI and setting
    * up event listeners.  Note the use of local and anonymous classes.
    */
    public DrawGUIs()
    {
        drawPanel = new JPanel();
        drawFrame = new JFrame();
        drawFrame.add(drawPanel);
        drawPanel.setLayout(null);
        drawFrame.setLayout(null);

        drawPanel.setBounds(0, 37, 694, 535);
        drawFrame.setBackground(Color.LIGHT_GRAY);
        drawFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        drawFrame.setPreferredSize(new Dimension(700, 600));
        drawFrame.pack();
        drawFrame.setTitle("MyDraw -  Gruppe 5");
        drawFrame.setResizable(false);
        drawFrame.setVisible(true);

        color = Color.black;
        colorBG = Color.white;

        
        // selector for drawing modes
        shape_chooser = new Choice();
        shape_chooser.add("Scribble");
        shape_chooser.add("Rectangle");
        shape_chooser.add("Oval");

        // selector for drawing colors
        color_chooser = new Choice();
        color_chooser.add("Black");
        color_chooser.add("Green");
        color_chooser.add("Red");
        color_chooser.add("Blue");

       
        // Create two buttons
        clear = new JButton("Clear");
        quit = new JButton("Quit");
        auto = new JButton("Auto");
        save = new JButton("Save");
                
        
        //Create two labels
        shapeLabel = new JLabel("Shape: ");
        colorLabel = new JLabel("Color: ");


        quit.setBounds(10, 10, 60, 20 );
        clear.setBounds(80, 10, 70, 20);
        shapeLabel.setBounds(160, 10, 50, 20);
        shape_chooser.setBounds(220, 10, 90, 20);
        colorLabel.setBounds(320, 10, 50, 20);
        color_chooser.setBounds(380, 10, 90, 20);
        save.setBounds(550, 10, 70, 20);
        auto.setBounds(480, 10, 60, 20);


        drawPanel.setBackground(colorBG);
        drawPanel.setForeground(colorBG);
        drawFrame.setBackground(colorBG);
        drawFrame.setForeground(colorBG);



        drawFrame.add(quit);
        drawFrame.add(clear);
        drawFrame.add(shapeLabel);
        drawFrame.add(shape_chooser);
        drawFrame.add(colorLabel);
        drawFrame.add(color_chooser);
        drawFrame.add(save);
        drawFrame.add(auto);



        saveImage = new BufferedImage(drawPanel.getWidth(), drawPanel.getHeight(), BufferedImage.TYPE_INT_RGB );
        backgroundG = saveImage.createGraphics();
        backgroundG.setColor(colorBG);
        backgroundG.fillRect(0, 37, 694, 535);




    } 
    //constructor end

    public void setHeight(int height)
    {
        Rectangle rekt = drawPanel.getBounds();
        drawPanel.setBounds(rekt.x, rekt.y, rekt.width, height);
    }

    public void setWidth(int width)
    {
        Rectangle rekt = drawPanel.getBounds(); //get rekt
        drawPanel.setBounds(rekt.x, rekt.y, width, rekt.height);
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
        Graphics g = drawPanel.getGraphics();
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
        Graphics g = drawPanel.getGraphics();
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
        Graphics g = drawPanel.getGraphics();
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
        Graphics g = drawPanel.getGraphics();
        g.setColor(colorBG);
        g.fillRect(0, 0, drawPanel.getSize().width, drawPanel.getSize().height);
        backgroundG.setColor(colorBG);
        backgroundG.fillRect(0, 0, drawPanel.getSize().width, drawPanel.getSize().height);

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

    /**
     * temporary help function for refactoring
     * @return background graphics of gui class
     */
    public Graphics2D getBackgroundG()
    {
        return backgroundG;
    }

    /**
     * @return draw perspective of the gui
     */
    public JPanel getDrawPanel()
    {
        return drawPanel;
    }

    public JFrame getDrawFrame()
    {
        return drawFrame;
    }
    

    
    
}