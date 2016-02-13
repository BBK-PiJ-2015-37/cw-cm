import java.util.Calendar;
import java.util.Set;

public class MockMeeting extends MeetingImpl {
	
	public MockMeeting(int id, Calendar date, Set<Contact> attendees) {
		super(id, date, attendees);
	}
}