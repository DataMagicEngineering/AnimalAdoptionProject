package recordLog;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The Controller Class to record hours of volunteers or employees.
 */
public class RecordLogsController {

  @FXML
  private AnchorPane lblEnterHrs;

  @FXML
  private TextField txtfHrsWorked;

  @FXML
  private Button btnConfirmHrs;

  @FXML
  private Tab tbpRecordedHrs;

  @FXML
  private TextArea txtaShowHrs;

  @FXML
  private Button btnReturntoEmployeeMenu;

  /**
   * Method that switches scenes to the "Confirm Hours" page.
   * @param actionEvent gets the Source, Scene, and Window.
   * @throws IOException since the code has a chance to contain an Exception.
   * @author Luis Hernandez
   */
  public void goToConfirmHours(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../recordLog/recordedLogs.fxml"));
    primaryStage.setTitle("Record Log");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }

  /**
   * Method that switches scenes to the Employee Dashboard page.
   * @param actionEvent gets the Source, Scene, and Window.
   * @throws IOException since the code has a chance to contain an Exception.
   * @author Luis Hernandez
   */
  public void goBackToDashboard(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../employeedashboard/EmployeeDashboard.fxml"));
    primaryStage.setTitle("Dashboard");
    primaryStage.setScene(new Scene(root));
    root.getStylesheets().add("mainCSS.css");
    primaryStage.show();
  }
}
