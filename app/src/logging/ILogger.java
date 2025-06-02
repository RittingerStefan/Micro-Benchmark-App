package logging;

public interface ILogger {
    // subject to change - we'll add only the ones we actually need

    /**
     * Writes the given parameters.
     * @param toWrite - the long to write
     */
    void write(long toWrite);

    /**
     * Writes the given parameters.
     * @param toWrite - the String to write
     */
    void write(String toWrite);

    /**
     * Writes the given parameters.
     * @param toWrite - represents different things depending on implementation.
     */
    void write(Object...toWrite);

    /**
     * Writes the time value given as parameter in the specified time unit
     * @param time - the time value to write
     * @param timeUnit - the time unit for the value
     */
    void writeTime(long time, TimeUnit timeUnit);

    /**
     * Writes a string followed by the given time value in the specified time unit
     * @param string - the string to write before the time
     * @param time - the time value to write
     * @param timeUnit - the time unit for the value
     */
    void writeTime(String string, long time, TimeUnit timeUnit);

    /**
     * Closes whatever stream was needed to write the information, if there is a 
     * case to do so.
     */
    void close();
}
