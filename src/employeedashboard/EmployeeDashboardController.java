package employeedashboard;

import java.awt.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.Database;
import models.questions.Question;
import models.user.AuthorizationLevel;

public class EmployeeDashboardController {



  @FXML
  private Button viewEventsBtn;

  @FXML
  private Button viewAnimalBtn;

  @FXML
  private Tab anwQuestionsList;

  @FXML
  private ListView<?> ans;

  @FXML
  private TextField answerQuestionTxtBtn;

  @FXML
  private Button answerBtn;

  @FXML
  private Label userStatusLbl;

  @FXML
  private Button recordHoursBtn;

  @FXML
  private Text answerAuthorTxt;

  @FXML
  private ListView<Question> unanwQuestionList;

  @FXML
  private TextArea logList;

  @FXML
  private Label dashLoginLbl;

  @FXML
  private Text answereTxt;

  @FXML
  void answerQuestion(ActionEvent event) {

  }


  private void setUpUnansweredList() {

    Database database = Database.get();

    ObservableList<Question> unansweredQuestions = FXCollections
        .observableArrayList(database.getUnansweredQuestions());

    unanwQuestionList.setItems(unansweredQuestions);
  }


  public void initialize() {
    dashLoginLbl.setText(
        "Welcome " + Database.getCurrentUser().getLastName() + ", " + Database.getCurrentUser()
            .getFirstName() + "!");
    if (Database.getCurrentUser().getPrivileges() == AuthorizationLevel.ADMINISTRATION) {
      userStatusLbl.setText("Employee");
    } else {
      userStatusLbl.setText("Volunteer");
    }
    setUpUnansweredList();
  }

}
