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
   *
   * @param actionEvent
   * @throws IOException
   * @author Emily Schwarz and Luis Hernandez
   */
  public void goBack(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../customerdashboard/CustomerDashboard.fxml"));
    primaryStage.setTitle("Events");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
