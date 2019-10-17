package models.user;

import java.time.Instant;

/**
 * Base class for a general User.
 */
public abstract class User {
  protected int id;
  protected String username;
  protected String password;
  protected String firstName;
  protected String lastName;
  protected Instant dateOfBirth;
  protected AuthorizationLevel privileges;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Instant getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Instant dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public AuthorizationLevel getPrivileges() {
    return privileges;
  }

  public void setPrivileges(AuthorizationLevel privileges) {
    this.privileges = privileges;
  }
}
