package animalList;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Database;
import models.animal.Animal;

/**
 * AnimalListController class which contains two methods that switch the scenes between the adoption
 * page and the main menu.
 *
 * @author The Data Magic Engineering Team
 */
public class AnimalListController {

  private Database database = Database.get();
  private ObservableList<Animal> Animals = FXCollections
      .observableArrayList(database.getAnimalList());

  @FXML
  private TableColumn<List<String>, String> breedColumn;

  @FXML
  private TableColumn<?, ?> nameColumn;

  @FXML
  private Button returnToMain;

  @FXML
  private TableColumn<?, ?> speciesColumn;

  @FXML
  private TableView<Animal> animalsTableView;


  public void initialize() {
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    speciesColumn.setCellValueFactory(new PropertyValueFactory<>("species"));
    breedColumn.setCellValueFactory(new PropertyValueFactory<>("breeds"));
    animalsTableView.setItems(Animals);
  }

  /**
   * Method which sets the scene to the Adoption Page.
   *
   * @param event an ActionEvent that gets the source, scene, and window of the program.
   * @throws Exception is thrown since this method has the potential to contain an Exception.
   */
  @FXML
  void goToAnimalAdoption(ActionEvent event) throws Exception {
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../animalProfile/AnimalProfile.fxml"));
    primaryStage.setTitle("Animal Profile");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  /**
   * Method which sets the scene to the Main Menu.
   *
   * @param event an ActionEvent that gets the source, scene, and window of the program.
   * @throws Exception is thrown since this method has the potential to contain an Exception.
   */
  @FXML
  void goToMainMenu(ActionEvent event) throws Exception {
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../customerdashboard/CustomerDashboard.fxml"));
    primaryStage.setTitle("Main Screen");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
