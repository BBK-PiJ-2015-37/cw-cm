import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class ContactManagerImpl implements ContactManager {
	private Set<Contact> contactList;
	private List<Meeting> meetingList;
	private Calendar currentDate;
	
	public ContactManagerImpl() {
		contactList = new HashSet<>();
		meetingList = new ArrayList<>();
		currentDate = new GregorianCalendar();
	}
	
	@Override
	/**
	 * @see ContactManager
	 */
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		if (contacts.equals(null) || date.equals(null)) {
			throw new NullPointerException("Null parameters not permitted");
		}
		if (contacts.size() == 0) {
			throw new IllegalArgumentException("At least one attendee required");
		}
		if (!allContactsValid(contacts)) {
			throw new IllegalArgumentException("At least one attendee is unknown");
		}
		if (date.before(currentDate)) {
			throw new IllegalArgumentException("Date provided is in the past");
		}
		int newMeetingId = meetingList.size() + 1;
		FutureMeeting meetingToAdd = new FutureMeetingImpl(newMeetingId, date, contacts);
		meetingList.add(meetingToAdd);
		return newMeetingId;
	}
	
	@Override
	/**
	 * @see ContactManager
	 */
	public FutureMeeting getFutureMeeting(int id) {
		return null;
	}
	
	@Override
	/**
	 * @see ContactManager
	 */
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		
	}
	
	@Override
	/**
	 * @see ContactManager
	 */
	public int addNewContact(String name, String notes) {
		if (name.equals(null) || notes.equals(null)) {
			throw new NullPointerException("Null parameters not permitted");
		}
		if (name.equals("") || notes.equals("")) {
			throw new IllegalArgumentException("Empty strings not permitted");
		}
		int newContactId = contactList.size() + 1;
		Contact newContact = new ContactImpl(newContactId, name, notes);
		contactList.add(newContact);
		return newContactId;
	}
	
	@Override
	/**
	 * @see ContactManager
	 */
	public Set<Contact> getContacts(String name) {
		if (name.equals(null)) {
			throw new NullPointerException("Null parameters not permitted");
		}
		if (name.equals("")) {
			return this.contactList;
		} else {
			Set<Contact> result = new HashSet<>();
			for (Contact c : this.contactList) {
				if (c.getName().contains(name)) {
					result.add(c);
				}
			}
			return result;
		}
	}
	
	@Override
	/**
	 * @see ContactManager
	 */
	public Set<Contact> getContacts(int... ids) {
		if (ids.length == 0) {
			throw new IllegalArgumentException("Enter at least one Contact ID");
		}
		Set<Contact> result = new HashSet<>();
		for (int id : ids) {
			if (id < 1 || id > this.contactList.size()) {
				throw new IllegalArgumentException(id + " is not a known Contact ID");
			}
			for (Contact c : this.contactList) {
				if (c.getId() == id) {
					result.add(c);
				}
			}
		}
		return result;
		
	}
	
	/*
	 * Loops through elements in a given set of contacts and checks
	 * that Contact Manager's list of contacts contains all of them.
	 *
	 * @param contacts list of contacts
	 * @return true if all contacts in contact list, false otherwise
	 */
	private boolean allContactsValid(Set<Contact> contacts) {
		for (Contact c : contacts) {
			if (!contactList.contains(c)) {
				return false;
			}
		}
		return true;
	}
}