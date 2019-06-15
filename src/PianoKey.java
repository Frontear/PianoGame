import greenfoot.*;

public class PianoKey extends Actor {
    private final boolean black;
    private boolean key_held = false; // we need a separate boolean so that we don't call stop and play over and over (act is a fast loop, if the key remains down, it wouldn't play anything)
    public final Color color, press;
    private final String key;
    private final GreenfootSound note; // sound files from http://theremin.music.uiowa.edu/MISpiano.html
    
    public PianoKey(boolean black, String key) {
        this.black = black;
        this.color = (black ? Color.BLACK : Color.WHITE);
        this.press = (black ? Color.WHITE.darker().darker() : Color.WHITE.darker());
        this.note = new GreenfootSound(String.format("%s.mp3", this.key = key));
        
        GreenfootImage image = new GreenfootImage((black ? 30 : 50), (black ? 100 : 200));
        image.setColor(color);
        image.fill();
        this.setImage(image);
    }
    
    @Override public void act() {
        getWorld().showText(key, getX(), getY() + getImage().getHeight() / 2 - 15);
        if (Greenfoot.isKeyDown(key)) {
            if (!key_held) {
                if (note.isPlaying()) {
                    note.stop();
                }
            
                note.play();
                key_held = true;
            }
            this.darken();
        }
        else {
            key_held = false;
            this.lighten();
        }
    }
    
    public void darken() {
        GreenfootImage image = this.getImage();
        image.setColor(press);
        image.fill();
        this.setImage(image);
    }
    
    public void lighten() {
        GreenfootImage image = this.getImage();
        image.setColor(color);
        image.fill();
        this.setImage(image);
    }
    
    public static void makeSequence(World world) {
        int x = 40; // to center the sequence
        PianoKey key, last; // temporary objects
        
        // this is very ugly, I am sorry
        world.addObject(key = new PianoKey(false, "a"), x += (key.getImage().getWidth() + 1), world.getHeight() - key.getImage().getHeight() / 2);
        world.addObject(key = new PianoKey(false, "s"), x += (key.getImage().getWidth() + 1), world.getHeight() - key.getImage().getHeight() / 2);
        world.addObject(key = new PianoKey(true, "w"), x -= (key.getImage().getWidth()), 210);  // the white keys need to be generated before black keys so that the black key overlaps them.
        last = key;
        world.addObject(key = new PianoKey(false, "d"), x += (key.getImage().getWidth() + last.getImage().getWidth() + 1), world.getHeight() - key.getImage().getHeight() / 2);
        world.addObject(key = new PianoKey(true, "e"), x -= (key.getImage().getWidth()), 210);
        last = key;
        world.addObject(key = new PianoKey(false, "f"), x += (key.getImage().getWidth() + last.getImage().getWidth() + 1), world.getHeight() - key.getImage().getHeight() / 2);
        world.addObject(key = new PianoKey(false, "g"), x += (key.getImage().getWidth() + 1), world.getHeight() - key.getImage().getHeight() / 2);
        world.addObject(key = new PianoKey(true, "t"), x -= (key.getImage().getWidth()), 210);
        last = key;
        world.addObject(key = new PianoKey(false, "h"), x += (key.getImage().getWidth() + last.getImage().getWidth() + 1), world.getHeight() - key.getImage().getHeight() / 2);
        world.addObject(key = new PianoKey(true, "y"), x -= (key.getImage().getWidth()), 210);
        last = key;
        world.addObject(key = new PianoKey(false, "j"), x += (key.getImage().getWidth() + last.getImage().getWidth() + 1), world.getHeight() - key.getImage().getHeight() / 2);
        world.addObject(key = new PianoKey(true, "u"), x -= (key.getImage().getWidth()), 210);
        last = key;
        world.addObject(key = new PianoKey(false, "k"), x += (key.getImage().getWidth() + last.getImage().getWidth() + 1), world.getHeight() - key.getImage().getHeight() / 2);
        world.addObject(key = new PianoKey(false, "l"), x += (key.getImage().getWidth() + 1), world.getHeight() - key.getImage().getHeight() / 2);
        world.addObject(key = new PianoKey(true, "o"), x -= (key.getImage().getWidth()), 210);
        last = key;
        world.addObject(key = new PianoKey(false, ";"), x += (key.getImage().getWidth() + last.getImage().getWidth() + 1), world.getHeight() - key.getImage().getHeight() / 2);
        world.addObject(key = new PianoKey(true, "p"), x -= (key.getImage().getWidth()), 210);
    }
}