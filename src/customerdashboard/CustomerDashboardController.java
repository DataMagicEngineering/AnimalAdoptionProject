package customerdashboard;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for the Customer Dashboard, which contains all of the scenes that a user with no
 * privileges can view.
 * @author The Data Magic Engineering team.
 */
public class CustomerDashboardController {

  @FXML
  private Button btnViewAnimals;

  @FXML
  private Button btnViewEvents;

  @FXML
  private Button btnAskQuestions;

  @FXML
  private Button btnLogOff;

  @FXML
  private Button btnApplyVolunteer;

  @FXML
  private Label lblWelcome;

  /**
   * Method that switches the scene to the Animal List.
   * @param actionEvent gets the Source, Scene, and Window of the Animal List scene.
   * @throws IOException since the code has a potential to contain an IOException.
   * @author Emily Schwarz and Luis Hernandez
   */
  public void goToAnimalScreen(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../animalList/AnimalList.fxml"));
    primaryStage.setTitle("Animal List");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }

  /**
   * Method that switches the scene to the Events page.
   * @param actionEvent gets the Source, Scene, and Window of the Events scene.
   * @throws IOException since the code has a potential to contain an IOException.
   * @author Emily Schwarz and Luis Hernandez
   */
  public void goToEventsScreen(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../viewevents/ViewEvents.fxml"));
    primaryStage.setTitle("Events");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }

  /**
   * Method that switches the scenes to the Volunteer Application page.
   * @param actionEvent gets the Source, Scene, and Window of the Volunteer Application scene.
   * @throws IOException since the code has a potential to contain an IOException.
   * @author Emily Schwarz and Luis Hernandez
   */
  public void goToVolunteerApplication(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../volunteerApplicationPage/volunteerApplication.fxml"));
    primaryStage.setTitle("Volunteer Application");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }

  /**
   * Method that logs out the user. When the button is pressed, the scene switched back to the
   * login screen.
   * @param actionEvent gets the Source, Scene, and Window of the Login Screen scene.
   * @throws Exception since the code has a potential to contain any Exception.
   */
  @FXML
  void logOut(ActionEvent actionEvent) throws Exception {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("../login/LoginScreen.fxml"));
    primaryStage.setTitle("Adoption Apps");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }

  /**
   * Method that switches scenes to the "Ask Question" page.
   * @param actionEvent gets the Source, Scene, and Window of the Ask Question scene.
   * @throws Exception since the code has a potential to contain any Exception.
   */
  @FXML
  void askQuestion(ActionEvent actionEvent) throws Exception {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("../customerquestions/"
        + "CustomerAskQsScreen.fxml"));
    primaryStage.setTitle("Ask a Question");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }
}
