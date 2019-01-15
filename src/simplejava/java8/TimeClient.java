/**
 * 
 */
package simplejava.java8;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @title
 * @description
 */
public interface TimeClient {

	void setTime(int hour, int minute, int second);

	void setDate(int day, int month, int year);

	void setDateAndTime(int day, int month, int year, int hour, int minute, int second);

	LocalDateTime getLocalDateTime();

	ZonedDateTime getZonedDateTime(String zoneString);
	
	// 静态方法
	static ZoneId getZoneId (String zoneString) {
        try {
            return ZoneId.of(zoneString);
        } catch (DateTimeException e) {
            System.err.println("Invalid time zone: " + zoneString +
                "; using default time zone instead.");
            return ZoneId.systemDefault();
        }
    }

	// 1. 直接继承默认方法
	interface AnotherTimeClient extends TimeClient {
	}

	// 2. 重新声明默认方法，但不提供实现，变为abstract，实现接口的类需要提供实现
	interface AbstractZoneTimeClient extends TimeClient {
		ZonedDateTime getZonedDateTime(String zoneString);
	}

	// 3. 重写默认方法实现
	interface HandleInvalidTimeZoneClient extends TimeClient {
		default public ZonedDateTime getZonedDateTime(String zoneString) {
			try {
				return ZonedDateTime.of(getLocalDateTime(), ZoneId.of(zoneString));
			} catch (DateTimeException e) {
				System.err.println("Invalid zone ID: " + zoneString + "; "
						+ "using the default time zone instead.");
				return ZonedDateTime.of(getLocalDateTime(), ZoneId.systemDefault());
			}
		}
	}
}
