import greenfoot.*;

public class Game extends World {
    private boolean main_menu = true, made_sequence = false;
    
    public Game() {
        super(640, 360, 1); // 16:9 aspect ratio
        
        this.setBackground(Commons.fill(this.getWidth(), this.getHeight(), Commons.COLOR_DARK));
    }
    
    @Override public void started() {
        GreenfootImage title = new GreenfootImage("Piano Game", 42, Commons.COLOR_LIGHT, Commons.COLOR_DARK);
        getBackground().drawImage(title, (getWidth() - title.getWidth()) / 2, getHeight() / 8);
        Actor button = new Button("Start", 160, 70, Commons.COLOR_LIGHT.brighter());
        addObject(button, getWidth() / 2, (getHeight() - getHeight() / 8) - button.getImage().getHeight() / 16);
    }
    
    @Override public void act() {
        if (main_menu) {
            MouseInfo info = Greenfoot.getMouseInfo();
            if (info != null) {
                Actor actor = info.getActor();
                if (actor instanceof Button && info.getButton() == 1) { // left click
                    showText("", actor.getX(), actor.getY()); // this is necessary to remove the text created by button
                    setBackground(Commons.reset(getBackground()));
                    removeObject(actor);
                    main_menu = false;
                }
            }
        }
        else {
            if (!made_sequence) { // we can't make the sequence multiple times, or else it'll break the audio and crash
                PianoKey.makeSequence(this);
                made_sequence = true;
            }
        }
    }
}