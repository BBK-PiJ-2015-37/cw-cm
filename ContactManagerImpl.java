import java.util.Calendar;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class ContactManagerImpl implements ContactManager {
	private Set<Contact> contactList;
	
	public ContactManagerImpl() {
		contactList = new HashSet<>();
	}
	
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
}