/**
 * Contains information gathered from the UAS website.
 */
public class Info {

/**
 * Sets urlFeed and calls refresh().
 */
  public Info() {
  }

/**
 * Fetches updates from UAS.
 *
 * Calls HttpUrlConnection using the value in urlFeed.
 * <p>
 * If the resource is available this methods updates message and lastUpdate.
 * Then updates the cache with these values. Updates status with a statement
 * that the recent connection succeeded.
 * <p>
 * If the network or the resource is unavailable then this method pulls the
 * values from the cache, or set the value with "Err: NoCache" if there is not
 * a previous update. Updates status with a timestamp of last attempt an a
 * statement of the failure.
 * <p>
 * Test by checking message. lastUpdate, and status.
 */
  public void refresh() {
  }

/**
 * Used for sending updates to UAS.
 * 
 * Calls HttpUrlConnection using the value in urlFeed, the method provided,
 * and the values in argvalpairs.
 * <p>
 * This methods passes any exceptions.
 * <p>
 * Test for exceptions.
 */
  public void post() {
  }
}
