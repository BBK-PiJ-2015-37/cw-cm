import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class PastMeetingTests {
	private PastMeeting m1;
	private Set<Contact> attendees;
	private Contact c1;
	private Contact c2;
	private Calendar date;
	private String notes;
	
	@Before
	public void setUp() {
		c1 = new ContactImpl(1, "John", "Likes cats");
		c2 = new ContactImpl(2, "Carol");
		date = new GregorianCalendar(2015, 2, 10);
		notes = "Good meeting";
		attendees = new HashSet<>();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsNegativeIDThrowsException() {
		attendees.add(c1);
		m1 = new PastMeetingImpl(-1, date, attendees, notes);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsEmptyContactSetThrowsException() {
		m1 = new PastMeetingImpl(1, date, attendees, notes);
	}
	
	@Test (expected = NullPointerException.class)
	public void testsNullDateThrowsException() {
		attendees.add(c1);
		date = null;
		m1 = new PastMeetingImpl(1, date, attendees, notes);
	}
	
	@Test (expected = NullPointerException.class)
	public void testsNullContactSetThrowsException() {
		attendees = null;
		m1 = new PastMeetingImpl(1, date, attendees, notes);
	}
	
	@Test (expected = NullPointerException.class)
	public void testsNullNotesStringThrowsException() {
		attendees.add(c1);
		attendees.add(c2);
		notes = null;
		m1 = new PastMeetingImpl(1, date, attendees, notes);
	}
	
	@Test
	public void testsGetNotesWithNonEmptyNotesString() {
		attendees.add(c1);
		attendees.add(c2);
		m1 = new PastMeetingImpl(1, date, attendees, notes);
		assertEquals("Good meeting", m1.getNotes());
	}
	
	@Test
	public void testsGetNotesWithEmptyNotesString() {
		attendees.add(c1);
		attendees.add(c2);
		notes = "";
		m1 = new PastMeetingImpl(1, date, attendees, notes);
		assertEquals("", m1.getNotes());
	}
}