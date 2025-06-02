package timing;

public interface ITimer {
    /**
     * Starts a new timer. <br>
     * If called without a prior <b>stop</b>, it resets the current time, losing all 
     * time information.
     */
    void start();

    /**
     * Stops and resets the current timer. Does nothing if no timer is running
     * 
     * @return Elapsed <b>nanoseconds</b> since last <b>start</b> was invoked.
     */
    long stop();

    /**
     * Resumes the timer if it was previously paused. <br>
     * If called without a prior <b>pause</b>, it does nothing. <br>
     * If no <b>start</b> was called, it will <b>not</b> start the timer.
     */
    void resume();

    /**
     * Pauses the currently running timer, if there is one. Does not reset the timer.
     * 
     * @return Elapsed <b>nanoseconds</b> since last <b>start</b> or <b>resume</b> was invoked.
     */
    long pause();
}
