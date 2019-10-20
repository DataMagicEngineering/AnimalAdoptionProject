package animalList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * AnimalListController class which contains two methods that switch the scenes between the adoption page and the
 * main menu.
 * @author The Data Magic Engineering Team
 */
public class AnimalListController {
  @FXML
  private Button returnToMain;

  @FXML
  private Button testSelectBtn;

  /**
   * Method which sets the scene to the Adoption Page.
   * @param event an ActionEvent that gets the source, scene, and window of the program.
   * @throws Exception is thrown since this method has the potential to contain an Exception.
   */
  @FXML
  void goToAnimalAdoption(ActionEvent event) throws Exception{
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../adoptionPage/AdoptionPage.fxml"));
    primaryStage.setTitle("Adoption Page");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  /**
   * Method which sets the scene to the Main Menu.
   * @param event an ActionEvent that gets the source, scene, and window of the program.
   * @throws Exception is thrown since this method has the potential to contain an Exception.
   */
  @FXML
  void goToMainMenu(ActionEvent event) throws Exception {
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../mainScreen/AnimalAdoptMainScreen.fxml"));
    primaryStage.setTitle("Main Screen");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
