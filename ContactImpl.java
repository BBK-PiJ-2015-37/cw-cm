import java.io.Serializable;

/**
 * An implementation of the Contact interface
 *
 * @author Sam Jansen
 */

public class ContactImpl implements Contact, Serializable {
	private int id;
	private String name;
	private String notes;
	
	
	/**
	 * Constructs contact with id, name and notes.
	 */
	public ContactImpl(int id, String name, String notes) {
		constructNewContact(id, name, notes);
	}
	
	/**
	 * Constructs contact with id and name, setting notes to empty string.
	 */
	public ContactImpl(int id, String name) {
		constructNewContact(id, name, "");
	}
	
	@Override
	/**
	 * @see Contact
	 */
	public int getId() {
		return this.id;
	}
	
	@Override
	/**
	 * @see Contact
	 */
	public String getName() {
		return this.name;
	}
	
	@Override
	/**
	 * @see Contact
	 */
	public String getNotes() {
		return this.notes;
	}
	
	@Override
	/**
	 * @see Contact
	 */
	public void addNotes(String note) {
		if (getNotes().equals("")) {
			this.notes = note;
		} else {
			this.notes = this.notes.concat("\n" + note);
		}
	}
	
	/**
	 * Method acting as a general constructor for Contact, used to reduce
	 * repetition in official Contact constructors.
	 *
	 * @param id Contact's ID number
	 * @param name Contact's name
	 * @param notes Notes about contact
	 *
	 * @throws IllegalArgumentException if id is negative or zero
	 * @throws NullPointerException if name or notes is null
	 */
	private void constructNewContact(int id, String name, String notes) {
		ContactManagerUtils.checkIdAboveZero(id);
		ContactManagerUtils.checkParamsNotNull(name, notes);
		this.id = id;
		this.name = name;
		this.notes = notes;
	}
}