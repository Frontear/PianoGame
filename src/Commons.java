import greenfoot.*;
import java.util.*;

public class Commons {
    public static final Color COLOR_DARK = new Color(36, 36, 36);
    public static final Color COLOR_LIGHT = new Color(219, 219, 219);
    public static final Color SOFT_PURPLE = new Color(186, 85, 211);
    private static final List<Map.Entry<Integer, Integer>> positions = new ArrayList<>(); // this is to be able to remove all text when necessary
    
    public static GreenfootImage fill(int width, int height, Color color)  {
        return fill(new GreenfootImage(width, (height <= 0 ? 1 : height)), color);
    }
    
    public static void reset(World world) {
        positions.forEach((e) -> world.showText("", e.getKey(), e.getValue())); // remove all showText
        world.getObjects(Actor.class).forEach(a -> world.removeObject(a)); // remove all actors
        world.setBackground(fill(world.getBackground(), world.getBackground().getColor())); // clear the background color, which removes drawImage stuff
    }
    
    public static GreenfootImage fill(GreenfootImage image, Color color) {
        image.setColor(color);
        image.fill(); // populates every pixel with the specified color
        
        return image;
    }
    
    public static void drawString(Object text, int x, int y, World world) {
        world.showText(String.valueOf(text), x, y); // it's an object so I don't need to parse it as a string myself.
        positions.add(new AbstractMap.SimpleEntry<>(x, y));
    }
}
