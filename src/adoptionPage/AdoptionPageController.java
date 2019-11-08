package adoptionPage;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.xml.crypto.Data;
import main.Database;

/**
 * Adoption Page Controller, which displays information about the animal being adopted and the user
 * that is requesting to adopt the animal.
 * @author The Data Magic Engineering Team
 */
public class AdoptionPageController {

  @FXML
  private Button returnBtn;

  @FXML
  private Text animNameText;

  @FXML
  private Text animSpeciesText;

  @FXML
  private Text animBreedText;

  @FXML
  private Text userFirstNameText;

  @FXML
  private Text userLastNameText;

  @FXML
  private Text userDOBText;

  @FXML
  private Button adoptAnimalBtn;

  @FXML
  void adoptAnimal(ActionEvent event) {

  }

  /**
   * returns the user to the menu scene when the button is pressed.
   *
   * @param event User clicks/presses enter on the button
   * @throws Exception when the scene is not located at the same file location as stated
   */
  @FXML
  void returnToMenu(ActionEvent event) throws Exception {
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../animalList/AnimalList.fxml"));
    primaryStage.setTitle("Main Screen");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  /**
   * Method to test whether the First/Last name text fields match the users listed in the database.
   */
  public void initialize() {
    DateTimeFormatter formatDOB = DateTimeFormatter.ofPattern("MM/dd/yyyy").withZone(
        ZoneId.systemDefault());
    Instant userDOB = Database.getCurrentUser().getDateOfBirth();
    String formatUserDOB = formatDOB.format(userDOB);
    animNameText.setText(Database.getCurrentAnimal().getName());
    animBreedText.setText(Database.getCurrentAnimal().getBreedString());
    animSpeciesText.setText(Database.getCurrentAnimal().getSpecies());
    userFirstNameText.setText(Database.getCurrentUser().getFirstName());
    userLastNameText.setText(Database.getCurrentUser().getLastName());
    userDOBText.setText(formatUserDOB);
  }
}
