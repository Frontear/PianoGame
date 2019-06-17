import greenfoot.*;

public class Game extends World {
    private boolean main_menu = true, made_sequence = false;
    private boolean freeplay = false;
    
    public Game() {
        super(640, 360, 1, false); // 16:9 aspect ratio, also, no bounding
        
        this.setBackground(Commons.fill(this.getWidth(), this.getHeight(), Commons.COLOR_DARK));
    }
    
    @Override public void started() {
        GreenfootImage title = new GreenfootImage("Piano Mania", 42, Commons.COLOR_LIGHT, Commons.COLOR_DARK);
        getBackground().drawImage(title, (getWidth() - title.getWidth()) / 2, getHeight() / 8);
        Actor button = new Button("Start", 160, 40, Commons.COLOR_LIGHT.brighter());
        Actor freeplay = new Button("Freeplay", 100, 30, Commons.COLOR_LIGHT.brighter());
        addObject(button, getWidth() / 2, (getHeight() - getHeight() / 8) - button.getImage().getHeight() / 16);
        addObject(freeplay, getWidth() / 2, (getHeight() - getHeight() / 4) - freeplay.getImage().getHeight() / 16);
        
        // instructions
        Commons.drawString("1. Press the keys on the notes to play them", getWidth() / 2, getHeight() / 2, this);
        Commons.drawString("2. Try to play in time to the falling note", getWidth() / 2, getHeight() / 2 + 25, this);
        Commons.drawString("3. " + lives + " strikes and you are out", getWidth() / 2, getHeight() / 2 + 50, this);
    }
    
    public int lives = 5, score = 0; // has a minimum of 5 lives
    @Override public void act() {
        if (main_menu) {
            MouseInfo info = Greenfoot.getMouseInfo();
            if (info != null) {
                Actor actor = info.getActor();
                if (actor instanceof Button && info.getButton() == 1) { // left click
                    freeplay = ((Button) actor).getText().equals("Freeplay");
                    setBackground(Commons.reset(this));
                    main_menu = false;
                }
            }
        }
        else {
            if (!made_sequence) { // we can't make the sequence multiple times, or else it'll break the audio and crash
                PianoKey.makeSequence(this);
                made_sequence = true;
            }
            
            if (!freeplay) { // lives and scores don't matter in freeplay
                if (lives != 0) {
                    PianoKey.tick(this);
                    Commons.drawString("Lives: " + lives, 36, 7, this);
                    Commons.drawString("Score: " + score, 38, 7 + 25, this);
                }
                else {
                    Commons.reset(this);
                    Commons.drawString("Game Over!", getWidth() / 2, getHeight() / 2, this);
                    Commons.drawString("Score: " + score, getWidth() / 2, getHeight() / 2 + 25, this);
                }
            }
        }
    }
}
