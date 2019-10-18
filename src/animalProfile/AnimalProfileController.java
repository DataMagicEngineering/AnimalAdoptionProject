package animalProfile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AnimalProfileController {

  @FXML
  private Text animNameText;

  @FXML
  private Text animSpeciesText;

  @FXML
  private Text animBreedText;

  @FXML
  private Text animGenderText;

  @FXML
  private Text animIDText;

  @FXML
  private Text animBioText;

  @FXML
  private Text animColorText;

  @FXML
  private Text animWeightText;

  @FXML
  private Text animHeightText;

  @FXML
  private Text animAggressText;

  @FXML
  private Text animBathroomText;

  @FXML
  private Text animServiceText;

  @FXML
  private Text animAdoptedText;

  @FXML
  private Text animDateArrivedText;

  @FXML
  private Text animDateAdoptedText;

  @FXML
  private Text animBirthdayText;

  @FXML
  private Button editAnimalBtn;

  @FXML
  private Button returnBtn;

  @FXML
  private Button adoptBtn;

  @FXML
  void goToAdoptionPage(ActionEvent event) {

  }

  @FXML
  void goToEdit(ActionEvent event) {

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

}
