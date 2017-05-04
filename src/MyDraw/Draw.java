package MyDraw;
//This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
//Copyright (c) 1997 by David Flanagan
//This example is provided WITHOUT ANY WARRANTY either expressed or implied.
//You may study, use, modify, and distribute it for non-commercial purposes.
//For any commercial use, see http://www.davidflanagan.com/javaexamples

// minimal changes from AWT to Swing -> replace elements/classes
// behavior is similiar but not equal ! Why?
// Gruppe 5



/** The application class.  Processes high-level commands sent by GUI */
public class Draw
{

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


