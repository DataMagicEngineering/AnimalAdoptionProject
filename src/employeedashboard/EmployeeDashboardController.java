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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Database;
import models.adoption.AdoptionWithAnimal;
import models.application.VolunteerApplicationWithUser;
import models.questions.QuestionWithUser;
import models.user.AuthorizationLevel;

public class EmployeeDashboardController {

  private static Database database = Database.get();

  @FXML
  private Button viewEventsBtn;

  @FXML
  private Button viewAnimalBtn;

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
  private TextArea logList;

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

    setUpUnansweredList();
    setUpAnsweredList();
    setupVolunteerApplicationsPage();
    updateAdoptionsPage();
  }

  @FXML
  void logOut(ActionEvent actionEvent) throws Exception{
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("../login/LoginScreen.fxml"));
    primaryStage.setTitle("Adoption Apps");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

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

  private void setUpAnsweredList() {
    ObservableList<QuestionWithUser> answeredQustions = FXCollections
        .observableArrayList(database.getAnsweredQuestions());

    ans.setItems(answeredQustions);
  }

  private void setUpUnansweredList() {
    ObservableList<QuestionWithUser> unansweredQuestions = FXCollections
        .observableArrayList(database.getUnansweredQuestions());

    unanwQuestionList.setItems(unansweredQuestions);
  }

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

  @FXML
  public void goToAnimalScreen(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../animalList/AnimalList.fxml"));
    primaryStage.setTitle("Animal Screen");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

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

  @FXML
  public void goToEventsPage(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../viewevents/ViewEvents.fxml"));
    primaryStage.setTitle("Events");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  @FXML
  public void goToRecordLog(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../recordLog/recordLogPage.fxml"));
    primaryStage.setTitle("Record Log");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  public void approveVolunteerRequest() {
    for (VolunteerApplicationWithUser selectedRequests : unprocessedVolunteerApplicationsList
        .getSelectionModel().getSelectedItems()) {
      database.processVolunteerApplication(selectedRequests.getApplication(), true);
    }

    updateVolunteerApplicationsPage();
  }

  public void rejectVolunteerRequest() {
    for (VolunteerApplicationWithUser selectedRequests : unprocessedVolunteerApplicationsList
        .getSelectionModel().getSelectedItems()) {
      database.processVolunteerApplication(selectedRequests.getApplication(), false);
    }

    updateVolunteerApplicationsPage();
  }

  private void updateAdoptionsPage() {
    List<AdoptionWithAnimal> unprocessedAdoptionApplications = database
        .getUnprocessedAdoptionRequests();

    List<AdoptionWithAnimal> processedAdoptionApplications = database
        .getProcessedAdoptionRequests();

    unprocessedAdoptionsList
        .setItems(FXCollections.observableList(unprocessedAdoptionApplications));
    processedAdoptionsList.setItems(FXCollections.observableList(processedAdoptionApplications));
  }

  public void approveAdoptionRequest() {
    for (AdoptionWithAnimal request : unprocessedAdoptionsList.getSelectionModel()
        .getSelectedItems()) {
      request.getRequest().setApproved(true);
      request.getRequest().setDateApproved(Instant.now());
      database.processAdoptionRequest(request.getAdopter(), request.getRequest());
    }

    updateAdoptionsPage();
  }

  public void rejectAdoptionRequest() {
    for (AdoptionWithAnimal request : unprocessedAdoptionsList.getSelectionModel()
        .getSelectedItems()) {
      request.getRequest().setApproved(false);
      database.processAdoptionRequest(request.getAdopter(), request.getRequest());
    }

    updateAdoptionsPage();
  }
}