package registeruser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
  private TextField newUserUserNameTextFld;

  @FXML
  private Button newUserCancelButton;

  @FXML
  private DatePicker newUserDOBTextFld;

  @FXML
  private Text errorMessageText;

  @FXML
  private TextField newUserRePasswordTextFld;

  @FXML
  void registerUser(ActionEvent event) {

  }

  @FXML
  void returnToLoginScreen(ActionEvent event) {

  }

}
