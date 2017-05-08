package MyDraw;


import java.io.IOException;
import javax.swing.*;


public class GUIHandler
{
    //TODO command queue
    private JFileChooser chooser;
    private JFrame frame;
    public DrawGUIs gui;
    private String savePath;


    public GUIHandler(DrawGUIs getgui)
    {
        gui = getgui;
        frame = gui.getDrawFrame();
        chooser = new JFileChooser();

        chooser.addChoosableFileFilter(new BMPFileFilter());

        // Define action listener adapters that connect the  buttons to the app
        gui.clear.addActionListener(new DrawActionListener("clear", this));
        gui.quit.addActionListener(new DrawActionListener("quit", this));
        gui.auto.addActionListener(new DrawActionListener("auto", this));
        gui.save.addActionListener(new DrawActionListener("save", this));

        gui.shape_chooser.addItemListener(new ShapeManager(gui));
        gui.color_chooser.addItemListener(new ColorItemListener(gui));

        gui.getDrawFrame().addWindowStateListener(new ResponsiveHandler(gui));
    }
    

    
    public void doCommand(String command)
    {
        if (command.equals("clear"))
        { 
           gui.clear();

        }
        else if (command.equals("quit"))
        {
            frame.dispose();
            System.exit(0);
        }
        else if(command.equals("auto"))
        {           
            gui.autoDraw();
        }
        else if(command.equals("save"))
        {
            chooser.setVisible(true);

            int retrieve = chooser.showSaveDialog(gui.getDrawFrame());

            if(retrieve == chooser.APPROVE_OPTION)
            {
                String extension2 = chooser.getFileFilter().getDescription();

                if(extension2.equals("*bmp,*.BMP"))
                {
                    /**
                     *  ignore
                     */
                }
            }
            try
            {
                savePath = chooser.getSelectedFile().getAbsolutePath();
            }
            catch (NullPointerException e)
            {
                JOptionPane.showMessageDialog(frame, "Speichern abgebrochen");
                e.printStackTrace();
            }

            try
            {
                gui.writeImage(gui.getDrawing(), savePath + ".bmp");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
