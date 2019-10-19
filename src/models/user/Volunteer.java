package models.user;

public class Volunteer extends Customer {

  /**
   * Default constructor for Volunteer, which sets their privilege level to Volunteer.
   */
  public Volunteer() {
    privileges = AuthorizationLevel.VOLUNTEER;
  }
}
