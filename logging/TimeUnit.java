package logging;

public enum TimeUnit {
    NANO(9),
    MICRO(6),
    MILLI(3),
    SEC(0);

    private final int value;
    private TimeUnit(int value) { this.value = value; }

    /**
     * Converts the given time value from one unit to another
     * @param time - the value to convert
     * @param from - the current unit of the time
     * @param to - the unit to convert into
     * @return - the time value in the new time unit
     */
    public static double  convertUnit(long time, TimeUnit from, TimeUnit to) {
        int power = from.value - to.value;

        return time / Math.pow(10, power);
    }
}
