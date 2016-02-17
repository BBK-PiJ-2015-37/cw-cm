import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class ContactManagerTests {
	ContactManager cm;
	
	@Before
	public void setUp() {
		cm = new ContactManagerImpl();
	}
	
	@Test
	public void testsAddNewContactToEmptyContactSet() {
		int outcome = cm.addNewContact("John", "Likes cats");
		assertEquals(1, outcome);
	}
	
	@Test
	public void testsAddNewContactToContactSetWithOneContact() {
		cm.addNewContact("John", "Likes cats");
		int outcome = cm.addNewContact("Carol", "Good at business");
		assertEquals(2, outcome);
	}
	
	@Test
	public void testsAddNewContactToContactSetWithMultipleContacts() {
		cm.addNewContact("John", "Likes cats");
		cm.addNewContact("Carol", "Good at business");
		cm.addNewContact("Derek", "Never wears a tie");
		cm.addNewContact("Tim", "Nice but dim");
		int outcome = cm.addNewContact("Angela", "Negotiates hard");
		assertEquals(5, outcome);
	}
	
}