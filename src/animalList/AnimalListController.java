package animalList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AnimalListController {
  @FXML
  private Button returnToMain;

  @FXML
  private Button testSelectBtn;

  @FXML
  void goToAnimalAdoption(ActionEvent event) throws Exception{
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../adoptionPage/AdoptionPage.fxml"));
    primaryStage.setTitle("Adoption Page");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

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
