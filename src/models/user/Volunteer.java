package models.user;

public class Volunteer extends Customer {

  public Volunteer() {
    privileges = AuthorizationLevel.VOLUNTEER;
  }
}
