import greenfoot.*;

public class Scene extends World {
    private GreenfootImage text;
    public Scene() {
        super(640, 360, 1); // 16:9 aspect ratio
        
        // background
        GreenfootImage background = new GreenfootImage(this.getWidth(), this.getHeight());
        background.setColor(new Color(36, 36, 36));
        background.fill();
        this.setBackground(background);
        
        // example text
        this.text = new GreenfootImage("Hello Scene", 32, new Color(219, 219, 219), background.getColor());
    }
    
    @Override public void act() {
        getBackground().drawImage(text, (getWidth() - text.getWidth()) / 2, (getHeight() - text.getHeight()) / 8);
    }
}
