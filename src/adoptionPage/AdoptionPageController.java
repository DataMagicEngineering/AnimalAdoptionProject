package adoptionPage;

import java.io.IOException;
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
import main.Database;
import models.adoption.AdoptionRequest;

/**
 * The Adoption Page Controller displays information about the animal being adopted and the user
 * that is requesting to adopt the animal.
 *
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

  /**
   * Method that switches scenes to the Confirm Page screen while getting the ID of the current
   * user, creating a new Adoption Request, and sending the Adoption Request to the database.
   *
   * @param event gets the Source, Scene, and Window of the selected scene, and is casted to a
   *              Node.
   * @throws IOException since the method has a chance to contain an IOException.
   */
  @FXML
  void adoptAnimal(ActionEvent event) throws IOException {
    AdoptionRequest request = new AdoptionRequest();
    request.setCustomerId(Database.getCurrentUser().getId());
    request.setAnimalId(Database.getCurrentAnimal().getId());
    request.setApproved(false);
    request.setDateRequested(Instant.now());
    Database.get().submitAdoptionRequest(request);

    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../adoptionConfirmationPage/ConfirmPage.fxml"));
    primaryStage.setTitle("Adoption");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  /**
   * Method that returns the user to the menu scene when the button is pressed.
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
   * The initialize method in the AdoptionPageController changes the Instant of the user's date of
   * birth to the format "MM/dd/yyyy". This method also sets the text of the animal's name, breed,
   * species, first name, last name, and date of birth to the information obtained within the
   * database.
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
