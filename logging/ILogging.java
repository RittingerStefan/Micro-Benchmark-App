package logging;

public interface ILogging {
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
     * Closes whatever stream was needed to write the information, if there is a 
     * case to do so.
     */
    void close();
}
