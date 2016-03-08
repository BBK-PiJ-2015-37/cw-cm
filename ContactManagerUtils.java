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
}