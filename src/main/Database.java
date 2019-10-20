package main;

import static javax.swing.UIManager.getString;
import static models.animal.Color.*;
import static models.animal.Proficiency.*;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.adoption.AdoptionRequest;
import models.animal.Animal;
import models.animal.Color;
import models.animal.Proficiency;
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
   *
   * @param emp
   * @return
   * @author Ramzy El-Taher
   */
  public boolean checkEmployee(Employee emp) {
    if (AuthorizationLevel.ADMINISTRATION == emp.getPrivileges()) {
      return true;
    }
    return false;
  }

  /**
   * Method which allows employees to edit an animal's information in the database.
   *
   * @return void
   * @author Ramzy El-Taher
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
      ps.setString(13, animal.getBreedString());
      // If animal is bathroom trained
      ps.setBoolean(14, animal.isServiceTrained());
      // Aggression level
      ps.setString(15, animal.getAggression().toString());
      //Execute Query
      ps.executeUpdate();
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
   *
   * @param user    The user attempting to process this request.
   * @param request The request being processed
   * @return True if the processing was successful, else false.
   * @author Travis Gayle
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
   *
   * @return The current user or null if the user doesn't exist in the database.
   * @author Travis Gayle
   */
  public static User getCurrentUser() {
    return currentUser;
  }

  /**
   * @author Luis Hernandez
   * @return List<Animal> animal - list of all animals in db to be shown in the list screen
   */
  public List<Animal> getAnimalList() {

    List<Animal> animals = null;

    String animalList = "SELECT * FROM Animal";

    try {

      Animal myPet = new Animal();

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(animalList);

      while (rs.next()) {
        int a = rs.getInt("id");
        String b = rs.getString("name");
        String c = rs.getString("species");
        String d = rs.getString("description");
        char e = rs.getString("gender").charAt(0);
        String f = rs.getString("colors"); // colors is a List of enum color
        boolean g = rs.getBoolean("adopted");
        Instant h = rs.getTimestamp("dateArrived").toInstant();
        Instant i = rs.getTimestamp("dateAdopted").toInstant();
        Instant j = rs.getTimestamp("dateOfBirth").toInstant();
        boolean k = rs.getBoolean("serviceTrained");
        float l = rs.getFloat("weight");
        float m = rs.getFloat("height");
        String n = rs.getString("breeds");
        int o = rs.getInt("bathroomTraining");
        int p = rs.getInt("aggression");

        // breeds is a list<string> so we must convert from String to list<String>
        List<String> forBreeds = new ArrayList<>();
        forBreeds.add(n);

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
        switch(p){
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
        String[] theColor = null;
        theColor = f.split("|");

        // go through loop once checking individual values in array for color enum
        for(int z = 0; z < theColor.length; z++){
          if(theColor[z].equals("Red")){
            petColors.add(z, Red);
          }
          else if(theColor[z].equals("Orange")){
            petColors.add(z, Orange);
          }
          else if(theColor[z].equals("Yellow")){
            petColors.add(z, Yellow);
          }
          else if(theColor[z].equals("Green")){
            petColors.add(z, Green);
          }
          else if(theColor[z].equals("Blue")){
            petColors.add(z, Blue);
          }
          else if(theColor[z].equals("Brown")){
            petColors.add(z, Brown);
          }
          else if(theColor[z].equals("White")){
            petColors.add(z, White);
          }
          else if(theColor[z].equals("Black")){
            petColors.add(z, Black);
          }
          else if(theColor[z].equals("Gray")){
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

        animals.add(myPet);
      }


    } catch (SQLException e) {
      e.printStackTrace();
    }

    return animals;
  }
}
