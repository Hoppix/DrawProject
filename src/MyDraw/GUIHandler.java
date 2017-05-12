package MyDraw;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class GUIHandler
{
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
		chooser.addChoosableFileFilter(new TextFileFilter());

		// Define action listener adapters that connect the  buttons to the app
		gui.clear.addActionListener(new DrawActionListener("clear", this));
		gui.quit.addActionListener(new DrawActionListener("quit", this));
		gui.auto.addActionListener(new DrawActionListener("auto", this));
		gui.save.addActionListener(new DrawActionListener("save", this));
		gui.saveText.addActionListener(new DrawActionListener("saveText", this));
		gui.loadText.addActionListener(new DrawActionListener("loadText", this));


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
		}
		else if (command.equals("saveText"))
		{
			chooserSaveText();
		}
		else if (command.equals("loadText"))
		{

		}
		else if (command.equals("save"))
		{
			chooserSaveImage();
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
			gui.color = ColorHashMap.StringToColor(command);
			gui.imageG.setColor(gui.color);
			gui.imageG.setColor(gui.color);
		}
		else if (command.contains("changeBGColor"))
		{
			command = command.replace("changeBGColor", "");
			ColorHashMap map = new ColorHashMap();
			gui.colorBG = ColorHashMap.StringToColor(command);
			clear();
		}
	}

	public void clear()
	{
		executioner.cmdQueue.clear();

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
		//TODO paintG und imageG verwenden
		Point pA = new Point(5, 5);
		Point pB = new Point(150, 150);
		Point pC = new Point(300, 300);
		
		
		RectangleDrawer rect = new RectangleDrawer(pA, pB);
		FillRectangleDrawer rectFill = new FillRectangleDrawer(pB, pC);
		OvalDrawer oval = new OvalDrawer(pA, pB);
		FillOvalDrawer ovalFill = new FillOvalDrawer(pB, pC);
		LineDrawer line = new LineDrawer(pA, pB);
		ScribbleDrawer scribble = new ScribbleDrawer(pB, pC);
		TriangleDrawer triangle = new TriangleDrawer(pA, pC);

		this.clear();
		imageG.setColor(Color.black);
		paintG.setColor(Color.black);
		executioner.execute(rect);
		executioner.execute(oval);
		imageG.setColor(Color.green);
		paintG.setColor(Color.green);
		executioner.execute(rectFill);
		imageG.setColor(Color.red);
		paintG.setColor(Color.red);
		executioner.execute(ovalFill);
		executioner.execute(triangle);
		imageG.setColor(Color.blue);
		paintG.setColor(Color.blue);
		executioner.execute(line);
		imageG.setColor(Color.white);
		paintG.setColor(Color.white);
		executioner.execute(scribble);
		
		imageG.setColor(Color.black);
		paintG.setColor(Color.black);
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
		FileWriter fileWriter = new FileWriter(fileName);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		for (int i = 0; i < executioner.cmdQueue.size(); i++)
		{
			if (bufferedReader.readLine() == null)
			{
				bufferedWriter.write(executioner.cmdQueue.get(i).toString());
				bufferedWriter.newLine();
			}

		}

		bufferedWriter.close();
		bufferedReader.close();
	}

	public void loadFromText(String fileName) throws IOException
	{
		String line = null;
		String parse = null;
		String [] coordsSTR = null;
		int[] coordsINT = null;

		try
		{
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null)
			{
				//TODO read string input correctly
				if(line.contains("ScribbleDrawer"))
				{
					parse = line.replaceFirst("ScribbleDrawer: ", "");
					parse = parse.replaceFirst("start", "");
					parse = parse.substring(line.indexOf('(', line.indexOf(')')));
					coordsSTR = parse.split(",");

					coordsINT[0] = Integer.parseInt(coordsSTR[0]);
					coordsINT[1] = Integer.parseInt(coordsSTR[1]);

					Point start = new Point(coordsINT[0], coordsINT[1]);

					parse = line.replaceFirst("ScribbleDrawer: ", "");
					parse = parse.replaceFirst("start", "");
					parse = parse.substring(line.indexOf('(', line.indexOf(')')));
					coordsSTR = parse.split(",");

					coordsINT[0] = Integer.parseInt(coordsSTR[0]);
					coordsINT[1] = Integer.parseInt(coordsSTR[1]);

					Point end = new Point(coordsINT[0], coordsINT[1]);

					executioner.cmdQueue.add(new ScribbleDrawer(start, end));
				}
				else if(line.contains("LineDrawer"))
				{

				}
				else if(line.contains("RectangleDrawer"))
				{

				}
				else if(line.contains("FillRectangleDrawer"))
				{

				}
				else if(line.contains("OvalDrawer"))
				{

				}
				else if(line.contains("FillOvalDrawer"))
				{

				}
				else if(line.contains("TriangleDrawer"))
				{

				}

			}
			bufferedReader.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("datei nicht gefunden");
		}
	}

	private void chooserSaveImage()
	{
		chooser.setVisible(true);

		int retrieve = chooser.showSaveDialog(gui.drawFrame);
		String extension2 = chooser.getFileFilter().getDescription();
		String savePath = chooser.getSelectedFile().getAbsolutePath();

		if (retrieve == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				if (extension2.equals("*.bmp,*.BMP"))
				{
					try
					{
						this.writeImage(this.getDrawing(), savePath + ".bmp");

					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
			catch (NullPointerException e)
			{
				JOptionPane.showMessageDialog(frame, "Speichern abgebrochen");
				e.printStackTrace();
			}
		}
	}

	private void chooserSaveText()
	{
		chooser.setVisible(true);

		int retrieve = chooser.showSaveDialog(gui.drawFrame);
		String extension2 = chooser.getFileFilter().getDescription();
		String savePath = chooser.getSelectedFile().getAbsolutePath();

		if (retrieve == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				if (extension2.equals("*.txt"))
				{
					try
					{
						this.saveToText(savePath + ".txt");
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
			catch (NullPointerException e)
			{
				JOptionPane.showMessageDialog(frame, "Speichern abgebrochen");
				e.printStackTrace();
			}
		}
	}
}