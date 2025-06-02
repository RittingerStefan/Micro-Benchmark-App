package logging;

public class ConsoleLogger implements ILogger{

    public void write(long param) {
        System.out.println(param);
    }

    public void write(String param) {
        System.out.println(param);
    }

    public void write(Object...param) {
        for(Object o:param)
            System.out.print(o);
        System.out.println();
    }

    public void writeTime(long time, TimeUnit timeUnit) {
        System.out.println(TimeUnit.convertUnit(time, TimeUnit.NANO, timeUnit) + " " + timeUnit);
    }

    public void writeTime(String string, long time, TimeUnit timeUnit) {
        System.out.print(string);
        System.out.println(TimeUnit.convertUnit(time, TimeUnit.NANO, timeUnit) + " " + timeUnit);
    }
    
    public void close() {

    }

}
