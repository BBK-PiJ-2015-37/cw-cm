import java.util.Calendar;
import java.util.Set;

/**
 * A mock class needed purely for testing the abstract MeetingImpl class
 *
 * @author Sam Jansen
 */

public class MockMeeting extends MeetingImpl {
	
	public MockMeeting(int id, Calendar date, Set<Contact> attendees) {
		super(id, date, attendees);
	}
	
	public int getId(){
		return super.getId();
	}
	
	public Calendar getDate() {
		return super.getDate();
	}
	
	public Set<Contact> getContacts() {
		return super.getContacts();
	}
}