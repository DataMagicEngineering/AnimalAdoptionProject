package main;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import models.animal.Animal;
import models.user.AuthorizationLevel;
import models.user.Customer;
import models.user.Employee;
import models.user.User;
import models.user.Volunteer;

/**
 * Wrapper around the database to facilitate requests. Commands and actions relating to the database
 * should go through here.
 */
public class Database {
  private static Database thisDb;

  private Connection conn;

  private Database() {
    try {
      conn = DriverManager.getConnection("jdbc:sqlite:./res/shelter.db");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("There was an issue connecting to the database.");
    }
  }

  public static Database get() {
    if (thisDb == null) {
      thisDb = new Database();
    }
    return thisDb;
  }

  /**
   * Tries to create a new user in the database.
   * @param user A user that should be stored.
   * @return true if adding the user was successful, otherwise false.
   */
  public boolean signUpUser(User user) {
    String query = "INSERT INTO User ("
        + "username, password, firstName, lastName, dateOfBirth, privilege) "
        + "VALUES (?, ?, ?, ?, ?, ?)";
    try (PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setString(1, user.getUsername());
      statement.setString(2, user.getPassword());
      statement.setString(3, user.getFirstName());
      statement.setString(4, user.getLastName());
      statement.setTimestamp(5, Timestamp.from(user.getDateOfBirth()));
      statement.setInt(6, user.getPrivileges().ordinal());

      statement.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Attempts to login a user given a username and password.
   * @param username The username to check against the database.
   * @param password The password to check against the database.
   * @return The user that corresponds to this username and password, otherwise null if the
   * given username and password don't match with a user.
   */
  public User loginUser(String username, String password) {
    String findUserQuery =
        "SELECT id, username, password, "
          + "firstName, lastName, dateOfBirth, privilege "
          + "FROM User WHERE username=? AND password=? LIMIT 1";

    try (PreparedStatement statement = conn.prepareStatement(findUserQuery)) {
      statement.setString(1, username);
      statement.setString(2, password);
      ResultSet resultSet = statement.executeQuery();

      // If there is at least one row in this result set, then we know that this username and
      // password goes with a user.
      if (resultSet.next()) {
        int userId = resultSet.getInt(1);
        String thisUserName = resultSet.getString(2);
        String thisPassword = resultSet.getString(3);
        String firstName = resultSet.getString(4);
        String lastName = resultSet.getString(5);
        Instant dateOfBirth = resultSet.getTimestamp(6).toInstant();

        int authorizationLevelOrdinal = resultSet.getInt(7);

        // Declare our user first so that we can assign it to the right type of user later.
        User user;

        if (authorizationLevelOrdinal == AuthorizationLevel.ADMINISTRATION.ordinal()) {
          user = new Employee();
        } else if (authorizationLevelOrdinal == AuthorizationLevel.VOLUNTEER.ordinal()) {
          user = new Volunteer();
        } else {
          user = new Customer();
        }

        user.setId(userId);
        user.setUsername(thisUserName);
        user.setPassword(thisPassword);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);

        return user;
      }

    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    // Return null if we didn't find a user to return above.
    return null;
  }

  /**
   * Attempts to add new animal to DB
   *
   */
  public void addAnimal(Animal animal){

    // pre-making animal object that will be used to store info into db
    String query = "INSERT INTO Animal ("
        + "name, species, description, gender, colors, adopted, dateArrived, dateAdopted,"
        + " dateOfBirth, serviceTrained, weight, height, breeds, bathroomTraining, aggression) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setString(1, animal.getName());
      statement.setString(2, animal.getSpecies());
      statement.setString(3, animal.getDescription());
      statement.setString(4, String.valueOf(animal.getGender()));
      statement.setString(5, animal.getColorString());
      statement.setBoolean(6,animal.isAdopted());
      statement.setTimestamp(7, Timestamp.from(animal.getDateArrived()));
      statement.setTimestamp(8, Timestamp.from(animal.getDateAdopted()));
      statement.setTimestamp(9, Timestamp.from(animal.getDateOfBirth()));
      statement.setBoolean(10, animal.isServiceTrained());
      statement.setFloat(11, animal.getWeight());
      statement.setFloat(12, animal.getHeight());
      statement.setString(13, animal.getBreedString());
      statement.setString(14, animal.getBathroomTraining().toString());
      statement.setString(15, animal.getAggression().toString());

      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

}
