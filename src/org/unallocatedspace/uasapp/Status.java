/**
 * This class is a container which keeps other objects.
 */
public class Status {

/**
 * Creates member objects from the Info class and adds them to the array
 * members.
 */
  public Status() {
  }

/**
 * Refreshes all member objects.
 *
 * Calls Refresh() methods of all other objects to keep them up to date.
 * Exceptions are passed along. Each time the activity page awakens, it will
 * call this method to ensure freshness.
 * <p>
 * To test wether this succeeded cbeck the last update attribute of a member
 * object.
 */
  public void Refresh() {
  }
}
