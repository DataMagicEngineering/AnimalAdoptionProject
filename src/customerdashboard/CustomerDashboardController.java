package customerdashboard;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CustomerDashboardController {

  @FXML
  private Button btnViewAnimals;

  @FXML
  private Button btnViewEvents;

  @FXML
  private Button btnAskQuestions;

  @FXML
  private Button btnLogOff;

  @FXML
  private Button btnApplyVolunteer;

  @FXML
  private Label lblWelcome;

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
        .load(getClass().getResource("../viewevents/ViewEvents.fxml"));
    primaryStage.setTitle("Events");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  public void goToVolunteerApplication(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../volunteerApplicationPage/volunteerApplication.fxml"));
    primaryStage.setTitle("Volunteer Application");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
