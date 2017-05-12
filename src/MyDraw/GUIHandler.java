package MyDraw;


import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.*;


public class GUIHandler
{
	private String savePath;
	private JFileChooser chooser;
	private JFrame frame;

	public DrawGUIs gui;
	public Graphics imageG;
	public Graphics paintG;
	public Queue<String> cmdQueue;
	public Stack<String> doneActions;
	public CommandHandler executioner;


	public GUIHandler(DrawGUIs getgui)
	{
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


	public void doCommand(String command)
	{
		if (command == null)
		{
			return;
		}
		if (command.equals("clear"))
		{
			clear();

		}
		else if (command.equals("quit"))
		{
			frame.dispose();
			System.exit(0);
		}
		else if (command.equals("auto"))
		{
			doneActions.add(command);
			autoDraw();
			System.out.println("fix Autoraw method");
		}
		else if (command.equals("save"))
		{
			chooser.setVisible(true);

			int retrieve = chooser.showSaveDialog(gui.drawFrame);

			if (retrieve == chooser.APPROVE_OPTION)
			{
				String extension2 = chooser.getFileFilter().getDescription();

				if (extension2.equals("*bmp,*.BMP"))
				{
					/**
					 *  ignore
					 */
				}
			}
			try
			{
				savePath = chooser.getSelectedFile().getAbsolutePath();

				try
				{
					this.writeImage(this.getDrawing(), savePath + ".bmp");
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			catch (NullPointerException e)
			{
				JOptionPane.showMessageDialog(frame, "Speichern abgebrochen");
				e.printStackTrace();
			}

		}
		else if (command.contains("changeShape"))
		{
			command = command.replace("changeShape", "");
			executioner.shape = command;

		}
		else if (command.contains("changeColor"))
		{
			System.out.println("changingColor: " + command);
			command = command.replace("changeColor", "");
			ColorHashMap map = new ColorHashMap();
			gui.color = map.StringToColor(command);
			gui.imageG.setColor(gui.color);
			gui.imageG.setColor(gui.color);
		}
		else if (command.contains("changeBGColor"))
		{
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

	public void autoDraw()
	{
		//TODO fix redundanz
		Point pA = new Point(5, 5);
		Point pB = new Point(150, 150);
		Point pC = new Point(300, 300);
		ArrayList<Point> points = new ArrayList<>();
		points.add(pA);
		points.add(pB);
		points.add(pC);

		gui.colorBG = Color.white;
		this.clear();

		gui.color = Color.black;
		this.drawRectangle(points.get(0), points.get(1));

		gui.color = Color.green;
		this.drawOval(points.get(1), points.get(2));

		gui.color = Color.red;
		this.drawPolyLine(points);

		gui.color = Color.blue;
		this.drawOval(points.get(0), points.get(2));
	}

	public void redraw()
	{

		for (int i = 0; i < executioner.cmdQueue.size(); i++)
		{
			executioner.cmdQueue.get(i).draw(gui.paintG);
			executioner.cmdQueue.get(i).draw(gui.imageG);
		}
	}

	public void undo()
	{
		executioner.cmdQueue.pollLast();
		executioner.cmdQueue.getLast().draw(gui.paintG);
		executioner.cmdQueue.getLast().draw(gui.imageG);
	}

	public void redo()
	{
		executioner.cmdQueue.getLast().draw(gui.paintG);
		executioner.cmdQueue.getLast().draw(gui.imageG);
	}

	public void drawRectangle(Point upper_left, Point lower_right)
	{
		Graphics g = gui.drawPanel.getGraphics();
		g.setColor(gui.color);

		int x = upper_left.x;
		int y = upper_left.y;
		int w = Math.abs(lower_right.x - upper_left.x);
		int h = Math.abs(lower_right.y - upper_left.y);

		g.drawRect(x, y, w, h);
		gui.imageG.setColor(gui.color);
		gui.imageG.drawRect(x, y, w, h);
	}

	public void drawOval(Point upper_left, Point lower_right)
	{
		Graphics g = gui.drawPanel.getGraphics();
		g.setColor(gui.color);

		int x = upper_left.x;
		int y = upper_left.y;
		int w = Math.abs(lower_right.x - upper_left.x);
		int h = Math.abs(lower_right.y - upper_left.y);


		g.drawOval(x, y, w, h);
		gui.imageG.setColor(gui.color);
		gui.imageG.drawOval(x, y, w, h);
	}

	public void drawPolyLine(java.util.List<Point> points)
	{
		Graphics g = gui.drawPanel.getGraphics();
		g.setColor(gui.color);
		gui.imageG.setColor(gui.color);

		for (int i = 0; i < points.size() - 1; i++)
		{
			if (points.get(i + 1).equals(null))
			{
				break;
			}
			Point pointA = points.get(i);
			Point pointB = points.get(i + 1);

			int xA = pointA.x;
			int yA = pointA.y;
			int xB = pointB.x;
			int yB = pointB.y;

			g.drawLine(xA, yA, xB, yB);

		}

		for (int i = 0; i < points.size() - 1; i++)
		{
			if (points.get(i + 1).equals(null))
			{
				break;
			}
			Point pointA = points.get(i);
			Point pointB = points.get(i + 1);

			int xA = pointA.x;
			int yA = pointA.y;
			int xB = pointB.x;
			int yB = pointB.y;

			gui.imageG.drawLine(xA, yA, xB, yB);

		}
	}

	public void saveToText(String fileName) throws IOException
	{
		//TODO overwrite tostring
		FileWriter fileWriter = new FileWriter(fileName);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		for (int i = 0; i <executioner.cmdQueue.size(); i++)
		{
			if (bufferedReader.readLine() == null)
			{
				System.out.println("Writing to line " + i + "..");
				bufferedWriter.write(executioner.cmdQueue.get(i).toString());
				bufferedWriter.newLine();
				System.out.println("Writing finished");
			}

		}

		bufferedWriter.close();
		bufferedReader.close();
	}

	public void loadFromText(String fileName) throws IOException
	{
		String line = null;

		try
		{
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null)
			{
				//TODO read string input correctly
			}

			bufferedReader.close();
			System.out.println("read txt file");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("datei nicht gefunden");
		}
	}

}