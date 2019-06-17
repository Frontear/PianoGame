import greenfoot.*;
import java.util.*;

public class FallingKey extends Actor {
    private boolean has_failed = false; // failed is set when a life is lost
    private final PianoKey key;
    private final Game game;
    public FallingKey(PianoKey key) {
        this.setImage(Commons.fill(key.getImage().getWidth(), key.black ? 10 : 20, Commons.SOFT_PURPLE.darker()));
        this.key = key;
        this.game = (Game) key.getWorld();
    }
    
    public boolean isIntersecting() {
        return getIntersectingObjects(PianoKey.class).stream().anyMatch(k -> k == key); // finds any key which is the same object
    }
    
    // prevents overlapping of the pianokey by reducing the width
    public boolean shorten(int factor) {
        setImage(Commons.fill(getImage().getWidth(), getImage().getHeight() - factor * 2, getImage().getColor()));

        return getImage().getHeight() != 1;
    }
    
    public void setFail(boolean fail) {
        // takes 5 ticks to fall a black falling note, 10 ticks for a white one
        int delay = (key.black ? 2 : 5) - PianoKey.factor; // reduce by factor, so that leniency reduces with falling speed
        if (delay < 0) {
            delay = 0; // It scales extremely fast and the game will become very difficult
        }
        // only set if it hasn't been set already
        if (!this.has_failed && game.timer > delay) {
            this.has_failed = fail;
        }
    }
    
    public boolean hasFailed() {
        return has_failed;
    }
}