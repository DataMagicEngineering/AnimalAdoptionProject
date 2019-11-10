package models.user;

/**
 * The base version of a user. Allowed to submit adoption requests.
 */
public class Customer extends User {
  protected boolean isVolunteer;

  public Customer() {
    privileges = AuthorizationLevel.BASIC;
  }
}
