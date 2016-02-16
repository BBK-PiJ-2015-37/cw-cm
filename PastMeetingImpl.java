import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
	private String notes;
	
	/**
	 * Constructs past meeting with id, date, a set of attendees and notes
	 * using constructor of MeetingImpl superclass.
	 *
	 * @param id Meeting's ID number
	 * @param date Date of meeting
	 * @param attendees Set of meeting's attendees
	 * @param notes Notes about meeting
	 *
	 * @throws IllegalArgumentException if id is negative or zero
	 *		   or if the set of attendees is empty
	 * @throws NullPointerException if date, set of attendees or notes is null
	 */
	public PastMeetingImpl(int id, Calendar date, Set<Contact> attendees, String notes) {
		super(id, date, attendees);
		if (notes.equals(null)) {
			throw new NullPointerException("Null parameters not permitted");
		}
		this.notes = notes;
	}
}