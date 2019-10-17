package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginScreenController {

  @FXML private AnchorPane backPane;

  @FXML private TextField usernameText;

  @FXML private TextField passwordText;

  @FXML private Button loginButton;

  @FXML private Label errorLabel;

  /** @param event pressing the login button */
  @FXML
  void pressedLogin(ActionEvent event) {
    String username = usernameText.getText();
    String password = passwordText.getText();
    /*
    insert code that will take in the username and password and run it through
    the database to make sure there is a connection
     */
    System.out.println("User logged in!");
  }
}
