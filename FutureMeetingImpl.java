import java.util.Calendar;
import java.util.Set;

/**
 * An implementation of the FutureMeeting interface
 *
 * @author Sam Jansen
 */

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {
	
	/**
	 * Constructs future meeting with id, date and a set of attendees
	 * using constructor of MeetingImpl superclass.
	 *
	 * @param id Meeting's ID number
	 * @param date Date of meeting
	 * @param attendees Set of meeting's attendees
	 *
	 * @throws IllegalArgumentException if id is negative or zero
	 *		   or if the set of attendees is empty
	 * @throws NullPointerException if date or set of attendees is null
	 */
	public FutureMeetingImpl(int id, Calendar date, Set<Contact> attendees) {
		super(id, date, attendees);
	}
}