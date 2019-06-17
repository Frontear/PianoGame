import greenfoot.*;
import java.util.*;

public class FallingKey extends Actor {
    private boolean has_failed = false; // failed is set when a life is lost
    private final PianoKey key;
    public FallingKey(PianoKey key) {
        this.setImage(Commons.fill(key.getImage().getWidth(), key.black ? 10 : 20, Commons.SOFT_PURPLE.darker()));
        this.key = key;
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
        if (!this.has_failed) { // only set failure if it hasn't already failed
            this.has_failed = fail;
        }
    }
    
    public boolean hasFailed() {
        return has_failed;
    }
}