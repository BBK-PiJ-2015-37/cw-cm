public class ContactImpl implements Contact {
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
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be positive and non-zero");
		}
		if (name.equals(null) || notes.equals(null)) {
			throw new NullPointerException("Null parameters not permitted");
		}
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
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be positive and non-zero");
		}
		if (name.equals(null)) {
			throw new NullPointerException("Null parameters not permitted");
		}
		this.id = id;
		this.name = name;
		this.notes = "";
	}
	
	@Override
	/**
	 * @see Contact
	 */
	public int getId() {
		return -999;
	}
	
	@Override
	/**
	 * @see Contact
	 */
	public String getName() {
		return null;
	}
	
	@Override
	/**
	 * @see Contact
	 */
	public String getNotes() {
		return null;
	}
}