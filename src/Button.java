import greenfoot.*;

public class Button extends Actor {
    private final String text;
    public Button(String text, int width, int height, Color color) {
        this.setImage(Commons.fill(width, height, color));
        this.text = text;
    }
    
    @Override public void addedToWorld(World world) {
        Commons.drawString(text, getX(), getY(), world);
    }
    
    public String getText() {
        return text;
    }
}
