import java.util.Calendar;
import java.util.Set;
import java.io.Serializable;

public abstract class MeetingImpl implements Meeting, Serializable {
	private int id;
	private Calendar date;
	private Set<Contact> attendees;
	
	/**
	 * Constructs meeting with id, date and a set of attendees.
	 *
	 * @param id Meeting's ID number
	 * @param date Date of meeting
	 * @param attendees Set of meeting's attendees
	 *
	 * @throws IllegalArgumentException if id is negative or zero
	 *		   or if the set of attendees is empty
	 * @throws NullPointerException if date or set of attendees is null
	 */
	public MeetingImpl(int id, Calendar date, Set<Contact> attendees) {
		ContactManagerUtils.checkIdAboveZero(id);
		if (attendees.isEmpty()) {
			throw new IllegalArgumentException("List of attendees cannot be empty");
		}
		ContactManagerUtils.checkParamsNotNull(date, attendees);
		this.id = id;
		this.date = date;
		this.attendees = attendees;
	}
	
	@Override
	/**
	 * @see Meeting
	 */
	public int getId(){
		return this.id;
	}
	
	@Override
	/**
	 * @see Meeting
	 */
	public Calendar getDate() {
		return this.date;
	}
	
	@Override
	/**
	 * @see Meeting
	 */
	public Set<Contact> getContacts() {
		return this.attendees;
	}
}