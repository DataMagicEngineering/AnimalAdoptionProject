package employeedashboard;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Database;
import models.adoption.AdoptionWithAnimal;
import models.application.VolunteerApplicationWithUser;
import models.logging.LogEntryWithUser;
import models.questions.QuestionWithUser;
import models.user.AuthorizationLevel;

/**
 * Controller for the Employee Dashboard, which contains all scenes and functionslity that a user
 * with employee privileges are allowed to do.
 * @author The Data Magic Engineering Team
 */
public class EmployeeDashboardController {

  private static Database database = Database.get();

  @FXML
  private Button viewEventsBtn;

  @FXML
  private Button viewAnimalBtn;

  @FXML
  private TabPane rootTabPane;

  @FXML
  private Tab adoptionApplicationTab;

  @FXML
  private ListView<QuestionWithUser> ans;

  @FXML
  private TextField answerQuestionTxtBox;

  @FXML
  private Button logOutBtn;

  @FXML
  private Button answerBtn;

  @FXML
  private Label userStatusLbl;

  @FXML
  private Button recordHoursBtn;

  @FXML
  private Text answerAuthorTxt;

  @FXML
  private Tab questionsTab;

  @FXML
  private ListView<QuestionWithUser> unanwQuestionList;

  @FXML
  private ListView<LogEntryWithUser> logList;

  @FXML
  private Label dashLoginLbl;

  @FXML
  private Text answerTxt;

  @FXML
  private ListView<VolunteerApplicationWithUser> unprocessedVolunteerApplicationsList;

  @FXML
  private ListView<VolunteerApplicationWithUser> processedVolunteerApplicationsList;

  @FXML
  private Button volunteerUnprocessedRejectBtn;

  @FXML
  private Tab volunteerApplicationTab;

  @FXML
  private Button volunteerUnprocessedApproveBtn;

  @FXML
  private Tab unansweredQuestionsTab;

  @FXML
  private ListView<AdoptionWithAnimal> unprocessedAdoptionsList;

  @FXML
  private ListView<AdoptionWithAnimal> processedAdoptionsList;


  /**
   * The initialize method displays the user's name and status on startup. If the user is not an
   * employee, the volunteer applications tab and the adoption applications are both disabled.
   * The answer question text box and button are also disabled.
   */
  public void initialize() {
    answerTxt.setText("");
    answerAuthorTxt.setText("");

    dashLoginLbl.setText(
        "Welcome " + Database.getCurrentUser().getLastName() + ", " + Database.getCurrentUser()
            .getFirstName() + "!");
    if (Database.getCurrentUser().getPrivileges() == AuthorizationLevel.ADMINISTRATION) {
      userStatusLbl.setText("Employee");
    } else {
      userStatusLbl.setText("Volunteer");
      volunteerApplicationTab.setDisable(true);
      adoptionApplicationTab.setDisable(true);
      answerQuestionTxtBox.setDisable(true);
      answerBtn.setDisable(true);
    }

    rootTabPane.getSelectionModel().selectedItemProperty().addListener(
        (currentValue, oldTab, newTab) -> {
          try {
            if (newTab.getId().equals("logList")) {
              updateLogsPage();
            }
          } catch (Exception ignored) {

          }
        });

    setUpUnansweredList();
    setUpAnsweredList();
    setupVolunteerApplicationsPage();
    updateAdoptionsPage();
    updateLogsPage();
  }

  private void updateLogsPage() {
    logList.setItems(FXCollections.observableList(database.getLogs()));
  }

