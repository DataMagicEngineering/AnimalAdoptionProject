package viewevents;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javax.xml.crypto.Data;
import main.Database;
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
  private ListView<?> listViewEvents;

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
          .load(getClass().getResource("../customerdashboard/CustomerDashBoard.fxml"));
      primaryStage.setTitle("Main Screen");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    }
  }
}
