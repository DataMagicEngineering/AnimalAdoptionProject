package customerdashboard;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CustomerDashboardController {

  @FXML
  private Button customerViewAnimalsBtn;

  @FXML
  private Button customerViewEventsBtn;

  @FXML
  private Button askQuestionBtn;

  @FXML
  private Button customerLogOutBtn;

  @FXML
  private Button applyToVolunteerBtn;

  public void goToAnimalScreen(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../animalList/AnimalList.fxml"));
    primaryStage.setTitle("Animal Screen");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  public void goToEventsScreen(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../newevent/NewEvent.fxml"));
    primaryStage.setTitle("Events");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
