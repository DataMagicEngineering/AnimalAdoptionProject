package viewevents;

import java.io.IOException;
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
import javafx.stage.Stage;
import main.Database;
import models.event.Event;
import models.user.AuthorizationLevel;
import models.user.User;

/**
 * The Controller Class to view events.
 */
public class ViewEventsController {

  @FXML
  private Button btnGoBack;

  @FXML
  private Label lblTop;

  @FXML
  private Label lblBottom;

  @FXML
  private ListView<Event> listViewEvents;

  @FXML
  private Button addEventBtn;

  @FXML
  private Button editEventBtn;

  /**
   * The initialize method for the ViewEventController class that sets the current events to the List View once the
   * program starts.
   */
  public void initialize() {
    Database database = Database.get();
    ObservableList<Event> events = FXCollections.observableArrayList(database.getEvents());

    User user = Database.getCurrentUser();

    listViewEvents.setItems(events);

    if (user.getPrivileges() == AuthorizationLevel.BASIC) {
      addEventBtn.setDisable(true);
      addEventBtn.setVisible(false);
      editEventBtn.setVisible(false);
      editEventBtn.setDisable(true);
    }
  }

  /**
   * Method for a button that switches the user to the "Create an Event" page once pressed.
   * @param event gets the Source, Scene, and Window of the "Create an Event" page.
   * @throws Exception since the code has a chance to contain an Exception.
   */
  @FXML
  void addEvent(ActionEvent event) throws Exception {
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../newevent/NewEvent.fxml"));
    primaryStage.setTitle("Create an Event");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  /**
   * Method for a button that switches the user to the "Edit Event" screen once pressed.
   * @param event gets the Source, Scene, and Window of the "Edit Event" page.
   * @throws Exception since the code has a chance to contain an Exception.
   */
  @FXML
  void editEvent(ActionEvent event) throws Exception {
    Database.setCurrentEvent(listViewEvents.getSelectionModel().getSelectedItem());

    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../newevent/NewEvent.fxml"));
    primaryStage.setTitle("Edit an Event");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  /**
   * The method for the button that, once pressed, returns the user back to the Employee Dashboard.
   * @param actionEvent gets the Source, Scene, and Window for the Employee Dashboard.
   * @throws IOException since the code has a chance to contain an IOException.
   * @author Emily Schwarz and Luis Hernandez
   */
  public void goBack(ActionEvent actionEvent) throws IOException {
    User user = Database.getCurrentUser();

    if (user.getPrivileges() == AuthorizationLevel.VOLUNTEER
        || user.getPrivileges() == AuthorizationLevel.ADMINISTRATION) {
      Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
      Parent root = FXMLLoader
          .load(getClass().getResource("../employeedashboard/EmployeeDashboard.fxml"));
      primaryStage.setTitle("Main Screen");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    } else {
      Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
      Parent root = FXMLLoader
          .load(getClass().getResource("../customerdashboard/CustomerDashboard.fxml"));
      primaryStage.setTitle("Main Screen");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    }
  }
}
