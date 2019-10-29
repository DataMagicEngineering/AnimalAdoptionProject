package employeedashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.Database;
import models.user.AuthorizationLevel;

public class EmployeeDashboardController {


  @FXML
  private Button viewEventsBtn;

  @FXML
  private Label userStatusLbl;

  @FXML
  private Button recordHoursBtn;

  @FXML
  private Button viewAnimalBtn;

  @FXML
  private TextArea logList;

  @FXML
  private TextField answerQuestionTxtBtn;

  @FXML
  private Label dashLoginLbl;

  @FXML
  private Button answerBtn;



  public void initialize() {
    dashLoginLbl.setText(
        "Welcome " + Database.getCurrentUser().getLastName() + ", " + Database.getCurrentUser()
            .getFirstName()+"!");
    if (Database.getCurrentUser().getPrivileges() == AuthorizationLevel.ADMINISTRATION) {
      userStatusLbl.setText("Employee");
    }
    else{
      userStatusLbl.setText("Volunteer");
    }
  }

}
