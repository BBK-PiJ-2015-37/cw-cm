import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.Before;

/**
 * A set of tests for the FutureMeetingImpl class
 *
 * @author Sam Jansen
 */

public class FutureMeetingTests {
	private Meeting m1;
	private Set<Contact> attendees;
	private Contact c1;
	private Contact c2;
	private Calendar date;
	
	@Before
	public void setUp() {
		c1 = new ContactImpl(1, "John", "Likes cats");
		c2 = new ContactImpl(2, "Carol");
		date = new GregorianCalendar(2017, 2, 20);
		attendees = new HashSet<>();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsNegativeIDThrowsException() {
		attendees.add(c1);
		m1 = new FutureMeetingImpl(-1, date, attendees);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsEmptyContactSetThrowsException() {
		m1 = new FutureMeetingImpl(1, date, attendees);
	}
	
	@Test (expected = NullPointerException.class)
	public void testsNullDateThrowsException() {
		attendees.add(c1);
		date = null;
		m1 = new FutureMeetingImpl(1, date, attendees);
	}
	
	@Test (expected = NullPointerException.class)
	public void testsNullContactSetThrowsException() {
		attendees = null;
		m1 = new FutureMeetingImpl(1, date, attendees);
	}
}