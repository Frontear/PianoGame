import greenfoot.*;

public class Game extends World {
    private GreenfootImage text, button, button_text;
    public Game() {
        super(640, 360, 1); // 16:9 aspect ratio
        
        // background
        GreenfootImage background = new GreenfootImage(this.getWidth(), this.getHeight());
        background.setColor(new Color(36, 36, 36));
        background.fill();
        this.setBackground(background);
        
        // example text
        this.text = new GreenfootImage("Piano Game", 32, new Color(219, 219, 219), background.getColor());
        
        // button
        this.button = new GreenfootImage(200, 40);
        this.button.setColor(new Color(219, 219, 219));
        this.button.fill();
        
        // button text
        this.button_text = new GreenfootImage("Start", 24, new Color(36, 36, 36), button.getColor());
    }
    
    @Override public void act() {
        getBackground().drawImage(text, (getWidth() - text.getWidth()) / 2, (getHeight() - text.getHeight()) / 8);
        getBackground().drawImage(button, (getWidth() - button.getWidth()) / 2, (getHeight() - button.getHeight() * 2));
        getBackground().drawImage(button_text, ((getWidth() - button_text.getWidth()) / 2), (getHeight() - button.getHeight() * 2) + button_text.getHeight() / 3);
    }
    
    // https://stackoverflow.com/a/13030061
    public Color getContrastColor(Color color) {
        double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
        return y >= 128 ? Color.BLACK : Color.WHITE;
    }
}
