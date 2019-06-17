import greenfoot.*;
import java.util.*;
import java.util.concurrent.*;

public class PianoKey extends Actor {
    final boolean black; // package-private, so that fallingkey can read it
    private boolean key_held = false; // we need a separate boolean so that we don't call stop and play over and over (act is a fast loop, if the key remains down, it wouldn't play anything)
    public final Color color, press; // the default and press down colors. 
    private final String key; // the key that must be pressed to play the note
    private final GreenfootSound note; // sound files from http://theremin.music.uiowa.edu/MISpiano.html
    private FallingKey falling_key; // a falling rectangle, which will fall over the key that must be played
    private boolean is_active = false; // is active represents the note that must next be played
    static int factor; // increases to increase difficulty
    
    private boolean can_reset = true; // can reset the game timer
    
    public PianoKey(boolean black, String key) {
        this.black = black;
        this.color = (black ? Commons.COLOR_DARK.brighter() : Commons.COLOR_LIGHT.brighter());
        this.press = (black ? Commons.COLOR_LIGHT.darker().darker() : Commons.COLOR_LIGHT.darker());
        this.note = new GreenfootSound(String.format("%s.mp3", this.key = key));
        this.setImage(Commons.fill((black ? 30 : 50), (black ? 100 : 200), color));
    }
    
    private boolean penalize_life = false, increase_score = false;
    @Override public void act() {
        Commons.drawString(key, getX(), getY() + getImage().getHeight() / 2 - 15, getWorld());
        
        if (Greenfoot.isKeyDown(key)) {
            if (!key_held) { // without this, the key sound does not play until after the key is let go. This is not what we want
                if (note.isPlaying()) { // specifically this part. Had we not checked key_held before, it wouldn't have a sustain effect
                    note.stop();
                }
            
                note.play();
                key_held = true;
            }
            this.setImage(Commons.fill(getImage(), press));
        }
        else {
            key_held = false;
            this.setImage(Commons.fill(getImage(), color));
        }
        
        if (is_active) {
            falling_key.setLocation(falling_key.getX(), falling_key.getY() + factor);
            if (falling_key.isIntersecting()) {
                if (can_reset) {
                    ((Game) getWorld()).timer = 0;
                    can_reset = false;
                }
                
                falling_key.setFail(!key_held); // has a built in leniency factor
                if (!falling_key.shorten(factor)) { // it can no longer shorten further
                    is_active = false;
                    getWorld().removeObject(falling_key);
                    if (falling_key.hasFailed()) {
                        if (!penalize_life) {
                            --((Game) getWorld()).lives;
                            penalize_life = true; // don't penalize more than once
                        }
                    }
                    else {
                        if (!increase_score) {
                            ++((Game) getWorld()).score;
                            increase_score = true;
                        }
                    }
                }
            }
        }
    }
    
    private void set_active() {
        falling_key = new FallingKey(this);
        penalize_life = false;
        increase_score = false;
        can_reset = true;
        getWorld().addObject(falling_key, getX(), 0 - falling_key.getImage().getHeight());
        is_active = true;
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
                    if (last == null) { // necessary so that it doesn't try to offset by a black key that won't exist. This is for the 'ww' part of the keys
                        world.addObject(key = new PianoKey(false, binding), x += (key.getImage().getWidth() + 1), world.getHeight() - key.getImage().getHeight() / 2);
                    }
                    else {
                        world.addObject(key = new PianoKey(false, binding), x += (key.getImage().getWidth() + last.getImage().getWidth() - 3), world.getHeight() - key.getImage().getHeight() / 2);
                        last = null;
                    }
                    break;
                case 'b':
                    world.addObject(key = new PianoKey(true, binding), x -= (key.getImage().getWidth() - 4), 210);
                    last = key;
                    break;
            }
        }
    }
    
    public static void tick(World world) {
        factor = (((Game) world).score / 10) + 1; // every 10 score, factor increases. +1 is there so that 0-10 is 1, 10-20 is 2, so on and so forth
        
        List<PianoKey> keys = world.getObjects(PianoKey.class);
        if (keys.stream().noneMatch(k -> k.is_active)) { // if no key is active
            int key = ThreadLocalRandom.current().nextInt(0, keys.size());
            keys.get(key).set_active();
        }
    }
}
