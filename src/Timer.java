public class Timer {
    private long ms;

    public Timer() {
        reset();
    }
    
    public void reset() {
        ms = System.nanoTime() / 1000000L;
    }

    public long getElapsedMS() {
        return (System.nanoTime() / 1000000L) - ms;
    }
    
    public boolean hasElapsedMS(long ms) {
        return getElapsedMS() >= ms;
    }
}