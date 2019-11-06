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


public class recordLogsController {

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
   *
   * @param actionEvent
   * @throws IOException
   * @author Luis Hernandez
   */
  public void goToConfirmHours(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../recordLog/recordedLogs.fxml"));
    primaryStage.setTitle("Record Log");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  /**
   *
   * @param actionEvent
   * @throws IOException
   * @author Luis Hernandez
   */
  public void goBackToDashboard(ActionEvent actionEvent) throws IOException {
    Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = FXMLLoader
        .load(getClass().getResource("../employeedashboard/EmployeeDashboard.fxml"));
    primaryStage.setTitle("Dashboard");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
