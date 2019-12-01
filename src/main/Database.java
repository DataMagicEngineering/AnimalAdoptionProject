package main;

import static models.animal.Color.Black;
import static models.animal.Color.Blue;
import static models.animal.Color.Brown;
import static models.animal.Color.Gray;
import static models.animal.Color.Green;
import static models.animal.Color.Orange;
import static models.animal.Color.Red;
import static models.animal.Color.White;
import static models.animal.Color.Yellow;
import static models.animal.Proficiency.Awful;
import static models.animal.Proficiency.Bad;
import static models.animal.Proficiency.Excellent;
import static models.animal.Proficiency.Good;
import static models.animal.Proficiency.Great;
import static models.animal.Proficiency.Neutral;
import static models.animal.Proficiency.None;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import models.adoption.AdoptionRequest;
import models.adoption.AdoptionWithAnimal;
import models.animal.Animal;
import models.animal.Color;
import models.animal.Proficiency;
import models.application.VolunteerApplication;
import models.application.VolunteerApplicationWithUser;
import models.event.Event;
import models.logging.LogEntry;
import models.logging.LogEntry.LogEntity;
import models.logging.LogEntryWithUser;
import models.questions.Question;
import models.questions.QuestionWithUser;
import models.user.AuthorizationLevel;
import models.user.Customer;
import models.user.Employee;
import models.user.User;
import models.user.Volunteer;

/**
 * The Database class is a wrapper around the database to facilitate requests. Commands and actions
 * relating to the database should go through here.
 *
 * @author The Data Magic Engineering Team
 */
public class Database {

  private static Database thisDb;

  private static User currentUser;
  private static Animal currentAnimal;
  private static Event currentEvent;

  private Connection conn;

  /**
   * The Default Constructor which initializes the Database.
   */
  private Database() {
    try {
      conn = DriverManager.getConnection("jdbc:sqlite:./res/shelter.db");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("There was an issue connecting to the database.");
    }
  }

  /**
   * A method that returns a new Database if none are currently available.
   * @return thisDb, which is a Database object.
   */
  public static Database get() {
    if (thisDb == null) {
      thisDb = new Database();
    }
    return thisDb;
  }

