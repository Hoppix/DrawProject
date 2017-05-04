package MyDraw;
//This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
//Copyright (c) 1997 by David Flanagan
//This example is provided WITHOUT ANY WARRANTY either expressed or implied.
//You may study, use, modify, and distribute it for non-commercial purposes.
//For any commercial use, see http://www.davidflanagan.com/javaexamples

// minimal changes from AWT to Swing -> replace elements/classes
// behavior is similiar but not equal ! Why?
// Gruppe 5

import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*; //++

/** The application class.  Processes high-level commands sent by GUI */
public class Draw_swing
{
    protected JPanel panel; //chg
    protected JFrame frame;

    /** main entry point.  Just create an instance of this application class */
    public static void main(String[] args)
    {
        new Draw_swing();
        
    }

    /** Application constructor:  create an instance of our GUI class */
    public Draw_swing()
    {
        frame = new JFrame();
        panel = new DrawGUIs(this);
        //panel.setBounds(0, 37, 694, 535);      
        frame.setBackground(Color.LIGHT_GRAY);
        frame.getContentPane().setLayout(null);
        frame.add(panel);        
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(700, 600));
        frame.pack();
        frame.setTitle("MyDraw - Gruppe 5");
        frame.setResizable(false);
        frame.setVisible(true);      
       
    }
     
    
    public Container getFrame()
    {
        return frame;
    }

   
    /** This is the application method that processes commands sent by the GUI */
    public void doCommand(String command)
    {
        DrawGUIs drawScreen = (DrawGUIs) panel;
        if (command.equals("clear"))
        { 
           drawScreen.clear();

        }
        else if (command.equals("quit"))
        { // quit the application
            frame.dispose(); // close the GUI
            System.exit(0); // and exit.
        }
        else if(command.equals("auto"))
        {           
            drawScreen.autoDraw();
        }
        else if(command.equals("save"))
        {
            try
            {
                //TODO Verzeichnis anpassen!
                drawScreen.writeImage(drawScreen.getDrawing(), "C:\\Users\\5hopfman\\Desktop\\test.bmp");                              
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}


