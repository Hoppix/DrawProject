package MyDraw;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class ColorHashMap
{
    public static HashMap<String, Color> colorMap;
    public static HashMap<Color, String> stringMap;
    
    public ColorHashMap()
    {
        colorMap = new HashMap<String, Color>();
        stringMap = new HashMap<Color, String>();
        
        colorMap.put("green", Color.green);
        stringMap.put(Color.green, "green");
        
        colorMap.put("blue", Color.blue);
        stringMap.put(Color.blue, "blue");
        
        colorMap.put("black", Color.black);
        stringMap.put(Color.black, "black");
        
        colorMap.put("red", Color.red);
        stringMap.put(Color.red, "red");
        
        colorMap.put("white", Color.white);
        stringMap.put(Color.white, "white");
        
        
    }
    
    public static String colorToString(Color color)
    {
        return stringMap.get(color);
    }
    
    public static Color StringToColor(String color)
    {
        return colorMap.get(color.toLowerCase());
    }
    
    public ArrayList<String> test()
    {
        ArrayList<String> colors = new ArrayList<String>();
        for (String value : stringMap.values())
        {
            colors.add(value);
        }
        return colors;
    }
}
