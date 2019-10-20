package mainScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainScreenController {

  @FXML
  private Button viewAnimalList;

  @FXML
  private Button logsBtn;

  @FXML
  void goToAnimalList(ActionEvent event) throws Exception {
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../animalList/AnimalList.fxml"));
    primaryStage.setTitle("Search Animals");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
