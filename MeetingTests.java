import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

/**
 * A set of tests for the MeetingImpl class
 *
 * @author Sam Jansen
 */

public class MeetingTests {
	private Meeting m1;
	private Set<Contact> attendees;
	private Contact c1;
	private Contact c2;
	private Contact c3;
	private Calendar date;
	
	@Before
	public void setUp() {
		c1 = new ContactImpl(1, "John", "Likes cats");
		c2 = new ContactImpl(2, "Bill", "Has gambling debts");
		c3 = new ContactImpl(3, "Carol");
		date = new GregorianCalendar(2016, 2, 13);
		attendees = new HashSet<>();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsNegativeIDThrowsException() {
		attendees.add(c1);
		m1 = new MockMeeting(-1, date, attendees);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsEmptyContactSetThrowsException() {
		m1 = new MockMeeting(1, date, attendees);
	}
	
	@Test (expected = NullPointerException.class)
	public void testsNullDateThrowsException() {
		attendees.add(c1);
		date = null;
		m1 = new MockMeeting(1, date, attendees);
	}
	
	@Test (expected = NullPointerException.class)
	public void testsNullContactSetThrowsException() {
		attendees = null;
		m1 = new MockMeeting(1, date, attendees);
	}
	
	@Test
	public void testsGetId() {
		attendees.add(c1);
		attendees.add(c2);
		m1 = new MockMeeting(115, date, attendees);
		assertEquals(115, m1.getId());
	}
	
	@Test
	public void testsGetDate() {
		attendees.add(c1);
		attendees.add(c2);
		m1 = new MockMeeting(115, date, attendees);
		Calendar expected = new GregorianCalendar(2016, 2, 13);
		assertEquals(expected, m1.getDate());
	}
	
	@Test
	public void testsGetContacts() {
		attendees.add(c1);
		attendees.add(c2);
		attendees.add(c3);
		m1 = new MockMeeting(115, date, attendees);
		Set<Contact> expected = new HashSet<>();
		expected.add(c1);
		expected.add(c2);
		expected.add(c3);
		assertEquals(expected, m1.getContacts());
	}
}