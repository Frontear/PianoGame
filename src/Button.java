import greenfoot.*;

public class Button extends Actor {
    private final String text;
    public Button(String text, int width, int height, Color color) {
        GreenfootImage image = new GreenfootImage(width, height);
        image.setColor(color);
        image.fill();
        this.setImage(image);
        
        this.text = text;
    }
    
    @Override public void act() {
        getWorld().showText(text, getX(), getY());
    }
}
