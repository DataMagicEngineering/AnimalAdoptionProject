package adoptionConfirmationPage;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import main.Database;
import models.user.AuthorizationLevel;

/**
 * @author Luis Hernandez and Emily Schwarz
 */
public class ConfirmPage {

  @FXML
  private Button btnReturntoList;

  @FXML
  private Button btnReturntoMain;

  @FXML
  private Button btnLogOff;

  @FXML
  private TextArea txtfieldThanks;

  public void LogUserOut(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("../login/LoginScreen.fxml"));
    primaryStage.setTitle("Adoption Apps");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();

    Database.setCurrentAnimal(null);
  }

  public void goToMain(ActionEvent actionEvent) throws IOException {
    if (Database.getCurrentUser().getPrivileges() == AuthorizationLevel.ADMINISTRATION
        || Database.getCurrentUser().getPrivileges() == AuthorizationLevel.VOLUNTEER) {
      Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
      Parent root = FXMLLoader
          .load(getClass().getResource("../employeedashboard/EmployeeDashboard.fxml"));
      primaryStage.setTitle("Dashboard");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    } else {
      Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
      Parent root = FXMLLoader
          .load(getClass().getResource("../customerdashboard/CustomerDashboard.fxml"));
      primaryStage.setTitle("Main Screen");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    }

    Database.setCurrentAnimal(null);
  }

  public void goToAnimalList(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../animalList/AnimalList.fxml"));
    primaryStage.setTitle("Main Screen");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();

    Database.setCurrentAnimal(null);
  }
}
