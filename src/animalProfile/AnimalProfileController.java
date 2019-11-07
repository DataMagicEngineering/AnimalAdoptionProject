package animalProfile;

import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Database;
import models.animal.Animal;
import models.animal.Vaccine;
import models.user.AuthorizationLevel;

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
  private ListView<Vaccine> animVaccListView;

  private void profileEditable() {
    if (Database.getCurrentUser().getPrivileges() != AuthorizationLevel.ADMINISTRATION) {
      editAnimalBtn.setVisible(false);
      editAnimalBtn.setDisable(true);
    }
  }

  private void setAnimalVaccine(ObservableList<Vaccine> vaccines) {
    animVaccListView.setItems(vaccines);
  }

  @FXML
  void goToAdoptionPage(ActionEvent event) throws Exception {
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../adoptionPage/AdoptionPage.fxml"));
    primaryStage.setTitle("Adoption Page");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
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
  void goBack(ActionEvent event) throws Exception {
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../animalList/AnimalList.fxml"));
    primaryStage.setTitle("Animal List");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  public void initialize() {
    profileEditable();
    Animal animal = Database.getCurrentAnimal();
    animNameText.setText(animal.getName());
    animSpeciesText.setText((animal.getSpecies()));
    animAggressText.setText(animal.getAggression().toString());
    animBathroomText.setText(animal.getBathroomTraining().toString());
    animBioText.setText(animal.getDescription());
    animBirthdayText
        .setText(animal.getDateOfBirth().toString());
    animBreedText.setText(animal.getBreedString());
    animColorText.setText((animal.getColorString()));
    animDateAdoptedText.setText(animal.getDateAdopted().toString());
    animDateArrivedText.setText((animal.getDateArrived().toString()));
    animGenderText.setText(String.valueOf(animal.getGender()));
    animHeightText.setText(String.valueOf(animal.getHeight()));
    animIDText.setText(String.valueOf(animal.getId()));
    animServiceText.setText(String.valueOf(animal.isServiceTrained()));
    animWeightText.setText(String.valueOf(animal.getWeight()));
  }
}
