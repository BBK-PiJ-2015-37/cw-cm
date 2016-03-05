import java.util.GregorianCalendar;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ContactManagerGetListMethodTests {
	private ContactManager cm;
	
	@Before
	public void SetUp() {
		cm = new ContactManagerImpl();
		
		cm.addNewContact("John", "Likes cats");
		cm.addNewContact("Carol", "Good at business");
		cm.addNewContact("Derek", "Never wears a tie");
		cm.addNewContact("Tim", "Nice but dim");
		cm.addNewContact("Harriet", "Tough negotiator");
		cm.addNewContact("Isabel", "Looks at her phone too much");
		cm.addNewContact("Grant", "Never reads emails");
		cm.addNewContact("Fiona", "Rather argumentative");
		cm.addNewContact("Gary", "Rarely invited to meetings");
		
		cm.addFutureMeeting(cm.getContacts(1,4,7), new GregorianCalendar(2018, 1, 3));
		cm.addNewPastMeeting(cm.getContacts(2,3), new GregorianCalendar(2015, 2, 28), "Notes");
		cm.addFutureMeeting(cm.getContacts(5,6,7), new GregorianCalendar(2017, 11, 20));
		cm.addNewPastMeeting(cm.getContacts(1,6,8), new GregorianCalendar(2015, 2, 27), "Notes");
		cm.addFutureMeeting(cm.getContacts(5,6,7), new GregorianCalendar(2017, 11, 20));
		cm.addNewPastMeeting(cm.getContacts(2,4,7), new GregorianCalendar(2015, 5, 12), "Notes");
		cm.addFutureMeeting(cm.getContacts(1,3,4), new GregorianCalendar(2016, 9, 19));
		cm.addNewPastMeeting(cm.getContacts(1,2,4,7), new GregorianCalendar(2015, 5, 12), "Notes");
		cm.addFutureMeeting(cm.getContacts(1,3,4), new GregorianCalendar(2016, 9, 19));
	}
	
	@Test (expected = NullPointerException.class)
	public void testsGetMeetingListOnWithNullDateThrowsException() {
		cm.getMeetingListOn(null);
	}
	
	@Test
	public void testsGetMeetingListOnWithDateWithNoMeetings() {
		List<Meeting> output = cm.getMeetingListOn(new GregorianCalendar(2016, 7, 18));
		assertEquals(0, output.size());
	}
	
	@Test
	public void testsGetMeetingListOnWithDateWithDuplicateMeetings() {
		List<Meeting> output = cm.getMeetingListOn(new GregorianCalendar(2017, 11, 20));
		assertEquals(1, output.size());
	}
	
	@Test
	public void testsGetMeetingListOnWithDateWithMultipleMeetings() {
		List<Meeting> output = cm.getMeetingListOn(new GregorianCalendar(2015, 5, 12));
		assertEquals(2, output.size());
	}
	
	@Test (expected = NullPointerException.class)
	public void testsGetFutureMeetingListWithNullContactThrowsException() {
		cm.getFutureMeetingList(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsGetFutureMeetingListWithUnknownContactThrowsException() {
		Contact mysteryGuy = new ContactImpl(999, "Mr X", "Who is this?");
		cm.getFutureMeetingList(mysteryGuy);
	}
	
	@Test
	public void testsGetFutureMeetingListWithContactWithNoMeetings() {
		Object[] contacts = cm.getContacts(9).toArray();
		Contact contactToUse = (Contact) contacts[0];
		List<Meeting> output = cm.getFutureMeetingList(contactToUse);
		assertTrue(output.size() == 0);
	}
	
	@Test
	public void testsGetFutureMeetingListReturnsNoDuplicates() {
		Object[] contacts = cm.getContacts(7).toArray();
		Contact contactToUse = (Contact) contacts[0];
		List<Meeting> output = cm.getFutureMeetingList(contactToUse);
		assertTrue(output.size() == 2);
	}
	
	@Test
	public void testsGetFutureMeetingListReturnsMeetingsChronologically() {
		Object[] contacts = cm.getContacts(7).toArray();
		Contact contactToUse = (Contact) contacts[0];
		List<Meeting> output = cm.getFutureMeetingList(contactToUse);
		assertTrue(output.get(0).getDate().before(output.get(1).getDate()));
	}
}