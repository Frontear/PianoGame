import greenfoot.*;

public class Commons {
    public static final Color COLOR_DARK = new Color(36, 36, 36);
    public static final Color COLOR_LIGHT = new Color(219, 219, 219);
    
    public static GreenfootImage fill(int width, int height, Color color)  {
        return fill(new GreenfootImage(width, height), color);
    }
    
    // Greenfoot does not remove elements, like drawImage, so we need to reset it. This is really preventable on Greenfoots end...
    public static GreenfootImage reset(GreenfootImage image) {
        return fill(image, image.getColor());
    }
    
    public static GreenfootImage fill(GreenfootImage image, Color color) {
        image.setColor(color);
        image.fill(); // populates every pixel with the specified color
        
        return image;
    }
}