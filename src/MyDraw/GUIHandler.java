package MyDraw;


import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.*;




public class GUIHandler {
    //TODO Draw commands in Command queue aufnehmen
    private JFileChooser chooser;
    private JFrame frame;
    public DrawGUIs gui;
    private String savePath;
    public Drawable drawer;
    public Graphics imageG;
    public Graphics paintG;
    public Queue<String> cmdQueue;
    public Stack<String> doneActions; //TODO: durchgef�hrte commands hier speichern (1.6)
    private Timer timer;

    public CommandHandler executioner;


    public GUIHandler(DrawGUIs getgui) {
        gui = getgui;

        chooser = new JFileChooser();
        cmdQueue = new LinkedList<String>();
        doneActions = new Stack<String>();

        imageG = gui.imageG;
        paintG = gui.paintG;
        frame = gui.drawFrame;

        executioner = new CommandHandler(this);

        chooser.addChoosableFileFilter(new BMPFileFilter());

        // Define action listener adapters that connect the  buttons to the app
        gui.clear.addActionListener(new DrawActionListener("clear", this));
        gui.quit.addActionListener(new DrawActionListener("quit", this));
        gui.auto.addActionListener(new DrawActionListener("auto", this));
        gui.save.addActionListener(new DrawActionListener("save", this));


        gui.shape_chooser.addItemListener(new ShapeItemListener(this));
        gui.color_chooser.addItemListener(new ColorItemListener(this));
        gui.colorBG_chooser.addItemListener(new ColorBGItemListener(this));

        gui.drawFrame.addWindowStateListener(new ResponsiveHandler(gui));

    }


    public void doCommand(String command) {
        if (command == null) {
            return;
        }
        if (command.equals("clear")) {
            clear();

        } else if (command.equals("quit")) {
            frame.dispose();
            System.exit(0);
        } else if (command.equals("auto")) {
            doneActions.add(command);
            //gui.autoDraw();
            System.out.println("fix Autoraw method");
        } else if (command.equals("save")) {
            chooser.setVisible(true);

            int retrieve = chooser.showSaveDialog(gui.drawFrame);

            if (retrieve == chooser.APPROVE_OPTION) {
                String extension2 = chooser.getFileFilter().getDescription();

                if (extension2.equals("*bmp,*.BMP")) {
                    /**
                     *  ignore
                     */
                }
            }
            try {
                savePath = chooser.getSelectedFile().getAbsolutePath();

                try {
                    this.writeImage(this.getDrawing(), savePath + ".bmp");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(frame, "Speichern abgebrochen");
                e.printStackTrace();
            }

        } else if (command.contains("changeShape")) {
            command = command.replace("changeShape", "");
            executioner.shape = command;

        } else if (command.contains("changeColor")) {
            System.out.println("changingColor: " + command);
            command = command.replace("changeColor", "");
            ColorHashMap map = new ColorHashMap();
            gui.color = map.StringToColor(command);
            gui.imageG.setColor(gui.color);
            gui.imageG.setColor(gui.color);
        } else if (command.contains("changeBGColor")) {
            command = command.replace("changeBGColor", "");
            ColorHashMap map = new ColorHashMap();
            gui.colorBG = map.StringToColor(command);
            clear();
        }
    }

    public void clear()
    {
        gui.paintG.setColor(gui.colorBG);
        gui.paintG.fillRect(0, 0, gui.drawPanel.getSize().width, gui.drawPanel.getSize().height);

        imageG.setColor(gui.colorBG);
        imageG.fillRect(0, 0, gui.drawPanel.getSize().width, gui.drawPanel.getSize().height);

    }

    public Image getDrawing()
    {
        return gui.saveImage;
    }

    public void writeImage(Image img, String filename) throws IOException
    {
        MyBMPFile.write(filename, img);
    }

}