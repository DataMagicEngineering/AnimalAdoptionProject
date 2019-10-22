package login;

import static models.animal.Color.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.xml.crypto.Data;
import main.Database;
import models.animal.Animal;
import models.animal.Color;
import models.animal.Proficiency;
import models.animal.Trick;
import models.animal.Vaccine;
import models.user.AuthorizationLevel;
import models.user.User;

public class LoginScreenController {

  @FXML
  private AnchorPane backPane;

  @FXML
  private TextField usernameText;

  @FXML
  private PasswordField passwordText;

  @FXML
  private Button loginButton;

  @FXML
  private Label errorLabel;

  @FXML
  private Label logginLabel;

  /**
   * @param event pressing the login button
   */
  @FXML
  void pressedLogin(ActionEvent event) throws Exception {
    Database database = Database.get();
    User user = database.loginUser(usernameText.getText(), passwordText.getText());
    if (user == null) {
      errorLabel.setVisible(true);
    } else {
      errorLabel.setVisible(false);
      if (user.getPrivileges() == AuthorizationLevel.VOLUNTEER || user.getPrivileges() == AuthorizationLevel.ADMINISTRATION) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader
            .load(getClass().getResource("../employeedashboard/EmployeeDashboard.fxml"));
        primaryStage.setTitle("Dashboard");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
      }
      else {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader
            .load(getClass().getResource("../mainScreen/AnimalAdoptMainScreen.fxml"));
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
      }
    }
  }
  public void initialize() {

  }
}
