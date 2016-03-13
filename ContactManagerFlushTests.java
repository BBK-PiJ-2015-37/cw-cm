import java.util.GregorianCalendar;
import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A set of tests for the flush() method in the ContactManagerImpl class
 *
 * @author Sam Jansen
 */

public class ContactManagerFlushTests {
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
		cm.addNewPastMeeting(cm.getContacts(2,4,7), new GregorianCalendar(2015, 5, 12), "Notes");
	}
	
	@After
	public void clear() {
		File storageFile = new File("." + File.separator + "contacts.txt");
		if(storageFile.exists()) {
			storageFile.delete();
		}	
	}
	
	@Test
	public void testFlushCreatesStorageFile() {
		cm.flush();
		assertTrue(new File("." + File.separator + "contacts.txt").exists());
	}
	
	@Test
	public void testFlushSavesMeetingsCorrectly() {
		cm.flush();
		ContactManager cm2 = new ContactManagerImpl();
		assertEquals(new GregorianCalendar(2015, 5, 12), cm2.getMeeting(10).getDate());
	}
	
	@Test
	public void testFlushSavesContactsCorrectly() {
		cm.flush();
		ContactManager cm2 = new ContactManagerImpl();
		Object[] contacts = cm2.getContacts(9).toArray();
		Contact contactToUse = (Contact) contacts[0];
		assertEquals("Gary", contactToUse.getName());
	}
	
	@Test
	public void testFlushOverwritesOldStorageFileWhenNewContactAdded() {
		cm.flush();
		ContactManager cm2 = new ContactManagerImpl();
		cm2.addNewContact("Mary", "Notes");
		cm2.flush();
		ContactManager cm3 = new ContactManagerImpl();
		Object[] contacts = cm3.getContacts(10).toArray();
		Contact contactToUse = (Contact) contacts[0];
		assertEquals("Mary", contactToUse.getName());
	}
	
	@Test
	public void testFlushOverwritesOldStorageFileWhenNewMeetingAdded() {
		cm.flush();
		ContactManager cm2 = new ContactManagerImpl();
		cm2.addFutureMeeting(cm2.getContacts(2,6,7), new GregorianCalendar(2016, 12, 12));
		cm2.flush();
		ContactManager cm3 = new ContactManagerImpl();
		assertEquals(new GregorianCalendar(2016, 12, 12), cm3.getMeeting(11).getDate());
	}
}