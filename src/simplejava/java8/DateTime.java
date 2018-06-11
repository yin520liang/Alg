/**
 * 
 */
package simplejava.java8;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

/**
 * @title
 * @description
 * @author lvzhaoyang
 * @date 2017年9月15日上午11:18:25
 */
public class DateTime {
	public static void main(String[] args) {
		// Get the system clock as UTC offset
		final Clock clock = Clock.systemUTC();
		System.out.println(clock.instant()); // 2017-09-15T03:19:32.382Z
		System.out.println(clock.millis()); // 1505445572449
		
		// Get the local date and local time
		final LocalDate date = LocalDate.now();
		final LocalDate dateFromClock = LocalDate.now( clock );
		         
		System.out.println( date ); // 2017-09-15
		System.out.println( dateFromClock ); // 2017-09-15
		         
		// Get the local date and local time
		final LocalTime time = LocalTime.now();
		final LocalTime timeFromClock = LocalTime.now( clock );
		         
		System.out.println( time ); // 11:22:28.287
		System.out.println( timeFromClock ); // 11:22:28.287
		
		// Get duration between two dates
		final LocalDateTime from = LocalDateTime.of( 2014, Month.APRIL, 16, 0, 0, 0 );
		final LocalDateTime to = LocalDateTime.of( 2015, Month.APRIL, 16, 23, 59, 59 );
		 
		final Duration duration = Duration.between( from, to );
		System.out.println( "Duration in days: " + duration.toDays() ); // 365
		System.out.println( "Duration in hours: " + duration.toHours() ); // 8783
	}
	
}
