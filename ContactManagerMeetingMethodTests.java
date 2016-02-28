import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ContactManagerMeetingMethodTests {
	ContactManager cm;
	Calendar date;
	Set<Contact> attendees;
	Calendar presentDate;
	
	@Before
	public void setUp() {
		cm = new ContactManagerImpl();
		cm.addNewContact("John", "Likes cats");
		cm.addNewContact("Carol", "Good at business");
		cm.addNewContact("Derek", "Never wears a tie");
		cm.addNewContact("Tim", "Nice but dim");
		cm.addNewContact("Harriet", "Tough negotiator");
		cm.addNewContact("Isabel", "Looks at her phone too much");
		cm.addNewContact("Grant", "Never reads emails");
		cm.addNewContact("Fiona", "Rather argumentative");
		attendees = new HashSet<>();
		presentDate = new GregorianCalendar(2016, 2, 28);
	}
	
	@Test (expected = NullPointerException.class)
	public void testsAddFutureMeetingWithNullContactSetThrowsException() {
		date = new GregorianCalendar(2017, 2, 10);
		cm.addFutureMeeting(null, date);
	}
	
	@Test (expected = NullPointerException.class)
	public void testsAddFutureMeetingWithNullDateThrowsException() {
		attendees = cm.getContacts(1,3,5,7);
		cm.addFutureMeeting(attendees, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsAddFutureMeetingWithPastDateThrowsException() {
		attendees = cm.getContacts(1,3,5,7);
		date = new GregorianCalendar(2015, 2, 10);
		cm.addFutureMeeting(attendees, date);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsAddFutureMeetingWithEmptyContactSetThrowsException() {
		date = new GregorianCalendar(2017, 2, 10);
		cm.addFutureMeeting(attendees, date);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsAddFutureMeetingWithUnknownContactThrowsException() {
		attendees = cm.getContacts(1,3,5,7);
		Contact unknown = new ContactImpl(999, "Mystery");
		attendees.add(unknown);
		date = new GregorianCalendar(2017, 2, 10);
		cm.addFutureMeeting(attendees, date);
	}
}