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

  /**
   * Accessor for id
   * @return id, an int field in the User class.
   */
  public int getId() {
    return id;
  }

  /**
   * Mutator for id
   * @param id the int variable that the id field is assigned to.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Accessor for username
   * @return username, a String field in the User class.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Mutator for username
   * @param username the String variable that the username field is assigned to.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Accessor for password
   * @return password, a String field in the User class.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Mutator for password
   * @param password the String variable that the password field is assigned to.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Accessor for firstName
   * @return firstName, a String field in the User class.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Mutator for firstName
   * @param firstName the String variable that the firstName field is assigned to.
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Accessor for lastName
   * @return lastName, a String field in the User class.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Mutator for lastName
   * @param lastName the String variable that the lastName field is assigned to.
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Accessor for dateOfBirth
   * @return dateOfBirth, an Instant field in the User class.
   */
  public Instant getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * Mutator for dateOfBirth
   * @param dateOfBirth the Instant variable that the dateOfBirth field is assigned to.
   */
  public void setDateOfBirth(Instant dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  /**
   * Accessor for privileges
   * @return privileges, an AuthorizationLevel enum field in the User class.
   */
  public AuthorizationLevel getPrivileges() {
    return privileges;
  }

  /**
   * Mutator for privileges
   * @param privileges the AuthorizationLevel enum variable that the privileges field is assigned to.
   */
  public void setPrivileges(AuthorizationLevel privileges) {
    this.privileges = privileges;
  }
}
