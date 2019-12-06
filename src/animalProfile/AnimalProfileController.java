package animalProfile;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

/**
 * Animal Profile Controller, which displays information about the animal. The Scene has two
 * buttons: one that goes back to Animal List, and one that submits an Adoption request for the
 * animal.
 *
 * @author The Data Magic Engineering Team
 */
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

  /**
   * Method which prevents any users who aren't employees to edit the animal's profiles. This method
   * disables the "Edit Animal Profile" button from being viewed to users who do not have the right
   * privileges.
   */
  private void profileEditable() {
    if (Database.getCurrentUser().getPrivileges() != AuthorizationLevel.ADMINISTRATION) {
      editAnimalBtn.setVisible(false);
      editAnimalBtn.setDisable(true);
    }
  }

  /**
   * Method which populates the List of animal vaccines.
   *
   * @param vaccines The ObservableList that is being populated with the list of vaccines.
   */
  private void setAnimalVaccine(ObservableList<Vaccine> vaccines) {
    animVaccListView.setItems(vaccines);
  }

  /**
   * Method which sets the Scene to the Adoption Page.
   *
   * @param event The ActionEvent that gets the Source, Scene, and Window.
   * @throws Exception since the method has the possibility of containing an Exception.
   */
  @FXML
  void goToAdoptionPage(ActionEvent event) throws Exception {
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("/adoptionPage/AdoptionPage.fxml"));
    primaryStage.setTitle("Adoption Page");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }

  /**
   * Method which sets the Scene to the Edit Animal Profile page.
   *
   * @param event The ActionEvent that gets the Source, Scene, and Window.
   */
  @FXML
  void goToEdit(ActionEvent event) throws Exception {
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("/newanimal/NewAnimal.fxml"));
    primaryStage.setTitle("Edit " + Database.getCurrentAnimal().getName());
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
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
        .load(getClass().getResource("/animalList/AnimalList.fxml"));
    primaryStage.setTitle("Animal List");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();

    Database.setCurrentAnimal(null);
  }

  /**
   * The initialize method in the Animal Profile Controller class sets the animal's information when
   * the program is started. An animals information is contained within the Animal table in the
   * database, and when an animal is selected, that animal's information is loaded onto the screen.
   */
  public void initialize() {
    profileEditable();
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("MM/dd/yyyy").withZone(
        ZoneId.systemDefault());
    Animal animal = Database.getCurrentAnimal();
    animNameText.setText(animal.getName());
    animSpeciesText.setText((animal.getSpecies()));
    animAggressText.setText(animal.getAggression().toString());
    animBathroomText.setText(animal.getBathroomTraining().toString());
    animBioText.setText(animal.getDescription());
    animBirthdayText.setText(formatDate.format(animal.getDateOfBirth()));
    animBreedText.setText(animal.getFormattedBreeds());
    animColorText.setText((animal.getColorString().replace('|', ' ')));

    animAdoptedText.setText(animal.isAdopted() ? "✓" : "X");

    String adoptedDate = "N/A";

    if (animal.getDateAdopted() != null) {
      adoptedDate = formatDate.format(animal.getDateAdopted());
    }

    animDateAdoptedText.setText(adoptedDate);
    animDateArrivedText.setText(formatDate.format(animal.getDateArrived()));

    String gender = animal.getGender() == 'M' ? "Male" : "Female";
    animGenderText.setText(gender);
    animHeightText.setText(animal.getHeight() + " in");
    animIDText.setText(String.valueOf(animal.getId()));
    animServiceText.setText(animal.isServiceTrained() ? "✓" : "X");
    animWeightText.setText(animal.getWeight() + " lb");
  }
}
