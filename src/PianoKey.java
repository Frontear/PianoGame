import greenfoot.*;

public class PianoKey extends Actor {
    private final boolean black;
    private boolean key_held = false; // we need a separate boolean so that we don't call stop and play over and over (act is a fast loop, if the key remains down, it wouldn't play anything)
    public final Color color, press;
    private final String key;
    private final GreenfootSound note; // sound files from http://theremin.music.uiowa.edu/MISpiano.html
    
    public PianoKey(boolean black, String key) {
        this.black = black;
        this.color = (black ? Commons.COLOR_DARK.brighter() : Commons.COLOR_LIGHT.brighter());
        this.press = (black ? Commons.COLOR_LIGHT.darker().darker() : Commons.COLOR_LIGHT.darker());
        this.note = new GreenfootSound(String.format("%s.mp3", this.key = key));
        this.setImage(Commons.fill((black ? 30 : 50), (black ? 100 : 200), color));
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
        this.setImage(Commons.fill(getImage(), press));
    }
    
    public void lighten() {
        this.setImage(Commons.fill(getImage(), color));
    }
    
    public static void makeSequence(World world) {
        int x = 39; // to center the sequence
        PianoKey key, last = null; // temporary objects
        
        char bindings[] = "aswdefgthyjuklo;p".toCharArray(); // key bindings
        int i = 0;
        
        for (char c : "wwbwbwwbwbwbwwbwb".toCharArray()) { // piano key sequence
            final String binding = String.valueOf(bindings[i++]);
            switch (c) {
                case 'w':
                    if (last == null) {
                        world.addObject(key = new PianoKey(false, binding), x += (key.getImage().getWidth() + 1), world.getHeight() - key.getImage().getHeight() / 2);
                    }
                    else {
                        world.addObject(key = new PianoKey(false, binding), x += (key.getImage().getWidth() + last.getImage().getWidth() - 3), world.getHeight() - key.getImage().getHeight() / 2);
                        last = null; // necessary so that it doesn't try to offset by a black key that won't exist. This is for the 'ww' part of the keys
                    }
                    break;
                case 'b':
                    world.addObject(key = new PianoKey(true, binding), x -= (key.getImage().getWidth() - 4), 210);
                    last = key;
                    break;
            }
        }
    }
}