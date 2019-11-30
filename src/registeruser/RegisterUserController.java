package registeruser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Database;
import models.event.Event;
import models.user.AuthorizationLevel;
import models.user.Customer;

/**
 * The Controller Class for registering a user.
 */
public class RegisterUserController {

  @FXML
  private TextField newUserLastNameTextFld;

  @FXML
  private Button newUserSubmitButton;

  @FXML
  private TextField newUserFirstNameTextFld;

  @FXML
  private TextField newUserPasswordTextFld;

  @FXML
  private TextField newUserUsernameTextFld;

  @FXML
  private Button newUserCancelButton;

  @FXML
  private DatePicker newUserDOBTextFld;

  @FXML
  private Text errorMessageText;

  @FXML
  private TextField newUserRePasswordTextFld;

  /**
   * The method for a button that once pressed, gets the text fields for a new user and creates a new customer account
   * for them. The scene switches back to the login page afterwards.
   * @param event gets the Source, Scene, and Window for the login page.
   * @throws Exception since the code has a chance to contain an Exception.
   */
  @FXML
  void registerUser(ActionEvent event) throws Exception {
    if (newUserPasswordTextFld.getText().equals(newUserRePasswordTextFld.getText())) {
      Database database = Database.get();
      Customer user = new Customer();
      user.setFirstName(newUserFirstNameTextFld.getText());
      user.setLastName(newUserLastNameTextFld.getText());
      user.setPassword(newUserPasswordTextFld.getText());
      user.setUsername(newUserUsernameTextFld.getText());
      user.setDateOfBirth(newUserDOBTextFld.getValue().atStartOfDay(Event.EST).toInstant());
      user.setPrivileges(AuthorizationLevel.BASIC);
      database.signUpUser(user);

      Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      Parent root = FXMLLoader
          .load(getClass().getResource("../login/LoginScreen.fxml"));
      primaryStage.setTitle("Adoption Application");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    }
  }

  /**
   * A method for a button that, once pressed, returns the user back to the login screen.
   * @param event gets the Source, Scene, and Window for the login screen.
   * @throws Exception since the code has a chance to contain an Exception.
   */
  @FXML
  void returnToLoginScreen(ActionEvent event) throws Exception {
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../login/LoginScreen.fxml"));
    primaryStage.setTitle("Adoption Application");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

}
