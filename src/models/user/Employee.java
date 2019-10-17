package models.user;

public class Employee extends User {
  private int salary;

  public Employee() {
    privileges = AuthorizationLevel.ADMINISTRATION;
  }
}
