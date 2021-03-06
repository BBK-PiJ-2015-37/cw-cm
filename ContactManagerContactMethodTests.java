import java.util.Set;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A set of tests for the following methods in the ContactManagerImpl class:
 *
 * - addNewContact();
 * - getContacts(String name);
 * - getContacts(int... ids);
 *
 * @author Sam Jansen
 */

public class ContactManagerContactMethodTests {
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
	
	@Test (expected = IllegalArgumentException.class)
	public void testsAddNewContactWithEmptyNameThrowsException() {
		cm.addNewContact("", "Likes cats");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsAddNewContactWithEmptyNotesThrowsException() {
		cm.addNewContact("John", "");
	}
	
	@Test (expected = NullPointerException.class)
	public void testsAddNewContactWithNullNameThrowsException() {
		cm.addNewContact(null, "Likes cats");
	}
	
	@Test (expected = NullPointerException.class)
	public void testsAddNewContactWithNullNotesThrowsException() {
		cm.addNewContact("John", null);
	}
	
	@Test (expected = NullPointerException.class)
	public void testsGetContactsWithNullStringThrowsException() {
		String contactName = null;
		cm.getContacts(contactName);
	}
	
	@Test
	public void testsGetContactsWithEmptyStringReturnsFullContactSet() {
		cm.addNewContact("John", "Likes cats");
		cm.addNewContact("Carol", "Good at business");
		cm.addNewContact("Derek", "Never wears a tie");
		Set<Contact> output = cm.getContacts("");
		assertTrue(output.size() == 3);
	}
	
	@Test
	public void testsGetContactsWithStringOnEmptyContactSetReturnsEmptySet() {
		Set<Contact> output = cm.getContacts("John");
		assertTrue(output.isEmpty());
	}
	
	@Test
	public void testsGetContactsWithEmptyStringOnEmptyContactSetReturnsEmptySet() {
		Set<Contact> output = cm.getContacts("");
		assertTrue(output.isEmpty());
	}
	
	@Test
	public void testsGetContactsWithStringReturnsEmptySetWhenNoMatches() {
		cm.addNewContact("John", "Likes cats");
		cm.addNewContact("Carol", "Good at business");
		cm.addNewContact("Derek", "Never wears a tie");
		Set<Contact> output = cm.getContacts("Francine");
		assertTrue(output.isEmpty());
	}
	
	@Test
	public void testsGetContactsWithStringReturnsSetWithOneItemWhenOneMatch() {
		cm.addNewContact("John", "Likes cats");
		cm.addNewContact("Carol", "Good at business");
		cm.addNewContact("Derek", "Never wears a tie");
		Set<Contact> output = cm.getContacts("John");
		assertTrue(output.size() == 1);
	}
	
	@Test
	public void testsGetContactsWithStringReturnsSetWithTwoItemsWhenTwoMatches() {
		cm.addNewContact("John A", "Likes cats");
		cm.addNewContact("John B", "Good at business");
		cm.addNewContact("Derek", "Never wears a tie");
		Set<Contact> output = cm.getContacts("John");
		assertTrue(output.size() == 2);
	}
	
	@Test
	public void testsGetContactsWithStringIsCaseSensitive() {
		cm.addNewContact("John", "Likes cats");
		cm.addNewContact("Carol Notjohn", "Good at business");
		cm.addNewContact("Derek", "Never wears a tie");
		Set<Contact> output = cm.getContacts("John");
		assertTrue(output.size() == 1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testsGetContactsWithNoIdsThrowsException() {
		cm.addNewContact("John", "Likes cats");
		cm.getContacts();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsGetContactsWithUnknownIdThrowsException() {
		cm.addNewContact("John", "Likes cats");
		cm.getContacts(2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsGetContactsWithNegativeIdThrowsException() {
		cm.addNewContact("John", "Likes cats");
		cm.getContacts(-2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsGetContactsWithUnknownIdAndEmptyContactSetThrowsException() {
		cm.getContacts(1);
	}
	
	@Test
	public void testsGetContactsWithOneIdAndOneItemInContactSet() {
		cm.addNewContact("John", "Likes cats");
		Set<Contact> output = cm.getContacts(1);
		assertTrue(output.size() == 1);
	}
	
	@Test
	public void testsGetContactsWithTwoIdsAndTwoItemsInContactSet() {
		cm.addNewContact("John", "Likes cats");
		cm.addNewContact("Carol", "Good at business");
		Set<Contact> output = cm.getContacts(1, 2);
		assertTrue(output.size() == 2);
	}
	
	@Test
	public void testsGetContactsWithFourIdsAndFourItemsInContactSet() {
		cm.addNewContact("John", "Likes cats");
		cm.addNewContact("Carol", "Good at business");
		cm.addNewContact("Derek", "Never wears a tie");
		cm.addNewContact("Tim", "Nice but dim");
		Set<Contact> output = cm.getContacts(1, 2, 3, 4);
		assertTrue(output.size() == 4);
	}
	
	@Test
	public void testsGetContactsWithOneIdAndFourItemsInContactSet() {
		cm.addNewContact("John", "Likes cats");
		cm.addNewContact("Carol", "Good at business");
		cm.addNewContact("Derek", "Never wears a tie");
		cm.addNewContact("Tim", "Nice but dim");
		Set<Contact> output = cm.getContacts(2);
		assertTrue(output.size() == 1);
	}
	
	@Test
	public void testsGetContactsWithThreeIdsAndFourItemsInContactSet() {
		cm.addNewContact("John", "Likes cats");
		cm.addNewContact("Carol", "Good at business");
		cm.addNewContact("Derek", "Never wears a tie");
		cm.addNewContact("Tim", "Nice but dim");
		Set<Contact> output = cm.getContacts(2, 3, 4);
		assertTrue(output.size() == 3);
	}
	
	@Test
	public void testsGetContactsWithThreeIdsOutOfOrderAndFourItemsInContactSet() {
		cm.addNewContact("John", "Likes cats");
		cm.addNewContact("Carol", "Good at business");
		cm.addNewContact("Derek", "Never wears a tie");
		cm.addNewContact("Tim", "Nice but dim");
		Set<Contact> output = cm.getContacts(4, 1, 3);
		assertTrue(output.size() == 3);
	}
}