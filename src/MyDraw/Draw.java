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
public class Draw
{
    protected JPanel panel; //chg
    protected JFrame frame;
    protected GUIHandler app;
    protected DrawGUIs gui;

    /** main entry point.  Just create an instance of this application class */
    public static void main(String[] args)
    {
        new Draw();
    }
    
    public Draw()
    {
        app = new GUIHandler();
        gui = new DrawGUIs(app);
    }

    
}


