import greenfoot.*;

public class Game extends World {
    private boolean main_menu = true, made_sequence;
    
    public Game() {
        super(640, 360, 1); // 16:9 aspect ratio
        
        // background
        GreenfootImage background = new GreenfootImage(this.getWidth(), this.getHeight());
        background.setColor(new Color(36, 36, 36));
        background.fill();
        this.setBackground(background);
        
    }
    
    @Override public void started() {
        GreenfootImage title = new GreenfootImage("Piano Game", 42, new Color(219, 219, 219), new Color(36, 36, 36));
        getBackground().drawImage(title, (getWidth() - title.getWidth()) / 2, getHeight() / 8);
        Actor button = new Button("Start", 160, 70, new Color(236, 236, 236));
        addObject(button, getWidth() / 2, (getHeight() - getHeight() / 8) - button.getImage().getHeight() / 16);
    }
    
    @Override public void act() {
        if (main_menu) {
            MouseInfo info = Greenfoot.getMouseInfo();
            if (info != null) {
                Actor actor = info.getActor();
                if (actor instanceof Button && info.getButton() == 1) { // left click
                    showText("", actor.getX(), actor.getY()); // this is necessary to remove the text created by button
                    removeObject(actor);
                    main_menu = false;
                }
            }
        }
        else {
            if (!made_sequence) {
                PianoKey.makeSequence(this);
                made_sequence = true;
            }
        }
    }
}