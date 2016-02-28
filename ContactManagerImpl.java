import java.util.Calendar;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class ContactManagerImpl implements ContactManager {
	private Set<Contact> contactList;
	
	public ContactManagerImpl() {
		contactList = new HashSet<>();
	}
	
	@Override
	/**
	 * @see ContactManager
	 */
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		return -999;
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
}