  /**
   * Tries to create a new user in the database.
   *
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

      // Fetch the user that was just created with their id.
      currentUser = getUsers()
          .stream()
          .filter(fetchedUser -> fetchedUser.getUsername().equals(user.getUsername()))
          .collect(Collectors.toList()).get(0);

      log(new LogEntry(
          LogEntity.User,
          currentUser.getId(),
          String.format("User %s %s created an account", user.getFirstName(), user.getLastName()),
          currentUser.getId())
      );

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Attempts to login a user given a username and password. The result of this request also stores
   * the current user in the currentUser static variable for access anywhere.
   *
   * @param username The username to check against the database.
   * @param password The password to check against the database.
   * @return The user that corresponds to this username and password, otherwise null if the given
   * username and password don't match with a user.
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
        User user = parseUserInRow(resultSet);
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
   * @author Luis Hernandez
   */
  public void addAnimal(Animal animal) {

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
      statement.setBoolean(6, animal.isAdopted());
      statement.setTimestamp(7, Timestamp.from(animal.getDateArrived()));

      Timestamp dateAdopted = null;
      if (animal.getDateAdopted() != null) {
        dateAdopted = Timestamp.from(animal.getDateAdopted());
      }

      statement.setTimestamp(8, dateAdopted);

      statement.setTimestamp(9, Timestamp.from(animal.getDateOfBirth()));
      statement.setBoolean(10, animal.isServiceTrained());
      statement.setFloat(11, animal.getWeight());
      statement.setFloat(12, animal.getHeight());
      statement.setString(13, animal.getBreedString());
      statement.setString(14, animal.getBathroomTraining().toString());
      statement.setString(15, animal.getAggression().toString());

      statement.executeUpdate();

      int createdAnimalId = getAnimalList()
          .stream()
          .filter(fetchedAnimal -> {
            return fetchedAnimal.getName().equals(animal.getName()) &&
                fetchedAnimal.getDescription().equals(animal.getDescription());
          })
          .collect(Collectors.toList()).get(0).getId();

      log(new LogEntry(
          LogEntity.Animal,
          currentUser.getId(),
          String.format("Created animal '%s'", animal.getName()),
          createdAnimalId)
      );

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to check Employee Status.
   *
   * @param emp an Employee object.
   * @return The privilege level of the Employee, which is an ADMINISTRATION enum.
   * @author Ramzy El-Taher
   */
  public boolean checkEmployee(Employee emp) {
    return AuthorizationLevel.ADMINISTRATION == emp.getPrivileges();
  }

  /**
   * Method which allows employees to edit an animal's information in the database.
   *
   * @author Ramzy El-Taher
   */
  public void editAnimal(Animal animal) {
    String SQL = "UPDATE Animal SET name = ?, species = ?, description = ?,"
        + "gender = ?, colors = ?, adopted = ?, dateArrived = ?, dateAdopted = ?,"
        + "dateOfBirth = ?, serviceTrained = ?, weight = ?, height = ?, breeds = ?,"
        + "bathroomTraining = ?, aggression = ? WHERE Animal.id=?";
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
      ps.setString(13, animal.getBreedString());
      // If animal is bathroom trained
      ps.setBoolean(14, animal.isServiceTrained());
      // Aggression level
      ps.setString(15, animal.getAggression().toString());
      ps.setInt(16, animal.getId());
      //Execute Query
      ps.executeUpdate();

      log(new LogEntry(
          LogEntity.Animal,
          currentUser.getId(),
          String.format("Updated animal %s", animal.getName()),
          animal.getId())
      );

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Submits a question to the database.
   *
   * @param question The question the user has asked
   * @return True if the question was successfully stored, else false.
   * @author Travis Gayle
   */
  public boolean askQuestion(Question question) {
    String query = "INSERT INTO Question (userId, answered, question) VALUES (?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setInt(1, question.getUserId());
      ps.setBoolean(2, false);
      ps.setString(3, question.getQuestion());
      ps.executeUpdate();

      // Fetch the question that was just created.
      int createdQuestionId = getQuestions()
          .stream()
          .filter(fetchedQuestion -> fetchedQuestion.getQuestion().getQuestion()
              .equals(question.getQuestion()))
          .collect(Collectors.toList())
          .get(0)
          .getQuestion()
          .getId();

      log(new LogEntry(
          LogEntity.Question,
          currentUser.getId(),
          String.format("Asked question '%s'", question.getQuestion()),
          createdQuestionId)
      );
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
   * @param answerer The employee who asked the question
   * @param question The question being answered
   * @return True if this question was successfully answered, else false.
   * @author Travis Gayle
   */
  public boolean answerQuestion(User answerer, Question question) {
    if (answerer.getPrivileges().ordinal() < AuthorizationLevel.VOLUNTEER.ordinal()) {
      return false;
    }

    String query =
        "UPDATE Question "
            + "SET  (employeeId, answered, answer) = (?, ?, ?)"
            + "WHERE Question.id=?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setInt(1, answerer.getId());
      ps.setBoolean(2, true);
      ps.setString(3, question.getAnswer());
      ps.setInt(4, question.getId());
      ps.executeUpdate();

      log(new LogEntry(
          LogEntity.Question,
          currentUser.getId(),
          String.format("Answered question '%s'", question.getQuestion()),
          question.getId())
      );

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Submits an adoption request to be processed by the shelter.
   *
   * @param request The adoption request to be processed
   * @return True of the request was successfully submitted, else false.
   * @author Travis Gayle
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
      ps.setTimestamp(4, Timestamp.from(request.getDateRequested()));
      ps.executeUpdate();

      int createdAdoptionRequestId = getAdoptionRequests()
          .stream()
          .filter(req -> {
            return req.getRequest().getAnimalId() == request.getAnimalId() &&
                req.getAdopter().getId() == request.getCustomerId();
          })
          .collect(Collectors.toList()).get(0)
          .getRequest().getId();

      log(new LogEntry(
          LogEntity.Animal,
          currentUser.getId(),
          String.format("Submitted adoption request for '%s'",
              getAnimal(request.getAnimalId()).getName()),
          createdAdoptionRequestId)
      );

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Allows an employee to process an adoption request and approve or reject it.
   *
   * @param user    The user attempting to process this request.
   * @param request The request being processed
   * @return True if the processing was successful, else false.
   * @author Travis Gayle
   */
  public boolean processAdoptionRequest(User user, AdoptionRequest request) {
    // Make sure this user has permission to do this.
    if (currentUser.getPrivileges() == AuthorizationLevel.BASIC) {
      return false;
    }

    String query = "UPDATE AdoptionRequest "
        + "SET (approved, dateApproved) = (?, ?)"
        + "WHERE AdoptionRequest.id = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setBoolean(1, request.isApproved());
      ps.setTimestamp(2, Timestamp.from(request.getDateApproved()));
      ps.setInt(3, request.getId());

      User requestingUser = getUserById(request.getCustomerId());
      Animal requestedAnimal = getAnimal(request.getAnimalId());

      String approvedOrRejected = request.isApproved() ? "Approved" : "Rejected";

      log(new LogEntry(
          LogEntity.Animal,
          currentUser.getId(),
          String.format(
              "%s %s %s's adoption request for '%s'",
              approvedOrRejected,
              requestingUser.getFirstName(),
              requestingUser.getLastName(),
              requestedAnimal.getName()),
          request.getId())
      );
      ps.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * @param animal The animal that should be removed
   * @see #removeAnimal(int)
   */
  public boolean removeAnimal(Animal animal) {
    return removeAnimal(animal.getId());
  }

  /**
   * Removes an animal from the database if the current user has the authority to do so.
   *
   * @param animalId The id of the animal to be removed.
   * @return true if the removal was successful, else false.
   */
  public boolean removeAnimal(int animalId) {
    if (currentUser.getPrivileges() != AuthorizationLevel.ADMINISTRATION) {
      return false;
    }

    Animal animalToRemove = getAnimal(animalId);
    String query = "DELETE FROM Animal WHERE Animal.id=?";

    try (PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setInt(1, animalId);
      statement.executeUpdate();

      log(new LogEntry(
          LogEntity.Animal,
          currentUser.getId(),
          String.format("Removed animal '%s'", animalToRemove.getName()),
          animalToRemove.getId())
      );
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Returns the current user logged in.
   *
   * @return The current user or null if the user doesn't exist in the database.
   * @author Travis Gayle
   */
  public static User getCurrentUser() {
    return currentUser;
  }

  /**
   * @return List<Animal> animal - list of all animals in db to be shown in the list screen
   * @author Luis Hernandez
   */
  public List<Animal> getAnimalList() {

    List<Animal> animals = new ArrayList<>();

    String animalList = "SELECT * FROM Animal";

    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(animalList);

      while (rs.next()) {
        Animal thisAnimal = parseAnimalInResultSetRow(rs);
        animals.add(thisAnimal);
      }


    } catch (SQLException e) {
      e.printStackTrace();
    }

    return animals;
  }

  /**
   * Gets a current user by their id.
   *
   * @param userId The id of the user desired
   * @return The user if the given id corresponds to a user, else null.
   */
  public User getUserById(int userId) {
    String sql = "SELECT * FROM User WHERE id=? LIMIT 1";

    try (PreparedStatement statement = conn.prepareStatement(sql)) {
      statement.setInt(1, userId);
      ResultSet set = statement.executeQuery();

      if (set.next()) {
        return parseUserInRow(set);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Gets a single Animal with the given id.
   *
   * @param animalId The id of the animal
   * @return null if there is no animal with this id, otherwise the animal.
   */
  public Animal getAnimal(int animalId) {
    String query = "SELECT * FROM Animal WHERE id=?";

    try (PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setInt(1, animalId);
      ResultSet set = statement.executeQuery();

      // Move to first item.
      if (set.next()) {
        return parseAnimalInResultSetRow(set);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null; // If we reach this point then we know there is no animal.
  }

  /**
   * Returns a list of all questions.
   *
   * @return A list of questions.
   */
  public List<QuestionWithUser> getQuestions() {
    String sql = "SELECT * FROM Question";
    return doQuestionQuery(sql);
  }

  /**
   * Returns a list of all questions that haven't been answered yet.
   *
   * @return A list of unanswered questions.
   */
  public List<QuestionWithUser> getUnansweredQuestions() {
    String sql = "SELECT * FROM Question WHERE answered = 0";
    return doQuestionQuery(sql);
  }

  /**
   * Returns a list of questions that have been answered already.
   *
   * @return A list of answered questions.
   */
  public List<QuestionWithUser> getAnsweredQuestions() {
    return doQuestionQuery("SELECT * FROM Question WHERE answered = 1");
  }

  /**
   * Returns a list of all AdoptionRequests
   *
   * @return A list of all adoption requests
   */
  public List<AdoptionWithAnimal> getAdoptionRequests() {
    return doAdoptionQuery("SELECT * FROM AdoptionRequest");
  }

  /**
   * Returns a list of AdoptionRequests that haven't been processed by an employee yet.
   *
   * @return A list of all unprocessed adoption requests.
   */
  public List<AdoptionWithAnimal> getUnprocessedAdoptionRequests() {
    return doAdoptionQuery("SELECT * FROM AdoptionRequest WHERE dateApproved IS NULL");
  }

  /**
   * Returns a list of AdoptionRequests that have been processed by an employee.
   *
   * @return A list of processed AdoptionRequests
   */
  public List<AdoptionWithAnimal> getProcessedAdoptionRequests() {
    return doAdoptionQuery("SELECT * FROM AdoptionRequest WHERE dateApproved IS NOT NULL");
  }

  /**
   * Inserts an event into the database.
   *
   * @param event The event that should be added.
   * @return true if the operation was successful, otherwise false.
   */
  public boolean addEvent(Event event) {
    // Only Volunteers and Employees can create events.
    if (currentUser.getPrivileges().ordinal() < AuthorizationLevel.VOLUNTEER.ordinal()) {
      return false;
    }

    String query = "INSERT INTO Event "
        + "(creatorId, name, date, published, targetAudience, description) "
        + "VALUES (?, ?, ?, ?, ?, ?)";

    try (PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setInt(1, event.getEventCreatorId());
      statement.setString(2, event.getName());
      statement.setTimestamp(3, Timestamp.from(event.getDate()));
      statement.setBoolean(4, event.isPublished());
      statement.setInt(5, event.getTargetAudience().ordinal());
      statement.setString(6, event.getDescription());

      statement.executeUpdate();

      // Fetch the event that was just inserted so we can log this.
      int createdEventId = getEvents()
          .stream()
          .filter(evt -> evt.getName().equals(event.getName()))
          .collect(Collectors.toList()).get(0).getId();

      log(new LogEntry(
          LogEntity.Event,
          currentUser.getId(),
          String.format("Created event '%s'", event.getName()),
          createdEventId)
      );

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Marks an unprocessed volunteer application as processed, along with denoting if this
   * application approved or rejected.
   *
   * @param application The application that should be processed
   * @param approved    Whether the this application is approved and the user should become a
   *                    volunteer.
   * @return True if this update was successful, else false.
   */
  public boolean processVolunteerApplication(VolunteerApplication application, boolean approved) {
    String query = "UPDATE VolunteerApplication SET (dateApproved, approved) = (?, ?) WHERE id=?";

    try (PreparedStatement statement = conn.prepareStatement(query)) {
      application.setDateApproved(Instant.now());
      application.setApproved(approved);

      statement.setTimestamp(1, Timestamp.from(application.getDateApproved()));
      statement.setBoolean(2, application.isApproved());
      statement.setInt(3, application.getId());
      statement.executeUpdate();

      User applicantUser = getUserById(application.getApplicantId());

      if (approved) {
        if (makeUserVolunteer(application.getApplicantId())) {
          log(new LogEntry(
              LogEntity.User,
              currentUser.getId(),
              String.format("Promoted '%s %s' to volunteer", applicantUser.getFirstName(),
                  applicantUser.getLastName()),
              applicantUser.getId())
          );
          return true;
        } else {
          System.err.println("There was an issue making the given user a volunteer.");
          return false;
        }
      } else {
        log(new LogEntry(
            LogEntity.User,
            currentUser.getId(),
            String.format("Rejected %s %s's volunteer application", applicantUser.getFirstName(),
                applicantUser.getLastName()),
            applicantUser.getId())
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
  }

  /**
   * Returns whether or not the given username exists within the database.
   *
   * @param username The username that should be searched for within the database.
   * @return True if the username does already exist or if there's an issue retrieving the user,
   * otherwise false.
   */
  public boolean doesUsernameExist(String username) {
    String query = "SELECT username from User WHERE username=?";
    try (PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setString(1, username);
      ResultSet results = statement.executeQuery();

      // If the user does exist in the database, this function will return true;
      return results.next();
    } catch (SQLException e) {
      e.printStackTrace();

      // Return true if we can't tell if the user exists to prevent accidentally creating 2 users
      // the same name.
      return true;
    }
  }

  /**
   * Gives the user with the given id volunteer privileges.
   *
   * @param userId The id of the user to be updated.
   * @return True if this update was successful, else false.
   */
  private boolean makeUserVolunteer(int userId) {
    String query = "UPDATE User SET (privilege) = (?) WHERE id=?";

    try (PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setInt(1, AuthorizationLevel.VOLUNTEER.ordinal());
      statement.setInt(2, userId);
      statement.executeUpdate();

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static void setCurrentAnimal(Animal currentAnimal) {
    Database.currentAnimal = currentAnimal;
  }

  public static Animal getCurrentAnimal() {
    return currentAnimal;
  }

  /**
   * Updates a preexisting event.
   *
   * @param event The event that should be updated
   * @return True if the update was successful.
   */
  public boolean updateEvent(Event event) {
    // Only Volunteers and Employees can create events.
    if (currentUser.getPrivileges().ordinal() < AuthorizationLevel.VOLUNTEER.ordinal()) {
      return false;
    }

    String query = "UPDATE Event "
        + "SET (name, date, published, targetAudience, description)=(?, ?, ?, ?, ?) "
        + "WHERE id=?";

    try (PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setString(1, event.getName());
      statement.setTimestamp(2, Timestamp.from(event.getDate()));
      statement.setBoolean(3, event.isPublished());
      statement.setInt(4, event.getTargetAudience().ordinal());
      statement.setString(5, event.getDescription());
      statement.setInt(6, event.getId());

      log(new LogEntry(
          LogEntity.Event,
          currentUser.getId(),
          String.format("Modified the event '%s'", event.getName()),
          event.getId())
      );

      statement.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Returns a list of all events for all audiences.
   *
   * @return A list with all saved events.
   */
  public List<Event> getEvents() {
    String query = "SELECT * FROM Event";
    List<Event> events = new ArrayList<>();
    try (ResultSet results = conn.createStatement().executeQuery(query)) {
      while (results.next()) {
        Event thisEvent = new Event();
        thisEvent.setId(results.getInt(1));
        thisEvent.setEventCreatorId(results.getInt(2));
        thisEvent.setName(results.getString(3));
        thisEvent.setDate(results.getTimestamp(4).toInstant());
        thisEvent.setPublished(results.getBoolean(5));
        thisEvent.setTargetAudience(AuthorizationLevel.values()[results.getInt(6)]);
        thisEvent.setDescription(results.getString(7));

        events.add(thisEvent);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return events;
  }

  /**
   * Returns a list of all volunteer applications.
   *
   * @return A list with all submitted volunteer applications.
   */
  public List<VolunteerApplicationWithUser> getVolunteerApplications() {
    return doVolunteerApplicationQuery("SELECT * FROM VolunteerApplication");
  }

  /**
   * Returns a list of all volunteer applications that haven't been processed by an employee
   *
   * @return A list with all unprocessed volunteer applications.
   */
  public List<VolunteerApplicationWithUser> getUnprocessedVolunteerApplications() {
    return doVolunteerApplicationQuery(
        "SELECT * FROM VolunteerApplication WHERE dateApproved IS NULL");
  }

  /**
   * Returns a list of all volunteer applications that have been processed by an employee
   *
   * @return A list with all processed volunteer applications.
   */
  public List<VolunteerApplicationWithUser> getProcessedVolunteerApplications() {
    return doVolunteerApplicationQuery(
        "SELECT * FROM VolunteerApplication WHERE dateApproved IS NOT NULL");
  }

  /**
   * Executes a query for VolunteerApplications to the database and returns the response. Queries
   * should use the `*` in order to get all columns of the VolunteerApplication table
   *
   * @param sql The query to be executed.
   * @return A list of VolunteerApplications and the result of the given SQL query
   */
  private List<VolunteerApplicationWithUser> doVolunteerApplicationQuery(String sql) {
    List<VolunteerApplicationWithUser> applications = new ArrayList<>();
    try (ResultSet results = conn.createStatement().executeQuery(sql)) {
      while (results.next()) {
        VolunteerApplication thisApplication = new VolunteerApplication();
        thisApplication.setId(results.getInt(1));
        thisApplication.setApplicantId(results.getInt(2));
        thisApplication.setApproved(results.getBoolean(3));

        Timestamp dateRequested = results.getTimestamp(4);
        Timestamp dateApproved = results.getTimestamp(5);

        if (dateRequested != null) {
          thisApplication.setDateRequested(dateRequested.toInstant());
        }

        if (dateApproved != null) {
          thisApplication.setDateApproved(dateApproved.toInstant());
        }

        User requestingUser = getUserById(thisApplication.getApplicantId());
        applications.add(new VolunteerApplicationWithUser(requestingUser, thisApplication));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return applications;
  }

  /**
   * Executes a query for Questions to the database and returns the response. Queries should use the
   * `*` in order to get all columns of the Question table
   *
   * @param sql The query to be executed.
   * @return A list of Questions and the result of the given SQL query
   */
  private List<QuestionWithUser> doQuestionQuery(String sql) {
    List<QuestionWithUser> questions = new ArrayList<>();

    try (ResultSet results = conn.createStatement().executeQuery(sql)) {
      while (results.next()) {
        Question question = parseQuestionFromResultSetRow(results);
        User asker = getUserById(question.getUserId());
        User answerer = getUserById(question.getEmployeeId());
        questions.add(new QuestionWithUser(question, asker, answerer));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return questions;
  }

  /**
   * Executes a query for Adoptions to the database and returns the response. Queries should use the
   * `*` in order to get all columns of the AdoptionRequest table
   *
   * @param sql The query to be executed.
   * @return A list of AdoptionRequests and the animal the request is for.
   */
  private List<AdoptionWithAnimal> doAdoptionQuery(String sql) {
    List<AdoptionWithAnimal> requests = new ArrayList<>();

    try (ResultSet results = conn.createStatement().executeQuery(sql)) {
      while (results.next()) {
        int id = results.getInt(1);
        int customerId = results.getInt(2);
        int animalId = results.getInt(3);
        boolean adoptionApproved = results.getBoolean(4);
        Instant dateRequested = results.getTimestamp(5).toInstant();

        Instant dateApproved = null;
        Timestamp dateApprovedTS = results.getTimestamp(6);

        if (dateApprovedTS != null) {
          dateApproved = dateApprovedTS.toInstant();
        }

        Animal animal = getAnimal(animalId);
        User customer = getUserById(customerId);
        AdoptionRequest request = new AdoptionRequest(id, customerId, animalId, adoptionApproved,
            dateRequested, dateApproved);
        requests.add(new AdoptionWithAnimal(request, animal, customer));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return requests;
  }


  /**
   * Reads all columns of the current row of a result set to create a Question
   *
   * @param resultSet The result set that values should be read from
   * @return The question from the values of the current row of the ResultSet
   * @throws SQLException if there is an issue reading values from the database
   */
  private Question parseQuestionFromResultSetRow(ResultSet resultSet) throws SQLException {
    int questionId = resultSet.getInt(1);
    int userId = resultSet.getInt(2);
    boolean isAnswered = resultSet.getBoolean(3);
    int employeeId = resultSet.getInt(4);
    String question = resultSet.getString(5);
    String answer = resultSet.getString(6);

    return new Question(questionId, userId, isAnswered, employeeId, question, answer);
  }

  /**
   * Reads all columns of the current row of a result set to create an Animal
   *
   * @param rs The result set that values should be read from
   * @return The Animal from the values of the current row of the ResultSet
   * @throws SQLException if there is an issue reading values from the database
   */
  private Animal parseAnimalInResultSetRow(ResultSet rs) throws SQLException {
    Animal myPet = new Animal();

    int a = rs.getInt("id");
    String b = rs.getString("name");
    String c = rs.getString("species");
    String d = rs.getString("description");
    char e = rs.getString("gender").charAt(0);
    String f = rs.getString("colors"); // colors is a List of enum color
    boolean g = rs.getBoolean("adopted");
    Instant h = rs.getTimestamp("dateArrived").toInstant();

    Timestamp dateAdopted = rs.getTimestamp("dateAdopted");
    Instant i = null;

    if (dateAdopted != null) {
      i = rs.getTimestamp("dateAdopted").toInstant();
    }

    Instant j = rs.getTimestamp("dateOfBirth").toInstant();
    boolean k = rs.getBoolean("serviceTrained");
    float l = rs.getFloat("weight");
    float m = rs.getFloat("height");
    String n = rs.getString("breeds");
    int o = rs.getInt("bathroomTraining");
    int p = rs.getInt("aggression");

    // breeds is a list<string> so we must convert from String to list<String>
    List<String> forBreeds = new ArrayList<>();
    String[] myBreeds = n.split("\\|");
    for (int y = 0; y < myBreeds.length; y++) {
      forBreeds.add(y, myBreeds[y]);
    }

    //Enum numbers start at 0, so if you use Color.Blue.ordinal() or something like that, you
    // need to start at 0 to get everything
    // change int to enum Proficiency for animal object list
    Proficiency aniProf = null;
    switch (o) {
      case 0:
        aniProf = None;
        break;
      case 1:
        aniProf = Awful;
        break;
      case 2:
        aniProf = Bad;
        break;
      case 3:
        aniProf = Neutral;
        break;
      case 4:
        aniProf = Good;
        break;
      case 5:
        aniProf = Great;
        break;
      case 6:
        aniProf = Excellent;
        break;
      default:
        System.out.println("invalid number inputted! [ 0 - 6 accepted]");
        break;
    }
    // switch case similar to above but for aggression
    Color petAnger = null;
    switch (p) {
      case 0:
        petAnger = Red;
        break;
      case 1:
        petAnger = Orange;
        break;
      case 2:
        petAnger = Yellow;
        break;
      case 3:
        petAnger = Green;
        break;
      case 4:
        petAnger = Blue;
        break;
      case 5:
        petAnger = Brown;
        break;
      case 6:
        petAnger = White;
        break;
      case 7:
        petAnger = Black;
        break;
      case 8:
        petAnger = Gray;
        break;
      default:
        System.out.println("invalid number inputted. [ 0 - 8 accepted]");
        break;
    }
    // create List<Color> thru logic to add into animal myPet
    List<Color> petColors = new ArrayList<>();
    String[] theColor = f.split("\\|");

    // go through loop once checking individual values in array for color enum
    for (int z = 0; z < theColor.length; z++) {
      if (theColor[z].equals("Red")) {
        petColors.add(z, Red);
      } else if (theColor[z].equals("Orange")) {
        petColors.add(z, Orange);
      } else if (theColor[z].equals("Yellow")) {
        petColors.add(z, Yellow);
      } else if (theColor[z].equals("Green")) {
        petColors.add(z, Green);
      } else if (theColor[z].equals("Blue")) {
        petColors.add(z, Blue);
      } else if (theColor[z].equals("Brown")) {
        petColors.add(z, Brown);
      } else if (theColor[z].equals("White")) {
        petColors.add(z, White);
      } else if (theColor[z].equals("Black")) {
        petColors.add(z, Black);
      } else if (theColor[z].equals("Gray")) {
        petColors.add(z, Gray);
      }
    }

    myPet.setId(a);
    myPet.setName(b);
    myPet.setSpecies(c);
    myPet.setDescription(d);
    myPet.setGender(e);
    myPet.setColors(petColors);
    myPet.setAdopted(g);
    myPet.setDateArrived(h);
    myPet.setDateAdopted(i);
    myPet.setDateOfBirth(j);
    myPet.setServiceTrained(k);
    myPet.setWeight(l);
    myPet.setHeight(m);
    myPet.setBreeds(forBreeds);
    myPet.setBathroomTraining(aniProf);
    myPet.setAggression(petAnger);
    myPet.setTricks(new ArrayList<>());

    return myPet;
  }

  /**
   * Method that creates a new User, and reads the database for all of the users that already exists.
   * @param resultSet The ResultSet used to get each column in the database.
   * @return user, which is a User object used for creating new users.
   * @throws SQLException since the code has the potential to contain an exception.
   */
  private User parseUserInRow(ResultSet resultSet) throws SQLException {
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

  /**
   * Method that only allows the user to apply for volunteer if they are not a volunteer or employee.
   * @param user A user object that is used to check if the current user's privileges are not a customer.
   * @return false if the user is not a customer, true if the user is a customer.
   */
  public boolean applyForVolunteer(User user) {
    // Only allow this user to apply for volunteer if they're not a volunteer or employee.
    if (user.getPrivileges() != AuthorizationLevel.BASIC) {
      return false;
    }

    String query = "INSERT INTO VolunteerApplication (applicantId, approved, dateRequested) VALUES (?, ?, ?)";

    try (PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setInt(1, user.getId());
      statement.setBoolean(2, false);
      statement.setTimestamp(3, Timestamp.from(Instant.now()));
      statement.executeUpdate();

      log(new LogEntry(LogEntity.User, user.getId(), "Applied for volunteer.", user.getId()));

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Accessor for currentEvent.
   * @return currentEvent, which is a field of type Event.
   */
  public static Event getCurrentEvent() {
    return currentEvent;
  }

  /**
   * Mutator for currentEvent
   * @param currentEvent the variable the the "currentEvent" field is assigned to.
   */
  public static void setCurrentEvent(Event currentEvent) {
    Database.currentEvent = currentEvent;
  }

  private void log(LogEntry entry) {
    String insertQuery = "INSERT INTO Log "
        + "(entityAffected, userId, message, affectedId, dateOccurred) "
        + "VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
      stmt.setInt(1, entry.getEntityAffected().ordinal());
      stmt.setInt(2, entry.getUserId());
      stmt.setString(3, entry.getMessage());
      stmt.setInt(4, entry.getAffectedId());
      stmt.setTimestamp(5, Timestamp.from(entry.getDateOccurred()));

      stmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      System.err.println("There was an issue logging an action.");
    }
  }

  public List<LogEntryWithUser> getLogs() {
    List<LogEntryWithUser> logs = new ArrayList<>();

    try (ResultSet results = conn.createStatement().executeQuery("SELECT * FROM Log")) {
      while (results.next()) {
        int entryId = results.getInt(1);
        LogEntity affectedEntity = LogEntity.values()[results.getInt(2)];
        int userId = results.getInt(3);
        String message = results.getString(4);
        int affectedId = results.getInt(5);
        Instant date = results.getTimestamp(6).toInstant();

        LogEntry entry = new LogEntry(entryId, affectedEntity, userId, message, affectedId, date);
        logs.add(new LogEntryWithUser(entry, getUserById(userId)));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return logs;
  }

  private List<User> getUsers() {
    List<User> users = new ArrayList<>();
    try (ResultSet results = conn.createStatement().executeQuery("SELECT * FROM User")) {
      while (results.next()) {
        users.add(parseUserInRow(results));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return users;
  }

}

