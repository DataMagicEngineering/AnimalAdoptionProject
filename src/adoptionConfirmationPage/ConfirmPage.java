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
 * The ConfirmPage class contains all of the code and functionality of the Confirmation Page screen.
 * The screen contains three different buttons, all of which go to different screens depending on
 * the button clicked.
 *
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

  /**
   * The method that logs the user off when the button is pressed.
   *
   * @param actionEvent gets the Source, Scene, and Window of the selected scene, and is casted to a
   *                    Node.
   * @throws IOException since the method has a chance to contain an IOException.
   */
  public void LogUserOut(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("../login/LoginScreen.fxml"));
    primaryStage.setTitle("Adoption Apps");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();

    Database.setCurrentAnimal(null);
  }

  /**
   * The method that switches screens to the Main Screen.
   *
   * @param actionEvent gets the Source, Scene, and Window of the selected scene, and is casted to a
   *                    Node.
   * @throws IOException since the method has a chance to contain an IOException.
   */
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

  /**
   * Method that switches scenes to the Animal List screen.
   *
   * @param actionEvent gets the Source, Scene, and Window of the selected scene, and is casted to a
   *                    Node.
   * @throws IOException since the method has a chance to contain an IOException.
   */
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
