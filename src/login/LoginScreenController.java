package login;

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
import main.Database;
import models.user.AuthorizationLevel;
import models.user.User;

/**
 * The Login Screen Controller is the first screen that users see when he or she starts the
 * application. If the user has an account, they can type in their credentials, and depending on the
 * user's administration level, it directs them to the appropriate screen. If the user does not have
 * an account, there is a button at the bottom of the screen that new users can press, which directs
 * them to a registration page.
 *
 * @author The Data Magic Engineering Team
 */
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
  private Button registerHereButton;

  @FXML
  private Label errorLabel;

  @FXML
  private Label logginLabel;

  public void initialize() {

  }

  /**
   * Method that gets the ID of the user logging in, determines if the user is a customer,
   * volunteer, or employee, and once that information is processed, the program sends the user to
   * the appropriate screens.
   *
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
      if (user.getPrivileges() == AuthorizationLevel.VOLUNTEER
          || user.getPrivileges() == AuthorizationLevel.ADMINISTRATION) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader
            .load(getClass().getResource("../employeedashboard/EmployeeDashboard.fxml"));
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(new Scene(root));
        root.getStylesheets().add("mainCSS.css");
        primaryStage.show();
      } else {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader
            .load(getClass().getResource("../customerdashboard/CustomerDashboard.fxml"));
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(new Scene(root));
        root.getStylesheets().add("mainCSS.css");
        primaryStage.show();
      }
    }
  }

  /**
   * Method that directs a user to the Registration screen.
   *
   * @param event gets the Source, Scene, and Window of the selected scene, and is casted to a
   *              Node.
   * @throws Exception since the method has a chance to contain an IOException.
   */
  @FXML
  void goToRegisterScreen(ActionEvent event) throws Exception {
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../registeruser/RegisterUser.fxml"));
    primaryStage.setTitle("Registration Page");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }
}
