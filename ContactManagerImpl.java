import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Comparator;
import java.util.Collections;
import java.util.ListIterator;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * An implementation of the ContactManager interface
 *
 * @author Sam Jansen
 */

public class ContactManagerImpl implements ContactManager {
	private Set<Contact> contactList;
	private List<Meeting> meetingList;
	private Calendar currentDate;
	private final String FILENAME = "contacts.txt";
	
	/*
	 * Constructs a ContactManagerImpl instance with a list of contacts and
	 * a list of meetings. If a contacts.txt file is found in the same location
	 * as the code, the contacts and meetings lists will be populated with
	 * contacts and meetings read from this file. If no such file is found, empty
	 * contacts and meeting lists are created. This constructor also stores the
	 * current date, used to validate creation of past and future meetings.
	 */
	public ContactManagerImpl() {
		File storageFile = new File("." + File.separator + FILENAME);
		if (storageFile.exists()) {
			try (ObjectInputStream in = new ObjectInputStream(
			                           new BufferedInputStream(
			                             new FileInputStream(storageFile)))) {
				contactList = (Set<Contact>) in.readObject();
				meetingList = (List<Meeting>) in.readObject();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			contactList = new HashSet<>();
			meetingList = new ArrayList<>();
		}
		currentDate = new GregorianCalendar();
	}
	
	@Override
	/**
	 * @see ContactManager
	 */
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		ContactManagerUtils.checkParamsNotNull(contacts, date);
		validateContacts(contacts);
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
		ContactManagerUtils.checkParamsNotNull(contact);
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
		ContactManagerUtils.checkParamsNotNull(date);
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
		ContactManagerUtils.checkParamsNotNull(contact);
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
		Collections.sort(out, new PastDateComparator());
		return out;
	}
	
	@Override
	/**
	 * @see ContactManager
	 */
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		ContactManagerUtils.checkParamsNotNull(contacts, date);
		validateContacts(contacts);
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
		ContactManagerUtils.checkParamsNotNull(text);
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
		ContactManagerUtils.checkParamsNotNull(name, notes);
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
		ContactManagerUtils.checkParamsNotNull(name);
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
	
	@Override
	/**
	 * @see ContactManager
	 */
	public void flush() {
		File storageFile = new File("." + File.separator + FILENAME);
		if (!storageFile.exists()) {
			try {
				storageFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try (ObjectOutputStream out = new ObjectOutputStream(
			                           new BufferedOutputStream(
			                             new FileOutputStream(storageFile)))) {
			out.writeObject(contactList);
			out.writeObject(meetingList);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	 * that compares the IDs of two meetings at a time and removes duplicates as
	 * it finds them.
	 *
	 * @param list the list from which duplicates will be removed
	 */
	private void removeDuplicates(List<? extends Meeting> list) {
		for (int i = 0; i < list.size(); i++) {
			ListIterator<? extends Meeting> iterator = list.listIterator(i + 1);
			while (iterator.hasNext()) {
				Meeting m = iterator.next();
				int id1 = list.get(i).getId();
				int id2 = m.getId();
				if (id1 == id2) {
					iterator.remove();			
				}
			}
		}
	}
	
	/*
	 * Method that validates the set of contacts passed as a parameter when adding
	 * new meetings. It checks that there is at least one contact in the set and that
	 * all of the contacts in the set are in the ContactManager's list of known contacts.
	 *
	 * @param contacts list of contacts
	 * @throws IllegalArgumentException if there are no contacts in the set or if one of more
	 * of the contacts is unknown
	 */
	private void validateContacts(Set<Contact> contacts) {
		if (contacts.size() == 0) {
			throw new IllegalArgumentException("At least one attendee required");
		}
		if (!allContactsValid(contacts)) {
			throw new IllegalArgumentException("At least one attendee is unknown");
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
	
	class PastDateComparator implements Comparator<PastMeeting> {
		
		@Override
		/*
		 * A method to compare two past meetings by the date on which they
		 * took place.
		 *
		 * @param o1 the first Past Meeting
		 * @param o2 the second Past Meeting
		 * @return a negative integer, zero, or a positive integer
		 * according to whether the first Past Meeting is before, on the same
		 * date as, or after the second Past Meeting 
		 */
		public int compare(PastMeeting o1, PastMeeting o2) {
			return o1.getDate().compareTo(o2.getDate());
		}
	}
}