  /**
   * Method that logs out the user. When the button is pressed, the scene switched back to the
   * login screen.
   * @param actionEvent gets the Source, Scene, and Window of the Login Screen scene.
   * @throws Exception since the code has a potential to contain any Exception.
   */
  @FXML
  void logOut(ActionEvent actionEvent) throws Exception{
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("../login/LoginScreen.fxml"));
    primaryStage.setTitle("Adoption Apps");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }

  /**
   * Method that allows employees to answer questions. An employee can select a question, respond
   * to the question in the answer question text box, and submit the response.
   * @param event an ActionEvent.
   */
  @FXML
  private void answerQuestion(ActionEvent event) {
    // sets the answer to the Question
    unanwQuestionList.getSelectionModel().getSelectedItem().getQuestion()
        .setAnswer(answerQuestionTxtBox.getText());

    // sets the answer boolean to true
    unanwQuestionList.getSelectionModel().getSelectedItem().getQuestion().setAnswered(true);

    // adds the data to the database
    database.answerQuestion(Database.getCurrentUser(),
        unanwQuestionList.getSelectionModel().getSelectedItem().getQuestion());

    updateQuestions();
  }

  private void updateQuestions() {
    setUpAnsweredList();
    setUpUnansweredList();
  }

  /**
   * Method that populates an ObservableList of type "QuestionWithUser" with answered questions.
   */
  private void setUpAnsweredList() {
    ObservableList<QuestionWithUser> answeredQuestions = FXCollections
        .observableArrayList(database.getAnsweredQuestions());

    ans.setItems(answeredQuestions);
  }

  /**
   * Method that populates an ObservableList of type "QuestionWithUser" with unanswered questions.
   */
  private void setUpUnansweredList() {
    ObservableList<QuestionWithUser> unansweredQuestions = FXCollections
        .observableArrayList(database.getUnansweredQuestions());

    unanwQuestionList.setItems(unansweredQuestions);
  }

  /**
   * Method that displays processed questions with the replies to the user's questions.
   * @param event a MouseEvent.
   */
  @FXML
  void requestAnswer(MouseEvent event) {
    QuestionWithUser selectedQuestion = ans.getSelectionModel().getSelectedItem();
    if (selectedQuestion != null) {
      String employeeName =
          "Answered by " + selectedQuestion.getAnswerer().getFirstName() + " " + selectedQuestion
              .getAnswerer().getLastName();

      answerAuthorTxt.setText(employeeName);

      answerTxt.setText(selectedQuestion.getQuestion().getAnswer());
    }
  }

  /**
   * Method that switches the scene to the Animal List.
   * @param actionEvent gets the Source, Scene, and Window of the Animal List scene.
   * @throws IOException since the code has a potential to contain an IOException.
   */
  @FXML
  public void goToAnimalScreen(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../animalList/AnimalList.fxml"));
    primaryStage.setTitle("Animal Screen");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }

  /**
   *
   */
  private void setupVolunteerApplicationsPage() {
    unprocessedVolunteerApplicationsList.selectionModelProperty().addListener(
        (observableValue, oldValue, newValue) -> {
          boolean disabled;
          disabled = newValue.getSelectedItems().size() <= 0;

          volunteerUnprocessedApproveBtn.setDisable(disabled);
          volunteerUnprocessedRejectBtn.setDisable(disabled);
        });

    updateVolunteerApplicationsPage();
  }

  /**
   * Method which populates a List of type "VolunteerApplicationWithUser", which is used to store
   * both unprocessed and processed volunteer applications.
   */
  private void updateVolunteerApplicationsPage() {
    List<VolunteerApplicationWithUser> processedApplications = database
        .getProcessedVolunteerApplications();
    List<VolunteerApplicationWithUser> unprocessedApplications = database
        .getUnprocessedVolunteerApplications();
    unprocessedVolunteerApplicationsList
        .setItems(FXCollections.observableArrayList(unprocessedApplications));
    processedVolunteerApplicationsList
        .setItems(FXCollections.observableArrayList(processedApplications));
  }

  /**
   * Method that switches scenes to the "Events" page.
   * @param actionEvent gets the Source, Scene, and Window of the Events scene.
   * @throws IOException since the code has a potential to contain any Exception.
   */
  @FXML
  public void goToEventsPage(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../viewevents/ViewEvents.fxml"));
    primaryStage.setTitle("Events");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }

  /**
   * Method which switches scenes to the "Record Logs" page.
   * @param actionEvent gets the Source, Scene, and Window of the "Record Logs" scene.
   * @throws IOException since the code has a potential to contain any Exception.
   */
  @FXML
  public void goToRecordLog(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../recordLog/recordLogPage.fxml"));
    primaryStage.setTitle("Record Log");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }

  /**
   * Method which processes a volunteer application by approving it and moving it to the
   * "Processed Applications" tab in the Volunteer Applications tab.
   */
  public void approveVolunteerRequest() {
    for (VolunteerApplicationWithUser selectedRequests : unprocessedVolunteerApplicationsList
        .getSelectionModel().getSelectedItems()) {
      database.processVolunteerApplication(selectedRequests.getApplication(), true);
    }

    updateVolunteerApplicationsPage();
  }

  /**
   * Method which processes a volunteer application by rejecting it and moving it to the
   * "Processed Applications" tab in the Volunteer Applications tab.
   */
  public void rejectVolunteerRequest() {
    for (VolunteerApplicationWithUser selectedRequests : unprocessedVolunteerApplicationsList
        .getSelectionModel().getSelectedItems()) {
      database.processVolunteerApplication(selectedRequests.getApplication(), false);
    }

    updateVolunteerApplicationsPage();
  }

  /**
   * Method which populates a List of type "AdoptionWithAnimal" that contains both processed and
   * unprocessed applications.
   */
  private void updateAdoptionsPage() {
    List<AdoptionWithAnimal> unprocessedAdoptionApplications = database
        .getUnprocessedAdoptionRequests();

    List<AdoptionWithAnimal> processedAdoptionApplications = database
        .getProcessedAdoptionRequests();

    unprocessedAdoptionsList
        .setItems(FXCollections.observableList(unprocessedAdoptionApplications));
    processedAdoptionsList.setItems(FXCollections.observableList(processedAdoptionApplications));
  }

  /**
   * Method which processes an adoption application by approving it and moving it to the
   * "Processed Applications" tab in the Adoption Application tab.
   */
  public void approveAdoptionRequest() {
    for (AdoptionWithAnimal request : unprocessedAdoptionsList.getSelectionModel()
        .getSelectedItems()) {
      request.getRequest().setApproved(true);
      request.getRequest().setDateApproved(Instant.now());
      database.processAdoptionRequest(request.getAdopter(), request.getRequest());
    }

    updateAdoptionsPage();
  }

  /**
   * Method which processes an adoption application by rejecting it and moving it to the
   * "Processed Applications" tab in the Adoption Application tab.
   */
  public void rejectAdoptionRequest() {
    for (AdoptionWithAnimal request : unprocessedAdoptionsList.getSelectionModel()
        .getSelectedItems()) {
      request.getRequest().setApproved(false);
      request.getRequest().setDateApproved(Instant.now());
      database.processAdoptionRequest(request.getAdopter(), request.getRequest());
    }

    updateAdoptionsPage();
  }
}