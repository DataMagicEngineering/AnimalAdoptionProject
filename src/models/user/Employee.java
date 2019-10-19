package models.user;

public class Employee extends User {
  private int salary;

  /**
   * Default Constructor for an Employee object, which sets their privilege level to administration.
   */
  public Employee() {

    privileges = AuthorizationLevel.ADMINISTRATION;
  }

}
