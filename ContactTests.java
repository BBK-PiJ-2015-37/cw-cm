import org.junit.Test;

import static org.junit.Assert.assertNotNull;

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
}