import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Comparator;
import java.util.Collections;
import java.util.ListIterator;

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
	public PastMeeting getPastMeeting(int id) {
		Meeting retrieved = getMeeting(id);
		if (retrieved instanceof FutureMeeting) {
			throw new IllegalStateException("Meeting with that id found in the future");
		}
		return (PastMeeting) retrieved;
	}
	
	@Override
	/**
	 * @see ContactManager
	 */
	public FutureMeeting getFutureMeeting(int id) {
		Meeting retrieved = getMeeting(id);
		if (retrieved instanceof PastMeeting) {
			throw new IllegalArgumentException("Meeting with that id found in the past");
		}
		return (FutureMeeting) retrieved;
	}
	
	@Override
	/**
	 * @see ContactManager
	 */
	public Meeting getMeeting(int id) {
		Meeting result = null;
		if (id > 0 && id <= meetingList.size()) {
			result = meetingList.get(id - 1);
		}
		return result;
	}
	
	@Override
	/**
	 * @see ContactManager
	 */
	public List<Meeting> getFutureMeetingList(Contact contact) {
		if (contact.equals(null)) {
			throw new NullPointerException("Null parameters not permitted");
		}
		if (!(contactList.contains(contact))) {
			throw new IllegalArgumentException("No such contact known");
		}
		List<Meeting> out = new ArrayList<>();
		for (Meeting m : meetingList) {
			if (m instanceof FutureMeeting) {
				Set<Contact> mContacts = m.getContacts();
				if (mContacts.contains(contact)) {
					out.add(m);
				}
			}
		}
		removeDuplicates(out);
		Collections.sort(out, new DateComparator());
		return out;
	}
	
	@Override
	/**
	 * @see ContactManager
	 */
	public List<Meeting> getMeetingListOn(Calendar date) {
		if (date.equals(null)) {
			throw new NullPointerException("Null parameters not permitted");
		}
		List<Meeting> out = new ArrayList<>();
		for (Meeting m : meetingList) {
			Calendar meetingDate = m.getDate();
			if ((meetingDate.get(Calendar.YEAR) == date.get(Calendar.YEAR))
					&& (meetingDate.get(Calendar.MONTH) == date.get(Calendar.MONTH))
						&& (meetingDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH))) {
				out.add(m);	
			}
		}
		removeDuplicates(out);
		Collections.sort(out, new DateComparator());
		return out;
	}
	
	@Override
	/**
	 * @see ContactManager
	 */
	public List<PastMeeting> getPastMeetingListFor(Contact contact) {
		if (contact.equals(null)) {
			throw new NullPointerException("Null parameters not permitted");
		}
		if (!(contactList.contains(contact))) {
			throw new IllegalArgumentException("No such contact known");
		}
		List<PastMeeting> out = new ArrayList<>();
		for (Meeting m : meetingList) {
			if (m instanceof PastMeeting) {
				Set<Contact> mContacts = m.getContacts();
				if (mContacts.contains(contact)) {
					PastMeeting pm = (PastMeeting) m;
					out.add(pm);
				}
			}
		}
		removeDuplicates(out);
		Collections.sort(out, new DateComparator());
		return out;
	}
	
	@Override
	/**
	 * @see ContactManager
	 */
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		if (contacts.equals(null) || date.equals(null)) {
			throw new NullPointerException("Null parameters not permitted");
		}
		if (contacts.size() == 0) {
			throw new IllegalArgumentException("At least one attendee required");
		}
		if (!allContactsValid(contacts)) {
			throw new IllegalArgumentException("At least one attendee is unknown");
		}
		if (date.after(currentDate)) {
			throw new IllegalArgumentException("Date provided is in the future");
		}
		int newMeetingId = meetingList.size() + 1;
		PastMeeting meetingToAdd = new PastMeetingImpl(newMeetingId, date, contacts, text);
		meetingList.add(meetingToAdd);
	}
	
	@Override
	/**
	 * @see ContactManager
	 */
	public PastMeeting addMeetingNotes(int id, String text) {
		if (text.equals(null)) {
			throw new NullPointerException("Null parameters not permitted");
		}
		if ((id < 1 || id > meetingList.size())) {
			throw new IllegalArgumentException("No meeting with that id found");
		}
		Meeting meeting = getMeeting(id);
		PastMeeting out = null;
		if (meeting instanceof FutureMeeting) {
			if (meeting.getDate().after(currentDate)) {
				throw new IllegalStateException("Meeting with that id found in the future");
			} else {
				out = new PastMeetingImpl(id, meeting.getDate(), meeting.getContacts(), text);
			}
		} else {
			PastMeeting past = (PastMeeting) meeting;
			out = new PastMeetingImpl(id, past.getDate(), past.getContacts(),
										past.getNotes().concat("\n" + text));
		}
		meetingList.set(id - 1, out);
		return out;
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
	
	/*
	 * A method that sets the Contact Manager's current date to that
	 * given. Method required purely for testing purposes. 
	 */
	public void setCurrentDate(Calendar date) {
		currentDate = date;
	}
	
	/*
	 * A method that removes duplicates from a list by creating a ListIterator
	 * that compares the dates and contact sets of two meetings at a time and
	 * removes duplicates as it finds them.
	 *
	 * @param list the list from which duplicates will be removed
	 */
	private void removeDuplicates(List<? extends Meeting> list) {
		for (int i = 0; i < list.size(); i++) {
			ListIterator<? extends Meeting> iterator = list.listIterator(i + 1);
			while (iterator.hasNext()) {
				Meeting m = iterator.next();
				Set<Contact> contacts1 = list.get(i).getContacts();
				Calendar date1 = list.get(i).getDate();
				Set<Contact> contacts2 = m.getContacts();
				Calendar date2 = m.getDate();
				if ((date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR))
					&& (date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH))
						&& (date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH))
							&& contacts1.size() == contacts2.size() && contacts1.containsAll(contacts2)) {
					iterator.remove();			
				}
			}
		}
	}
	
	class DateComparator implements Comparator<Meeting> {
		
		@Override
		/*
		 * A method to compare two meetings by the date on which they
		 * take place.
		 *
		 * @param o1 the first Meeting
		 * @param o2 the second Meeting
		 * @return a negative integer, zero, or a positive integer
		 * according to whether the first Meeting is before, on the same
		 * date as, or after the second Meeting 
		 */
		public int compare(Meeting o1, Meeting o2) {
			return o1.getDate().compareTo(o2.getDate());
		}
	}
}