package newanimal;

import java.time.Instant;
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.xml.crypto.Data;
import main.Database;
import models.animal.Animal;
import models.animal.Color;
import models.animal.Proficiency;
import models.event.Event;

/**
 * The NewAnimalController class is the Controller for the scene which creates a new animal. This class allows the user
 * to edit an animal's information and to add a new animal to the list of animals in the Animal List page.
 */
public class NewAnimalController {

  private static Database database = Database.get();

  public TextField nameInput;
  public TextField speciesInput;
  public TextField weightInput;
  public TextField heightInput;
  public TextField breedsInput;
  public TextField tricksInput;
  public TextField vaccineInput;
  public Text currentAggression;
  public Slider aggressionSlider;
  public ComboBox<Proficiency> bathroomTrainingBox;
  public ToggleButton genderToggle;
  public ListView<Color> colorsList;
  public ToggleButton serviceTrainingToggle;
  public DatePicker dateOfBirth;
  public TextArea descriptionInput;
  public Button createBtn;

  /**
   * The initialize method in the NewAnimaLController class sets values for an animal's date of birth, aggression,
   * bathroom training, color, service trained, and gender. These values will be used for every time the program starts.
   */
  public void initialize() {
    dateOfBirth.setValue(LocalDate.now());
    aggressionSlider.setMax(Color.values().length - 1);
    aggressionSlider.setBlockIncrement(1);
    aggressionSlider.valueProperty().addListener((observableValue, old, newValue) -> {
      currentAggression.setText(Color.values()[newValue.intValue()].toString());
    });

    bathroomTrainingBox.setItems(FXCollections.observableList(Arrays.asList(Proficiency.values())));
    bathroomTrainingBox.getSelectionModel().selectFirst();

    currentAggression.setText(Color.values()[0].toString());
    colorsList.setItems(FXCollections.observableList(Arrays.asList(Color.values())));
    colorsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    serviceTrainingToggle.selectedProperty().addListener((observableValue, old, isToggled) -> {
      serviceTrainingToggle.setText(isToggled ? "Yes" : "No");
    });

    genderToggle.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
      genderToggle.setText(newVal ? "Male" : "Female");
    });

    editAnimal();
  }

  /**
   * A method that returns the user back to the Animal List.
   * @param event gets the source, scene, and window.
   * @throws Exception since the method has a chance to contain an Exception.
   */
  public void cancelCreation(ActionEvent event) throws Exception {
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("../animalList/AnimalList.fxml"));
    primaryStage.setTitle("Animal List");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();

    Database.setCurrentAnimal(null);
  }

  /**
   * The editAnimal method gives the user the ability to edit an animal's information, which includes the animal's name,
   * species, breed, height, weight, description, tricks, color, date of birth, service trained, bathroom trained, and
   * aggression.
   */
  public void editAnimal() {
    Animal animal = Database.getCurrentAnimal();
    if (animal != null) {
      nameInput.setText(animal.getName());
      speciesInput.setText(animal.getSpecies());
      breedsInput.setText(animal.getBreedString().replace("|", ","));
      heightInput.setText(String.valueOf(animal.getHeight()));
      weightInput.setText(String.valueOf(animal.getWeight()));
      descriptionInput.setText(animal.getDescription());
      tricksInput.setText(animal.getTricks().toString().replace("{", "").replace("}", ""));
      for (Color colors : animal.getColors()) {
        colorsList.getSelectionModel().select(colors);
      }
      dateOfBirth.setChronology(Chronology.from(animal.getDateOfBirth()));
      serviceTrainingToggle.setSelected(animal.isServiceTrained());
      bathroomTrainingBox.getSelectionModel().select(animal.getBathroomTraining());
      aggressionSlider.setValue(animal.getAggression().ordinal());

      createBtn.setText("Edit");
    }

  }

  /**
   * The method which changes the animal's information that the user had edited, and switchers the user's scenes back to
   * the Animal List Scene.
   * @param actionEvent an ActionEvent that gets the Source, Scene, and Window of the Animal List page.
   * @throws Exception since the method has a chance to contain an Exception.
   */
  public void createAnimal(ActionEvent actionEvent) throws Exception {
    if (!validInput()) {
      return;
    }

    Animal animal = new Animal();

    if (Database.getCurrentAnimal() != null) {
      animal = Database.getCurrentAnimal();
    }

    animal.setName(nameInput.getText());
    animal.setSpecies(speciesInput.getText());
    animal.setDescription(descriptionInput.getText());
    animal.setGender(genderToggle.isSelected() ? 'M' : 'F');
    animal.setColors(colorsList.getSelectionModel().getSelectedItems());
    animal.setAdopted(false);
    animal.setDateArrived(Instant.now());
    animal.setDateOfBirth(Instant.from(dateOfBirth.getValue().atStartOfDay(Event.EST)));
    animal.setServiceTrained(serviceTrainingToggle.isSelected());
    animal.setWeight(Float.parseFloat(weightInput.getText()));
    animal.setHeight(Float.parseFloat(heightInput.getText()));
    animal.setBreeds(Arrays.asList(breedsInput.getText().split(",")));
    animal.setBathroomTraining(bathroomTrainingBox.getValue());
    animal.setVaccines(new ArrayList<>()); // Temp empty list
    animal.setAggression(Color.values()[(int) aggressionSlider.getValue()]);

    if (Database.getCurrentAnimal() != null) {
      database.editAnimal(animal);
    } else {
      database.addAnimal(animal);
    }

    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("../animalList/AnimalList.fxml"));
    primaryStage.setTitle("Animal List");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();

    Database.setCurrentAnimal(null);
  }

  /**
   * The validInput method is used to determine if the user is entering a valid entry in the text fields when creating
   * a new animal
   * @return true, which means that the input is valid.
   */
  private boolean validInput() {

    return true;
  }
}
