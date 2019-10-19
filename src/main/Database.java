package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import models.adoption.AdoptionRequest;
import models.animal.Animal;
import models.questions.Question;
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
  private static User currentUser;
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
   * Attempts to login a user given a username and password. The result of this request also stores
   * the current user in the currentUser static variable for access anywhere.
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

        currentUser = user;
        return user;
      }

    } catch (SQLException e) {
      e.printStackTrace();
      currentUser = null;
      return null;
    }
    // Return null if we didn't find a user to return above.
    currentUser = null;
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

  /**
   * Method to check Employee Status.
   * @author Ramzy El-Taher
   * @param emp
   * @return
   */
  public boolean checkEmployee(Employee emp) {
    if (AuthorizationLevel.ADMINISTRATION==emp.getPrivileges()) {
      return true;
    }
    return false;
  }

  /**
   * Method which allows employees to edit an animal's information in the database.
   * @author Ramzy El-Taher
   * @return void
   */
  public void editAnimal(Animal animal) {
    String SQL = "UPDATE Animal SET name = ?, species = ?, description = ?,"
        + "gender = ?, colors = ?, adopted = ?, dateArrived = ?, dateAdopted = ?,"
        + "dateOfBirth = ?, serviceTrained = ?, weight = ?, height = ?, breeds = ?,"
        + "bathroomTraining = ?, aggression = ?";
    try {
      PreparedStatement ps = conn.prepareStatement(SQL);
      // Name
      ps.setString(1, animal.getName());
      // Species
      ps.setString(2, animal.getSpecies());
      // Description
      ps.setString(3, animal.getDescription());
      // Gender
      ps.setString(4, String.valueOf(animal.getGender()));
      // Color
      ps.setString(5, animal.getColorString());
      // If the animal was adopted
      ps.setBoolean(6, animal.isAdopted());
      // Date Arrived
      ps.setTimestamp(7, Timestamp.from(animal.getDateArrived()));
      // Date Adopted
      ps.setTimestamp(8, Timestamp.from(animal.getDateArrived()));
      // Date of Birth
      ps.setTimestamp(9, Timestamp.from(animal.getDateOfBirth()));
      // If animal is serviced trained
      ps.setBoolean(10, animal.isServiceTrained());
      // Weight
      ps.setFloat(11, animal.getWeight());
      // Height
      ps.setFloat(12, animal.getHeight());
      // Breed
      ps.setString(13,animal.getBreedString());
      // If animal is bathroom trained
      ps.setBoolean(14,animal.isServiceTrained());
      // Aggression level
      ps.setString(15,animal.getAggression().toString());
      //Execute Query
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Submits a question to the database.
   * @author Travis Gayle
   * @param question The question the user has asked
   * @return True if the question was successfully stored, else false.
   */
  public boolean askQuestion(Question question) {
    String query = "INSERT INTO Question (userId, answered, question) VALUES (?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setInt(1, question.getUserId());
      ps.setBoolean(2, false);
      ps.setString(3, question.getQuestion());
      ps.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace()
;
    }
    return false;
  }

  /**
   * Allows an employee to answer a question from a user.
   *
   * @author Travis Gayle
   * @param answerer The employee who asked the question
   * @param question The question being answered
   * @return True if this question was successfully answered, else false.
   */
  public boolean answerQuestion(User answerer, Question question) {
    if (answerer.getPrivileges() != AuthorizationLevel.ADMINISTRATION) {
      return false;
    }

    String query =
        "UPDATE Question "
        + "SET (employeeId, answered, answer) = (?, ?, ?)"
        + "WHERE Question.id=?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setInt(1, answerer.getId());
      ps.setBoolean(2, true);
      ps.setString(3, question.getAnswer());
      ps.setInt(4, question.getId());
      ps.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Submits an adoption request to be processed by the shelter.
   * @author Travis Gayle
   * @param request The adoption request to be processed
   * @return True of the request was successfully submitted, else false.
   */
  public boolean submitAdoptionRequest(AdoptionRequest request) {
    String query = "INSERT INTO AdoptionRequest "
        + "(customerId, animalId, approved, dateRequested) "
        + "VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setInt(1, request.getCustomerId());
      ps.setInt(2, request.getAnimalId());

      // A request that has just been submitted can't have been answered yet.
      ps.setBoolean(3, false);
      ps.setTimestamp(4, Timestamp.from(Instant.now()));
      ps.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Allows an employee to process an adoption request and approve or reject it.
   * @author Travis Gayle
   * @param user The user attempting to process this request.
   * @param request The request being processed
   * @return True if the processing was successful, else false.
   */
  public boolean processAdoptionRequest(User user, AdoptionRequest request) {
    // Make sure this user has permission to do this.
    if (user.getPrivileges() != AuthorizationLevel.ADMINISTRATION) {
      return false;
    }

    String query = "UPDATE AdoptionRequest "
        + "SET (approved, dateApproved) = (?, ?)"
        + "WHERE AdoptionRequest.id = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setBoolean(1, request.isApproved());
      ps.setTimestamp(2, Timestamp.from(request.getDateApproved()));
      ps.setInt(3, request.getId());
      ps.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Returns the current user logged in.
   * @author Travis Gayle
   * @return The current user or null if the user doesn't exist in the database.
   */
  public static User getCurrentUser() {
    return currentUser;
  }
}
