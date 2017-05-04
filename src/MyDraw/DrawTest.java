package MyDraw;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DrawTest
{
    Draw_swing testApp;
    DrawGUIs testMe;
    Image testCorrect;
    
    //git commit test 1

    public DrawTest()
    {
        testApp = new Draw_swing();
        testMe = new DrawGUIs(testApp);
    }

    @Test
    public void testGetSetBG()
    {
        try
        {
            testMe.setBGColor("white");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        assertEquals(testMe.getBGColor() , "white");
        assertNotEquals("black", testMe.getBGColor());
    }
    
    @Test
    public void testeGetSetFG()
    {
        try
        {
            testMe.setFGColor("red");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        assertEquals(testMe.getFGColor(), "red");
        assertNotEquals("green", testMe.getFGColor());
    }
    
    @Test(expected = ColorException.class)
    public void testColorUndefined() throws ColorException
    {
        testMe.setFGColor("top kek");
    }
    
    @Test(expected = ColorException.class)
    public void testColorBGUndefined() throws ColorException
    {
        testMe.setBGColor("get rekt");
    }
    
    @Test
    public void testSetGetHeight()
    {
        testMe.setHeight(42);
        assertEquals(42, testMe.getHeight());
        assertNotEquals(0, testMe.getHeight());
        
        testMe.setHeight(1337);
        assertEquals(1337, testMe.getHeight());
        assertNotEquals(0, testMe.getHeight());
    }
    
    @Test
    public void testSetGetWidth()
    {
        testMe.setWidth(42);
        assertEquals(42, testMe.getWidth());
        assertNotEquals(0, testMe.getWidth());
        
        testMe.setWidth(1337);
        assertEquals(1337, testMe.getWidth());
        assertNotEquals(0, testMe.getWidth());
    }
    
  
    @Test
    public void testAutoDraw() throws IOException, InterruptedException
    {
        //TODO Verzeichnis korrigieren!
        testCorrect =  MyBMPFile.read("C:\\Users\\5hopfman\\Desktop\\testCorrect.bmp");
        testMe.autoDraw();
        testMe.writeImage(testMe.getDrawing(), "C:\\Users\\5hopfman\\Desktop\\test.bmp");
        BufferedImage testImg = (BufferedImage) testMe.getDrawing();
        BufferedImage buffImg = convertToBufferedImage(testCorrect);
        boolean boolshit = true;
        
        if(testImg.getHeight() == buffImg.getHeight() && testImg.getWidth() == buffImg.getWidth())
        {
            for(int x = 0; x < buffImg.getWidth(); x++)
            {
                for(int y = 0; y < buffImg.getHeight(); y++)
                {
                    if(testImg.getRGB(x, y) != buffImg.getRGB(x, y))
                    {
                        boolshit =  false;
                        break;
                    }
                }
            }
        }
        else
        {
            boolshit = false;
        }

        assertTrue(boolshit);
        
    }
    
    private BufferedImage convertToBufferedImage(Image image)
    {
        BufferedImage newImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }
    
    @Before
    public void resetTestMe()
    {
        testApp = new Draw_swing();
        testMe = (DrawGUIs) testApp.panel;
    }

}
