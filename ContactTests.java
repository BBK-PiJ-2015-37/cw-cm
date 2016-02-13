import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContactTests {
	private Contact c1;
	
	@Test (expected = IllegalArgumentException.class)
	public void testsNegativeIDThrowsExceptionForFullConstructor() {
		c1 = new ContactImpl(-1, "John", "Likes cats");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsNegativeIDThrowsExceptionForRestrictedConstructor() {
		c1 = new ContactImpl(-1, "John");
	}
	
	@Test (expected = NullPointerException.class)
	public void testsNullNameThrowsExceptionForFullConstructor() {
		c1 = new ContactImpl(1, null, "Likes cats");
	}
	
	@Test (expected = NullPointerException.class)
	public void testsNullNameThrowsExceptionForRestrictedConstructor() {
		c1 = new ContactImpl(1, null);
	}
	
	@Test (expected = NullPointerException.class)
	public void testsNullNotesThrowsExceptionForFullConstructor() {
		c1 = new ContactImpl(1, "John", null);
	}
	
	@Test
	public void testsGetIdWithFullConstructor() {
		c1 = new ContactImpl(1, "John", "Likes cats");
		assertEquals(1, c1.getId());
	}
	
	@Test
	public void testsGetIdWithRestrictedConstructor() {
		c1 = new ContactImpl(1, "John");
		assertEquals(1, c1.getId());
	}
	
	@Test
	public void testsGetNameWithFullConstructor() {
		c1 = new ContactImpl(1, "John", "Likes cats");
		assertEquals("John", c1.getName());
	}
	
	@Test
	public void testsGetNameWithRestrictedConstructor() {
		c1 = new ContactImpl(1, "John");
		assertEquals("John", c1.getName());
	}
	
	@Test
	public void testsGetNameWithFullConstructorAndEmptyName() {
		c1 = new ContactImpl(1, "", "Likes cats");
		assertEquals("", c1.getName());
	}
	
	@Test
	public void testsGetNameWithRestrictedConstructorAndEmptyName() {
		c1 = new ContactImpl(1, "");
		assertEquals("", c1.getName());
	}
	
	@Test
	public void testsGetNotesWithFullConstructor() {
		c1 = new ContactImpl(1, "John", "Likes cats");
		assertEquals("Likes cats", c1.getNotes());
	}
	
	@Test
	public void testsGetNotesWithRestrictedConstructor() {
		c1 = new ContactImpl(1, "John");
		assertEquals("", c1.getNotes());
	}
	
	@Test
	public void testsGetNotesWithFullConstructorAndEmptyNotes() {
		c1 = new ContactImpl(1, "John", "");
		assertEquals("", c1.getNotes());
	}
	
	@Test
	public void testsAddNotesToEmptyNotesWithFullConstructor() {
		c1 = new ContactImpl(1, "John", "");
		c1.addNotes("Likes cats");
		assertEquals("Likes cats", c1.getNotes());
	}
	
	@Test
	public void testsAddNotesToEmptyNotesWithRestrictedConstructor() {
		c1 = new ContactImpl(1, "John");
		c1.addNotes("Likes cats");
		assertEquals("Likes cats", c1.getNotes());
	}
	
	@Test
	public void testsAddNotesToNonEmptyNotes() {
		c1 = new ContactImpl(1, "John", "Likes cats");
		c1.addNotes("Hates dogs");
		assertEquals("Likes cats\nHates dogs", c1.getNotes());
	}
}