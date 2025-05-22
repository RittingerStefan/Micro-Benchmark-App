package logging;

import logging.TimeUnit;
public class ConsoleLogger implements ILog {

	@Override
	public void write(String string) {
		System.out.println(string);
	}

	@Override
	public void write(long value) {
		System.out.println(value);
	}

	@Override
	public void write(Object... values) {
		StringBuilder sb = new StringBuilder();
		for (Object o : values)
			sb.append(o).append(" ");
		System.out.println(sb.toString().trim());
	}

	@Override
	public void writeTime(long value, TimeUnit unit) {
		System.out.println(TimeUnit.toTimeUnit(value, unit) + " " + unit.name().toLowerCase());
	}

	@Override
	public void writeTime(String string, long value, TimeUnit unit) {
		System.out.println(string + " " + TimeUnit.toTimeUnit(value, unit) + " " + unit.name().toLowerCase());
	}

	@Override
	public void close() {
		// nothing to close for console
	}
}
