import java.util.GregorianCalendar;
import org.junit.Before;

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
}