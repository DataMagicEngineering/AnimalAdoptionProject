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

  public void initialize() {
    Database database = Database.get();
    ObservableList<Event> events = FXCollections.observableArrayList(database.getEvents());

    listViewEvents.setItems(events);
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
