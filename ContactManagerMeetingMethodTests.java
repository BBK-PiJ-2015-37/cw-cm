import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

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
	
	@Test
	public void testsAddFutureMeetingReturnsPositiveNonZeroInteger() {
		attendees = cm.getContacts(2,3,6,8);
		date = new GregorianCalendar(2017, 2, 10);
		int output = cm.addFutureMeeting(attendees, date);
		assertTrue(output > 0);
	}
	
	@Test
	public void testsAddFutureMeetingToEmptyMeetingList() {
		attendees = cm.getContacts(2,3,6,8);
		date = new GregorianCalendar(2017, 2, 10);
		int output = cm.addFutureMeeting(attendees, date);
		assertEquals(1, output);
	}
	
	@Test
	public void testsAddFutureMeetingToNonEmptyMeetingList() {
		attendees = cm.getContacts(2,3,6,8);
		date = new GregorianCalendar(2017, 2, 10);
		cm.addFutureMeeting(attendees, date);
		attendees = cm.getContacts(1,4,7,8);
		date = new GregorianCalendar(2016, 9, 10);
		cm.addFutureMeeting(attendees, date);
		attendees = cm.getContacts(4,5);
		date = new GregorianCalendar(2016, 5, 11);
		int output = cm.addFutureMeeting(attendees, date);
		assertEquals(3, output);
	}
	
	@Test (expected = NullPointerException.class)
	public void testsAddNewPastMeetingWithNullContactSetThrowsException() {
		date = new GregorianCalendar(2014, 2, 10);
		cm.addNewPastMeeting(null, date, "Notes");
	}
	
	@Test (expected = NullPointerException.class)
	public void testsAddNewPastMeetingWithNullDateThrowsException() {
		attendees = cm.getContacts(1,3,5,7);
		cm.addNewPastMeeting(attendees, null, "Notes");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsAddNewPastMeetingWithFutureDateThrowsException() {
		attendees = cm.getContacts(1,3,5,7);
		date = new GregorianCalendar(2017, 2, 10);
		cm.addNewPastMeeting(attendees, date, "Notes");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsAddNewPastMeetingWithEmptyContactSetThrowsException() {
		date = new GregorianCalendar(2015, 2, 10);
		cm.addNewPastMeeting(attendees, date, "Notes");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsAddNewPastMeetingWithUnknownContactThrowsException() {
		attendees = cm.getContacts(1,3,5,7);
		Contact unknown = new ContactImpl(999, "Mystery");
		attendees.add(unknown);
		date = new GregorianCalendar(2015, 2, 10);
		cm.addNewPastMeeting(attendees, date, "Notes");
	}
	
	
	@Test
	public void testsAddNewPastMeetingToEmptyMeetingList() {
		attendees = cm.getContacts(2,3,6,8);
		date = new GregorianCalendar(2015, 2, 10);
		cm.addNewPastMeeting(attendees, date, "Notes");
		attendees = cm.getContacts(2,3,6,8);
		date = new GregorianCalendar(2017, 2, 10);
		int output = cm.addFutureMeeting(attendees, date);
		assertEquals(2, output);
	}
	
	@Test
	public void testsAddNewPastMeetingToNonEmptyMeetingList() {
		attendees = cm.getContacts(2,3,6,8);
		date = new GregorianCalendar(2015, 2, 10);
		cm.addNewPastMeeting(attendees, date, "Notes");
		attendees = cm.getContacts(1,4,7,8);
		date = new GregorianCalendar(2014, 9, 10);
		cm.addNewPastMeeting(attendees, date, "Notes");
		attendees = cm.getContacts(4,5);
		date = new GregorianCalendar(2014, 5, 11);
		cm.addNewPastMeeting(attendees, date, "Notes");
		attendees = cm.getContacts(2,3,6,8);
		date = new GregorianCalendar(2017, 2, 10);
		int output = cm.addFutureMeeting(attendees, date);
		assertEquals(4, output);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsGetFutureMeetingWithPastMeetingThrowsException() {
		attendees = cm.getContacts(2,3,6,8);
		date = new GregorianCalendar(2015, 2, 10);
		cm.addNewPastMeeting(attendees, date, "Notes");
		cm.getFutureMeeting(1);
	}
	
	@Test
	public void testsGetFutureMeetingWithUnknownIdAndEmptyMeetingListReturnsNull() {
		assertNull(cm.getFutureMeeting(1));
	}
	
	@Test
	public void testsGetFutureMeetingWithUnknownIdAndNonEmptyMeetingListReturnsNull() {
		attendees = cm.getContacts(2,3,6,8);
		date = new GregorianCalendar(2015, 2, 10);
		cm.addNewPastMeeting(attendees, date, "Notes");
		assertNull(cm.getFutureMeeting(2));
	}
	
	@Test
	public void testsGetFutureMeetingWithOneMeetingInList() {
		attendees = cm.getContacts(1,2,3,4);
		date = new GregorianCalendar(2017, 2, 10);
		cm.addFutureMeeting(attendees, date);
		FutureMeeting output = cm.getFutureMeeting(1);
		assertTrue(output.getContacts().equals(attendees));
		assertTrue(output.getDate().equals(date));
	}
	
	@Test
	public void testsGetFutureMeetingWithMultipleMeetingsInList() {
		attendees = cm.getContacts(1,4,7,8);
		date = new GregorianCalendar(2016, 9, 10);
		cm.addFutureMeeting(attendees, date);
		attendees = cm.getContacts(1,4,7,8);
		date = new GregorianCalendar(2014, 9, 10);
		cm.addNewPastMeeting(attendees, date, "Notes");
		attendees = cm.getContacts(1,2,3,4);
		date = new GregorianCalendar(2017, 2, 10);
		cm.addFutureMeeting(attendees, date);
		FutureMeeting output = cm.getFutureMeeting(3);
		assertTrue(output.getContacts().equals(attendees));
		assertTrue(output.getDate().equals(date));
	}
	
	@Test
	public void testsGetMeetingWithValidId() {
		attendees = cm.getContacts(1,4,7,8);
		date = new GregorianCalendar(2016, 9, 10);
		cm.addFutureMeeting(attendees, date);
		Meeting output = cm.getMeeting(1);
		assertTrue(output.getContacts().equals(attendees));
		assertTrue(output.getDate().equals(date));
	}
	
	@Test
	public void testsGetMeetingWithInvalidIdReturnsNull() {
		attendees = cm.getContacts(1,4,7,8);
		date = new GregorianCalendar(2016, 9, 10);
		cm.addFutureMeeting(attendees, date);
		assertNull(cm.getMeeting(-2));
	}
	
	@Test
	public void testsGetMeetingWithUnknownIdReturnsNull() {
		attendees = cm.getContacts(1,4,7,8);
		date = new GregorianCalendar(2016, 9, 10);
		cm.addFutureMeeting(attendees, date);
		assertNull(cm.getMeeting(2));
	}
	
	@Test (expected = IllegalStateException.class)
	public void testsGetPastMeetingWithFutureMeetingThrowsException() {
		attendees = cm.getContacts(2,3,6,8);
		date = new GregorianCalendar(2017, 2, 10);
		cm.addFutureMeeting(attendees, date);
		cm.getPastMeeting(1);
	}
	
	@Test
	public void testsGetPastMeetingWithUnknownIdAndEmptyMeetingListReturnsNull() {
		assertNull(cm.getPastMeeting(1));
	}
	
	@Test
	public void testsGetPastMeetingWithUnknownIdAndNonEmptyMeetingListReturnsNull() {
		attendees = cm.getContacts(2,3,6,8);
		date = new GregorianCalendar(2015, 2, 10);
		cm.addNewPastMeeting(attendees, date, "Notes");
		assertNull(cm.getPastMeeting(2));
	}
	
	@Test
	public void testsGetPastMeetingWithOneMeetingInList() {
		attendees = cm.getContacts(2,3,6,8);
		date = new GregorianCalendar(2015, 2, 10);
		cm.addNewPastMeeting(attendees, date, "Notes");
		PastMeeting output = cm.getPastMeeting(1);
		assertTrue(output.getContacts().equals(attendees));
		assertTrue(output.getDate().equals(date));
	}
	
	@Test
	public void testsGetPastMeetingWithMultipleMeetingsInList() {
		attendees = cm.getContacts(1,4,7,8);
		date = new GregorianCalendar(2016, 9, 10);
		cm.addFutureMeeting(attendees, date);
		attendees = cm.getContacts(1,4,7,8);
		date = new GregorianCalendar(2014, 9, 10);
		cm.addNewPastMeeting(attendees, date, "Notes");
		attendees = cm.getContacts(2,3,6,8);
		date = new GregorianCalendar(2015, 2, 10);
		cm.addNewPastMeeting(attendees, date, "Notes");
		PastMeeting output = cm.getPastMeeting(3);
		assertTrue(output.getContacts().equals(attendees));
		assertTrue(output.getDate().equals(date));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsAddMeetingNotesWithUnknownMeetingIdThrowsException() {
		cm.addMeetingNotes(1, "Notes");
	}
	
	@Test (expected = IllegalStateException.class)
	public void testsAddMeetingNotesWithMeetingInFutureThrowsException() {
		attendees = cm.getContacts(1,4,7,8);
		date = new GregorianCalendar(2017, 9, 10);
		cm.addFutureMeeting(attendees, date);
		cm.addMeetingNotes(1, "Notes");
	}
	
	@Test (expected = NullPointerException.class)
	public void testsAddMeetingNotesWithNullNotesThrowsException() {
		attendees = cm.getContacts(1,4,7,8);
		date = new GregorianCalendar(2014, 9, 10);
		cm.addNewPastMeeting(attendees, date, "Notes");
		cm.addMeetingNotes(1, null);
	}
	
	@Test
	public void testsAddMeetingNotesWithPastMeetingAppendsNotes() {
		attendees = cm.getContacts(1,4,7,8);
		date = new GregorianCalendar(2014, 9, 10);
		cm.addNewPastMeeting(attendees, date, "Notes");
		cm.addMeetingNotes(1, "More notes");
		String expected = "Notes\nMore notes";
		String output = cm.getPastMeeting(1).getNotes();
		assertEquals(expected, output);
	}
	
	@Test
	public void testsAddMeetingNotesWithFutureMeetingNowPassedConvertsMeetingType() {
		attendees = cm.getContacts(1,4,7,8);
		date = new GregorianCalendar(2017, 9, 10);
		cm.addFutureMeeting(attendees, date);
		presentDate = new GregorianCalendar(2017, 9, 11);
		cm.addMeetingNotes(1, "Notes");
		assertTrue(cm.getMeeting(1) instanceof PastMeeting);
	}
	
	@Test
	public void testsAddMeetingNotesWithFutureMeetingNowPassedAddsNotes() {
		attendees = cm.getContacts(1,4,7,8);
		date = new GregorianCalendar(2017, 9, 10);
		cm.addFutureMeeting(attendees, date);
		presentDate = new GregorianCalendar(2017, 9, 11);
		cm.addMeetingNotes(1, "Notes");
		String expected = "Notes";
		String output = cm.getPastMeeting(1).getNotes();
		assertEquals(expected, output);
	}
}