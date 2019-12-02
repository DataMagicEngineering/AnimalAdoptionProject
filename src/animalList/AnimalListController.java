package animalList;

import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Database;
import models.animal.Animal;
import models.user.AuthorizationLevel;

/**
 * The Animal List Controller displays a list of animals available for adoption, and contains
 * functions which allows users to either view the animal profile or return back to main menu.
 * Employees have the ability to edit the animal's profile within this Scene.
 *
 * @author The Data Magic Engineering Team
 */
public class AnimalListController {

  private Database database = Database.get();
  private ObservableList<Animal> animals = FXCollections
      .observableArrayList(database.getAnimalList());

  @FXML
  private TextField searchTxtFld;

  @FXML
  private ChoiceBox<String> filterChoiceBox;

  @FXML
  private TableView<Animal> animalsTableView;

  @FXML
  private TableColumn<Animal, String> nameColumn;

  @FXML
  private TableColumn<Animal, String> speciesColumn;

  @FXML
  private TableColumn<Animal, String> breedColumn;

  @FXML
  private Button returnToMain;

  @FXML
  private Button selectedAnimalButton;

  @FXML
  private Button editAnimalButton;

  /**
   * The Initialize method in the Animal List Controller sets the Cell Value Factories for the Table
   * View in this Scene. It also populates the Table View with data from the Animal table in the
   * database. It also disables the "Edit Animal Button" visibility to any user who is not an
   * employee.
   *
   * @author Zachary Maroney
   */
  public void initialize() {
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    speciesColumn.setCellValueFactory(new PropertyValueFactory<>("species"));
    breedColumn.setCellValueFactory(new PropertyValueFactory<>("formattedBreeds"));
    loadAnimalList(animals);

    if (Database.getCurrentUser().getPrivileges() != AuthorizationLevel.ADMINISTRATION) {
      editAnimalButton.setDisable(true);
      editAnimalButton.setVisible(false);
    }

    filterChoiceBox.setItems(FXCollections.observableArrayList("Breed", "Species", "Name"));
    filterChoiceBox.getSelectionModel().selectFirst();

  }

  /**
   * Method which sets the scene to the Main Menu.
   *
   * @param event an ActionEvent that gets the source, scene, and window of the program.
   * @throws Exception is thrown since this method has the potential to contain an Exception.
   *
   * @Author Zachary Maroney
   */
  @FXML
  void goToMainMenu(ActionEvent event) throws Exception {
    if (Database.getCurrentUser().getPrivileges() == AuthorizationLevel.ADMINISTRATION
        || Database.getCurrentUser().getPrivileges() == AuthorizationLevel.VOLUNTEER) {
      Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      Parent root = FXMLLoader
          .load(getClass().getResource("../employeedashboard/EmployeeDashboard.fxml"));
      primaryStage.setTitle("Dashboard");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    } else {
      Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      Parent root = FXMLLoader
          .load(getClass().getResource("../customerdashboard/CustomerDashboard.fxml"));
      primaryStage.setTitle("Main Screen");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    }
  }

  /**
   * Method which sets the scene to the Adoption Page.
   *
   * @param event an ActionEvent that gets the source, scene, and window of the program.
   * @throws Exception is thrown since this method has the potential to contain an Exception.
   */
  @FXML
  void goToAnimalProfile(ActionEvent event) throws Exception {
    Database.setCurrentAnimal(animalsTableView.getSelectionModel().getSelectedItem());

    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../animalProfile/AnimalProfile.fxml"));
    primaryStage.setTitle("Animal Profile");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }

  /**
   * Method which gives employees the ability to create animals.
   *
   * @param event
   */
  @FXML
  void createAnimal(ActionEvent event) throws Exception {
    Database.setCurrentAnimal(null);

    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../newanimal/NewAnimal.fxml"));
    primaryStage.setTitle("New Animal");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }

  /**
   * Method which allows a user to filter a selection of animals when he or she is searching for an
   *
   *
   * @author Zachary Maroney
   */
  @FXML
  void applyFilter() {
    // initialize filtered Animal List.
    ObservableList<Animal> filteredAnimals;

    // if the search box is not empty.
    if (searchTxtFld != null) {

      // if the user wants to filter throw Breeds.
      if (filterChoiceBox.getSelectionModel().getSelectedItem().equals("Breed")) {

        // filterAnimal is set to filter throw the animal's breed with the User's search criteria.
        filteredAnimals = FXCollections.observableArrayList(
            animals.stream().filter(p -> p.getBreedString().toLowerCase()
                .contains(searchTxtFld.getText().toLowerCase()))
                .collect(Collectors.toList()));

      }
      // if the user wants to filter throw Species.
      else if (filterChoiceBox.getSelectionModel().getSelectedItem().equals("Species")) {

        // filterAnimal is set to filter throw the animal's species with the User's search criteria.
        filteredAnimals = FXCollections.observableArrayList(
            animals.stream().filter(
                p -> p.getSpecies().toLowerCase().contains(searchTxtFld.getText().toLowerCase()))
                .collect(Collectors.toList()));
      }

      // if the user wants to filter throw animal names.
      else {
        // filter is set to filter throw the animal's name with the user's search criteria.
        filteredAnimals = FXCollections.observableArrayList(
            animals.stream().filter(
                p -> p.getName().toLowerCase().contains(searchTxtFld.getText().toLowerCase()))
                .collect(Collectors.toList()));
      }
    }

    // search criteria is empty.
    else {

      // unfilter the list to show all animals.
      filteredAnimals = FXCollections
          .observableArrayList(database.getAnimalList());
    }

    // sets the tables to the filtered animals list.
    loadAnimalList(filteredAnimals);
  }

  private void loadAnimalList(ObservableList<Animal> animals) {
    animalsTableView.setItems(animals);
  }
}
