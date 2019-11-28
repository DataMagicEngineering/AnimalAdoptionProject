package models.user;

/**
 * The base version of a user. Allowed to submit adoption requests.
 */
public class Customer extends User {
  protected boolean isVolunteer;

  /**
   * The Default Constructor which sets a Customer instance's privileges to BASIC by default.
   */
  public Customer() {
    privileges = AuthorizationLevel.BASIC;
  }
}
