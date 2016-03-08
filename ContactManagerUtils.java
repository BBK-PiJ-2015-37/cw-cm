/*
 * A class for several static methods useful in the implementation
 * of ContactManager and various related classes.
 *
 * @author Sam Jansen
 */

public class ContactManagerUtils {
	
	/*
	 * A static method that takes an arbitrary number of Objects
	 * and checks whether they are null. Throws a NullPointerException
	 * if this is so.
	 *
	 * @param params the Objects to check
	 */
	public static void checkParamsNotNull(Object... params) {
		for (Object o : params) {
			if (o.equals(null)) {
				throw new NullPointerException("Null parameters not permitted");
			}
		}
	}
	
	/*
	 * A static method that takes an id and checks that it is positive
	 * and non-zero. Throws an IllegalArgumentException if not.
	 *
	 * @param id the id number to check
	 */
	public static void checkIdAboveZero(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be positive and non-zero");
		}
	}
}