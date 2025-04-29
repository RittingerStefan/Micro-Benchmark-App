package timing;

public class Timer implements ITimer{
    private long startTime;   // obtained when start is pressed
    private long currentTime; // time between start/resume and pause
    private long totalPause;  // total time the timer has been paused
    private long pauseTime;

    public void start() {
        // start timer
        startTime = System.nanoTime();

        // initialize other timing variables
        currentTime = startTime;
        totalPause = 0; pauseTime = 0;
    }

    public long stop() {
        long total;

        // if timer was paused but never resumed,
        // account for the paused time
        if(pauseTime > 0)
            totalPause = System.nanoTime() - pauseTime;

        // compute total
        total = System.nanoTime() - startTime - totalPause;
        // reset timer
        startTime = 0; currentTime = 0;
        return total;
    }

    public long pause() {
        // compute elapsed time since start/resume
        long elapsed = System.nanoTime() - currentTime;

        // record time of pause
        pauseTime = System.nanoTime();

        // reset current time
        currentTime = 0;
        return elapsed;
    }

    public void resume() {
        // add paused time to total
        totalPause += System.nanoTime() - pauseTime;

        // count current time again
        currentTime = System.nanoTime();

        // reset pause 
        pauseTime = 0;
    }

}
