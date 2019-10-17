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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.xml.crypto.Data;
import main.Database;

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
    if (database.loginUser(usernameText.getText(), passwordText.getText()) == null) {
      errorLabel.setVisible(true);
    } else {
      errorLabel.setVisible(false);
      Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      Parent root = FXMLLoader
          .load(getClass().getResource("../mainScreen/AnimalAdoptMainScreen.fxml"));
      primaryStage.setTitle("Main Screen");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    }
  }
}
