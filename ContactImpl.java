import java.io.Serializable;

public class ContactImpl implements Contact, Serializable {
	private int id;
	private String name;
	private String notes;
	
	
	/**
	 * Constructs contact with id, name and notes.
	 *
	 * @param id Contact's ID number
	 * @param name Contact's name
	 * @param notes Notes about contact
	 *
	 * @throws IllegalArgumentException if id is negative or zero
	 * @throws NullPointerException if name or notes is null
	 */
	public ContactImpl(int id, String name, String notes) {
		ContactManagerUtils.checkIdAboveZero(id);
		ContactManagerUtils.checkParamsNotNull(name, notes);
		this.id = id;
		this.name = name;
		this.notes = notes;
	}
	
	/**
	 * Constructs contact with id and name, setting notes to empty string.
	 *
	 * @param id Contact's ID number
	 * @param name Contact's name
	 *
	 * @throws IllegalArgumentException if id is negative or zero
	 * @throws NullPointerException if name is null
	 */
	public ContactImpl(int id, String name) {
		ContactManagerUtils.checkIdAboveZero(id);
		ContactManagerUtils.checkParamsNotNull(name);
		this.id = id;
		this.name = name;
		this.notes = "";
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
}