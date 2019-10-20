package adoptionPage;

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
        .load(getClass().getResource("../mainScreen/AnimalAdoptMainScreen.fxml"));
    primaryStage.setTitle("Main Screen");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  public void initialize() {
    userFirstNameText.setText(Database.getCurrentUser().getFirstName());
    userLastNameText.setText(Database.getCurrentUser().getLastName());
  }
}
