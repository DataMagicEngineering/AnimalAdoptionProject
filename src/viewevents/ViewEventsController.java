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

  @FXML
  void addEvent(ActionEvent event) throws Exception {
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../newevent/NewEvent.fxml"));
    primaryStage.setTitle("Creat an Event");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

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
   * @param actionEvent
   * @throws IOException
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
