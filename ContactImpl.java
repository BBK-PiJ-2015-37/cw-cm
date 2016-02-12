public class ContactImpl implements Contact {
	private int id;
	private String name;
	private String notes;
	
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
}