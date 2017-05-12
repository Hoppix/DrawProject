package MyDraw;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

class DrawGUIs
{
	private static final long serialVersionUID = 5549826489329119875L;
	private final static int FRAMEWIDTH = 820;
	private final static int FRAMEHEIGHT = 600;
	public final static int PANELDIFF = 60;
	Color color;
	Color colorBG;
	Color frameColor;
	BufferedImage saveImage;
	Graphics imageG;
	Graphics paintG;
	JPanel drawPanel;
	JFrame drawFrame;
	JButton clear;
	JButton quit;
	JButton auto;
	JButton save;
	JButton loadText;
	JButton saveText;
	JLabel shapeLabel;
	JLabel colorLabel;
	JLabel colorBGLabel;
	Choice shape_chooser;
	Choice color_chooser;
	Choice colorBG_chooser;

	/**
	 * aka code abomination.
	 */
	public DrawGUIs()
	{
		color = Color.black;
		colorBG = Color.white;
		frameColor = Color.lightGray;

		this.setupBorders();
		this.setupButtons();
		this.setupChooser();
		this.addButtons();


		saveImage = new BufferedImage(drawPanel.getWidth(), drawPanel.getHeight(), BufferedImage.TYPE_INT_RGB);

		imageG = saveImage.createGraphics();
		imageG.setColor(colorBG);
		imageG.fillRect(0, 20, drawPanel.getWidth(), drawPanel.getHeight());

		paintG = drawPanel.getGraphics();

	}

	public void setPanelHeight(int height)
	{
		Rectangle rekt = drawPanel.getBounds();
		drawPanel.setBounds(rekt.x, rekt.y, rekt.width, height);
	}


	public void setPanelWidth(int width)
	{
		Rectangle rekt = drawPanel.getBounds(); //get rekt
		drawPanel.setBounds(rekt.x, rekt.y, width, rekt.height);
	}


	private void setupBorders()
	{
		drawPanel = new JPanel();
		drawFrame = new JFrame();
		drawFrame.add(drawPanel);
		drawPanel.setLayout(null);
		drawFrame.setLayout(null);

		drawFrame.setBackground(Color.LIGHT_GRAY);
		drawFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		drawFrame.setPreferredSize(new Dimension(FRAMEWIDTH, FRAMEHEIGHT));
		drawFrame.pack();
		drawFrame.setTitle("MyDraw -  Gruppe 5");
		drawFrame.setResizable(true);
		drawFrame.setVisible(true);

		drawPanel.setBounds(0, PANELDIFF, drawFrame.getWidth(), drawFrame.getHeight() - PANELDIFF);

		drawFrame.getContentPane().setBackground(frameColor);
		drawFrame.getContentPane().setForeground(frameColor);
		drawPanel.setBackground(colorBG);
		drawPanel.setForeground(colorBG);
	}

	private void setupButtons()
	{
		// Create two buttons
		clear = new JButton("Clear");
		quit = new JButton("Quit");
		auto = new JButton("Auto");
		save = new JButton("Save");
		loadText = new JButton("Load Text");
		saveText = new JButton("Save Text");


		quit.setBounds(10, 10, 60, 20);
		clear.setBounds(80, 10, 70, 20);
		save.setBounds(650, 10, 70, 20);
		auto.setBounds(730, 10, 60, 20);
		loadText.setBounds(10, 35, 100, 20);
		saveText.setBounds(120, 35, 100, 20);

		quit.setBackground(frameColor);
		clear.setBackground(frameColor);
		save.setBackground(frameColor);
		auto.setBackground(frameColor);
		loadText.setBackground(frameColor);
		saveText.setBackground(frameColor);
	}

	private void setupChooser()
	{
		// selector for drawing modes
		shape_chooser = new Choice();
		shape_chooser.add("Scribble");
		shape_chooser.add("Line");
		shape_chooser.add("Rectangle");
		shape_chooser.add("FillRectangle");
		shape_chooser.add("Oval");
		shape_chooser.add("FillOval");

		// selector for drawing colors
		color_chooser = new Choice();
		color_chooser.add("Black");
		color_chooser.add("White");
		color_chooser.add("Green");
		color_chooser.add("Red");
		color_chooser.add("Blue");

		// selector for background color
		colorBG_chooser = new Choice();
		colorBG_chooser.add("White");
		colorBG_chooser.add("Black");
		colorBG_chooser.add("Green");
		colorBG_chooser.add("Red");
		colorBG_chooser.add("Blue");

		//Create two labels
		shapeLabel = new JLabel("Shape: ");
		colorLabel = new JLabel("Color: ");
		colorBGLabel = new JLabel("ColorBG: ");

		shape_chooser.setForeground(Color.black);
		color_chooser.setForeground(Color.black);
		colorBG_chooser.setForeground(Color.black);

		shapeLabel.setBounds(160, 10, 50, 20);
		shape_chooser.setBounds(220, 10, 90, 20);
		colorLabel.setBounds(320, 10, 50, 20);
		color_chooser.setBounds(370, 10, 90, 20);
		colorBGLabel.setBounds(480, 10, 60, 20);
		colorBG_chooser.setBounds(540, 10, 90, 20);
	}

	private void addButtons()
	{
		drawFrame.add(quit);
		drawFrame.add(clear);
		drawFrame.add(shapeLabel);
		drawFrame.add(shape_chooser);
		drawFrame.add(colorLabel);
		drawFrame.add(color_chooser);
		drawFrame.add(colorBGLabel);
		drawFrame.add(colorBG_chooser);
		drawFrame.add(save);
		drawFrame.add(auto);
		drawFrame.add(saveText);
		drawFrame.add(loadText);

	}


}