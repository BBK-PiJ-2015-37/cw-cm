import java.util.Calendar;
import java.util.Set;

public abstract class MeetingImpl implements Meeting {
	private int id;
	private Calendar date;
	private Set<Contact> attendees;
	
	public MeetingImpl(int id, Calendar date, Set<Contact> attendees) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be positive and non-zero");
		}
		if (attendees.isEmpty()) {
			throw new IllegalArgumentException("List of attendees cannot be empty");
		}
		if (date.equals(null) || attendees.equals(null)) {
			throw new NullPointerException("Null parameters not permitted");
		}
		this.id = id;
		this.date = date;
		this.attendees = attendees;
	}
}